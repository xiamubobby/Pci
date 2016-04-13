package im.hua.library.base.mvp.listener;

/**
 * Created by hua on 15/12/3.
 */
public interface BasePresenterListener {
    void showNetworkError(String message);

    void showServerError(String message);

    void hideLoading();
}
