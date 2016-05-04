package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.notification.adapter.bean.NotifiOthersBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.INotifiOthersPresenter;
import com.wonders.xlab.pci.doctor.realm.NotifiOthersRealm;
import com.wonders.xlab.pci.doctor.util.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersPresenter extends BasePagePresenter implements INotifiOthersPresenter {
    private NotifiOthersPresenterListener mListener;

    public NotifiOthersPresenter(NotifiOthersPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getOthersNotifi(final boolean isRefresh, String doctorId) {

        if (isRefresh) {
            mListener.showLoading("");
            resetPageInfo();
        }

        Observable.just(getNextPageIndex())
                .flatMap(new Func1<Integer, Observable<NotifiOthersRealm>>() {
                    @Override
                    public Observable<NotifiOthersRealm> call(Integer integer) {
                        return Observable.from(RealmUtil.getOthersNotifis(integer, 10));
                    }
                })
                .filter(new Func1<NotifiOthersRealm, Boolean>() {
                    @Override
                    public Boolean call(NotifiOthersRealm realm) {
                        return null != realm;
                    }
                })
                .map(new Func1<NotifiOthersRealm, NotifiOthersBean>() {
                    @Override
                    public NotifiOthersBean call(NotifiOthersRealm realm) {
                        NotifiOthersBean bean = new NotifiOthersBean();
                        bean.recordTimeInMill.set(realm.getReceiveTimeInMill());
                        bean.txtContent.set(realm.getMessage());
                        return bean;
                    }
                })
                .subscribe(new Subscriber<NotifiOthersBean>() {
                    List<NotifiOthersBean> notifications = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        updatePageInfo(getNextPageIndex());

                        mListener.hideLoading();
                        if (notifications.size() > 0) {
                            if (shouldAppend()) {
                                mListener.appendOthersNotifications(notifications);
                            } else {
                                mListener.showOthersNotifications(notifications);
                            }
                        } else {
                            if (isRefresh) {
                                mListener.showEmptyView("");
                            } else {
                                mListener.showReachTheLastPageNotice("通知都看完了");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.hideLoading();
                        mListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(NotifiOthersBean notifiOthersBean) {
                        notifications.add(notifiOthersBean);
                    }
                });
    }

    public interface NotifiOthersPresenterListener extends BasePagePresenterListener {
        void showOthersNotifications(List<NotifiOthersBean> notifications);
        void appendOthersNotifications(List<NotifiOthersBean> notifications);
    }
}
