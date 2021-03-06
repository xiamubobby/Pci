package im.hua.library.base.mvp.listener;

/**
 * Created by hua on 15/12/3.
 */
public interface BasePresenterListener {
    void showLoading(String message);

    void showNetworkError(String message);

    void showServerError(String message);

    void showEmptyView(String message);

    void showToast(String message);

    void hideLoading();
}
