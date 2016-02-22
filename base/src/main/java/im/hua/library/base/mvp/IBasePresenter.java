package im.hua.library.base.mvp;

/**
 * Created by hua on 15/12/3.
 */
public interface IBasePresenter {
    void showLoading(String message);

    void hideLoading(String message);

    void showError(String message);
}
