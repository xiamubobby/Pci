package im.hua.library.base.mvp;

/**
 * Created by hua on 15/12/3.
 */
public interface BaseView {
    void showLoading();

    void hideLoading();

    void showError(String message);
}
