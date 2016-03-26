package com.wonders.xlab.pci.module.base.mvn.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.assist.retrofit.HttpLoggingInterceptor;
import com.wonders.xlab.pci.module.base.mvn.entity.BaseEntity;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hua on 15/12/13.
 */
public abstract class BaseModel<T extends BaseEntity> {
    protected Retrofit mRetrofit;
    private Observable<T> mObservable;
    private Subscription subscribe;

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
//        client.setConnectTimeout(30, TimeUnit.SECONDS);
//        client.setWriteTimeout(30, TimeUnit.SECONDS);
//        client.setReadTimeout(30, TimeUnit.SECONDS);
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

        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }

        subscribe = mObservable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        BaseModel.this.onStart();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailed("请求失败，请重试！");
                    }

                    @Override
                    public void onNext(T result) {
                        if (result != null) {
                            onSuccess(result);
                        } else {
                            onFailed("获取数据出错！");
                        }
                    }
                });
    }

    public void cancel() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        if (mObservable != null) {
            mObservable.unsubscribeOn(AndroidSchedulers.mainThread());
            mObservable = null;
        }
    }

    protected void setObservable(@NonNull Observable<T> observable) {
        cancel();
        mObservable = observable;
        fetchData();
    }

    protected void onStart() {

    }

    protected abstract void onSuccess(T response);

    protected abstract void onFailed(String message);
}
