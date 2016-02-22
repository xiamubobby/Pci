package im.hua.library.base.mvp;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

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
public abstract class BaseModel {
    protected Retrofit mRetrofit;
    private Observable<? extends BaseEntity> mObservable;
    private Subscription subscribe;

    private ResponseListener mResponseListener;

    public interface ResponseListener {
        void onSuccess(BaseEntity response);

        void onFailed(Throwable e);
    }

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
        mRetrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    public abstract String getBaseUrl();

    private void fetchData() {
        if (mObservable == null) {
            throw new IllegalArgumentException("mObservable cannot be null, must call fetchData first!");
        }

        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }

        subscribe = mObservable.subscribeOn(Schedulers.newThread())//一定要设置在新线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        onFailed("请求失败，请重试！");
                        if (mResponseListener != null) {
                            mResponseListener.onFailed(e);
                        }
                        //TODO 只为测试使用
                        if (mResponseListener != null) {
                            mResponseListener.onSuccess(null);
                        }
                    }

                    @Override
                    public void onNext(BaseEntity result) {
                        /*if (result != null) {
                            onSuccess(result);
                        } else {
                            onFailed("获取数据出错！");
                        }*/
                        if (mResponseListener != null) {
                            mResponseListener.onSuccess(result);
                        }
                    }
                });
    }

    public void cancel() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        mObservable = null;
    }

    protected void fetchData(@NonNull Observable<? extends BaseEntity> observable,@NonNull ResponseListener callback) {
        cancel();
        mResponseListener = callback;
        mObservable = observable;
        fetchData();
    }
}
