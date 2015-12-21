package com.wonders.xlab.pci.mvn.view;

/**
 * Created by hua on 15/12/18.
 */
public interface SimpleView {
    void svSuccess();

    void svFailed(String message);

    void svShowLoading();

    void svHideLoading();
}
