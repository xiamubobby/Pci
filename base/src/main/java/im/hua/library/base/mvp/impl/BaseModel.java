package im.hua.library.base.mvp.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.retrofit.HttpLoggingInterceptor;
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
public abstract class BaseModel<T extends BaseEntity> implements IBaseModel {
    public final static int ERROR_CODE_UNEXPECTED = -9999999;
    protected Retrofit mRetrofit;
    private Observable<T> mObservable;
    private Subscription subscribe;

    public abstract String getBaseUrl();

    protected abstract void onSuccess(T response);

    protected abstract void onFailed(Throwable e, String message);

    public BaseModel() {
        /**
         * TODO 开启日志
         * 说明：由于当前的okhttp在jcenter库中并不是最新的，关于日志记录的代码在github中有，所以{@link HttpLoggingInterceptor}目前是手动在项目中新建的类
         * 如果后面okhttp更新了，可去掉，而用square的
         */
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        client.interceptors().add(logging);
//        client.setConnectTimeout(30, TimeUnit.SECONDS);
//        client.setWriteTimeout(30, TimeUnit.SECONDS);
//        client.setReadTimeout(30, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    private void fetchData() {
        if (mObservable == null) {
            Log.e("BaseModel", "mObservable cannot be null, must call fetchData first!");
            return;
        }

        subscribe = mObservable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("10000ms")) {
                            onFailed(new Throwable("连接超时，请检查网络后重试！"), "连接超时，请检查网络后重试！");
                        } else {
                            onFailed(new Throwable(e.getMessage()), "");
                        }

                    }

                    @Override
                    public void onNext(T result) {
                        if (null == result) {
                            onFailed(new Throwable("请求出错，请检查网络后重试！"), "");
                        } else if (0 != result.getRet_code()) {
                            onFailed(new Throwable(result.getMessage()),result.getMessage());
                        } else {
                            onSuccess(result);
                        }
                    }
                });
    }

    @Override
    public void cancel() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        mObservable = null;
    }

    protected void fetchData(@NonNull Observable<T> observable, boolean autoCancelPreFetch) {
        if (autoCancelPreFetch && subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        mObservable = observable;
        fetchData();
    }
}
