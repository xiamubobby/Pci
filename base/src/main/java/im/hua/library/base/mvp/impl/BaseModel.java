package im.hua.library.base.mvp.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.retrofit.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hua on 15/12/13.
 */
public abstract class BaseModel<T extends BaseEntity> implements IBaseModel {
    public final static int ERROR_CODE_CLIENT_EXCEPTION = -12344;
    public final static int ERROR_CODE_CONNECT_EXCEPTION = -12345;
    protected Retrofit mRetrofit;
    private Observable<Response<T>> mObservable;
    private Subscription subscribe;

    public abstract String getBaseUrl();

    @Deprecated
    protected void onSuccess(T response) {}

    @Deprecated
    protected void onFailed(int code, String message) {}

    public BaseModel() {
        /**
         * TODO 开启日志
         * 说明：由于当前的okhttp在jcenter库中并不是最新的，关于日志记录的代码在github中有，所以{@link HttpLoggingInterceptor}目前是手动在项目中新建的类
         * 如果后面okhttp更新了，可去掉，而用square的
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    private void request() {
        if (mObservable == null) {
            Log.e("BaseModel", "mObservable cannot be null, must call request first!");
            onFailed(-1, "请求失败，请重新打开后再试！");
            return;
        }

        subscribe = mObservable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<T>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            if (e instanceof SocketException) {
                                onFailed(ERROR_CODE_CONNECT_EXCEPTION, "连接出错，请检查网络后重试！");
                            } else if (e instanceof JsonParseException) {
                                onFailed(ERROR_CODE_CLIENT_EXCEPTION, "数据解析出错，请稍候重试！");
                            } else {
                                onFailed(ERROR_CODE_CLIENT_EXCEPTION, "请求失败，请检查网络后重试！");
                            }
                        } else {
                            onFailed(ERROR_CODE_CLIENT_EXCEPTION, "请求失败，请检查网络后重试！");
                        }
                    }

                    @Override
                    public void onNext(Response<T> result) {
                        int code = result.code();
                        switch (code) {
                            case 400:
                                onFailed(code, "服务器开小差了，请稍候重试哦！");
                                break;
                            case 401:
                                onFailed(code, "请确保在正确授权的情况下，再重试哦！");
                                return;
                            case 403:
                                onFailed(code, "服务器拒绝了你的请求，请稍候重试吧！");
                                return;
                            case 404:
                                onFailed(code, "好像没有找到服务器哦，请稍候重试吧！");
                                return;
                            case 405:
                                onFailed(code, "一定是你请求的方式有问题，换个方法吧！");
                                return;
                            case 415:
                                onFailed(code, "你上传了不支持的媒体类型哦，请先确认上传的类型是对的吧！");
                                return;
                            case 500:
                                onFailed(code, "服务器内部出错啦，请稍候重试！");
                                return;
                            case 503:
                                onFailed(code, "服务不可用咯，请稍候重试！");
                                return;
                        }

                        T body = result.body();
                        if (null == body) {
                            onFailed(-1, "获取数据出错，请重试！");
                        } else if (0 > body.getRet_code()) {
                            onFailed(body.getRet_code(), body.getMessage());
                        } else {
                            onSuccess(result.body());
                        }
                    }
                });
    }

    /**
     * cancel the network request and release the resource
     */
    @Override
    public void cancel() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        mObservable = null;
    }

    /**
     * get ready for the use of dagger2 and unittest
     * use {@link BaseModel#request(Observable, Callback)} instead
     *
     * @param observable
     * @param autoCancelPreFetch
     */
    @Deprecated
    protected void request(@NonNull Observable<Response<T>> observable, boolean autoCancelPreFetch) {
        if (autoCancelPreFetch && subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        mObservable = observable;
        request();
    }

    /**
     *
     * @param observable
     * @param callback
     */
    protected void request(@NonNull Observable<Response<T>> observable, final Callback<T> callback) {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        subscribe = observable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<T>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            if (e instanceof SocketException) {
                                callback.onFailed(ERROR_CODE_CONNECT_EXCEPTION, "连接出错，请检查网络后重试！");
                            } else if (e instanceof JsonParseException) {
                                callback.onFailed(ERROR_CODE_CLIENT_EXCEPTION, "数据解析出错，请稍候重试！");
                            } else {
                                onFailed(ERROR_CODE_CLIENT_EXCEPTION, "请求失败，请检查网络后重试！");
                            }
                        } else {
                            callback.onFailed(ERROR_CODE_CLIENT_EXCEPTION, "请求失败，请检查网络后重试！");
                        }
                    }

                    @Override
                    public void onNext(Response<T> result) {
                        int code = result.code();
                        switch (code) {
                            case 400:
                                callback.onFailed(code, "服务器开小差了，请稍候重试哦！");
                                break;
                            case 401:
                                callback.onFailed(code, "请确保在正确授权的情况下，再重试哦！");
                                return;
                            case 403:
                                callback.onFailed(code, "服务器拒绝了你的请求，请稍候重试吧！");
                                return;
                            case 404:
                                callback.onFailed(code, "好像没有找到服务器哦，请稍候重试吧！");
                                return;
                            case 405:
                                callback.onFailed(code, "一定是你请求的方式有问题，换个方法吧！");
                                return;
                            case 415:
                                callback.onFailed(code, "你上传了不支持的媒体类型哦，请先确认上传的类型是对的吧！");
                                return;
                            case 500:
                                callback.onFailed(code, "服务器内部出错啦，请稍候重试！");
                                return;
                            case 503:
                                callback.onFailed(code, "服务不可用咯，请稍候重试！");
                                return;
                        }

                        T body = result.body();
                        if (null == body) {
                            callback.onFailed(-1, "获取数据出错，请重试！");
                        } else if (0 > body.getRet_code()) {
                            callback.onFailed(body.getRet_code(), body.getMessage());
                        } else {
                            callback.onSuccess(result.body());
                        }
                    }
                });
    }

    /**
     * callback when the request success or occur an error
     * @param <T>
     */
    public interface Callback<T> {
        void onSuccess(T response);

        void onFailed(int code, String message);
    }
}
