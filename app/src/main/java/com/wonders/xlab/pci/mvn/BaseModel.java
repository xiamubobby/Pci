package com.wonders.xlab.pci.mvn;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.wonders.xlab.common.retrofit.HttpLoggingInterceptor;
import com.wonders.xlab.pci.Constant;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hua on 15/12/13.
 */
public abstract class BaseModel<T extends BaseEntity> {
    protected Retrofit mRetrofit;
    private Observable<T> mObservable;

    private boolean isRequesting = false;

    public BaseModel() {
        /**
         * TODO 开启日志
         * 说明：由于当前的okhttp在jcenter库中并不是最新的，关于日志记录的代码在github中有，所以{@link HttpLoggingInterceptor}目前是手动在项目中新建的类
         * 如果后面okhttp更新了，可去掉，而用square的
         */
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(logging);

        mRetrofit = new Retrofit.Builder().baseUrl(Constant.BASE_FEED)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    private synchronized void fetchData() {
        if (mObservable == null) {
            throw new IllegalArgumentException("mObservable cannot be null, must call setObservable first!");
        }
        mObservable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        BaseModel.this.onStart();
                        setRequesting(true);
                    }

                    @Override
                    public void onCompleted() {
                        setRequesting(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setRequesting(false);
                        onFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(T result) {
                        setRequesting(false);
                        if (result != null) {
                            if (result.getRet_code() == 0) {
                                onSuccess(result);
                            } else {
                                onFailed(result.getMessage());
                            }
                        } else {
                            onFailed(getErrorMessage());
                        }
                    }
                });

    }

    private synchronized void setRequesting(boolean isRequesting) {
        this.isRequesting = isRequesting;
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    public synchronized void cancel() {
        if (mObservable != null && isRequesting) {
            mObservable.unsubscribeOn(AndroidSchedulers.mainThread());
            mObservable = null;
        }
    }

    protected void cancelPreRequest() {

    }

    /**
     * cancel first
     *
     * @param observable
     */
    protected synchronized void setObservable(@NonNull Observable<T> observable) {
        if (mObservable != null && isRequesting) {
            cancelPreRequest();
        }
        cancel();
        mObservable = observable;
        fetchData();
    }

    protected String getErrorMessage() {
        return "获取数据出错！";
    }

    protected void onStart() {

    }

    protected abstract void onSuccess(@NonNull T response);

    /**
     * 请求成功，但是ret_code为-1等错误信息标记
     *
     * @param message
     */
    protected abstract void onFailed(String message);
}
