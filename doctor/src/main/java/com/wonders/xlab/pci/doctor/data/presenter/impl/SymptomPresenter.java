package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.data.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.data.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.SymptomCommentModel;
import com.wonders.xlab.pci.doctor.data.model.impl.SymptomModel;
import com.wonders.xlab.pci.doctor.module.chatroom.symptomold.bean.SymptomBean;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


/**
 * Created by hua on 16/2/24.
 */
public class SymptomPresenter extends BasePresenter implements SymptomModel.SymptomModelListener, SymptomCommentModel.SymptomCommentModelListener {
    private SymptomPresenterListener mListener;
    private SymptomModel mSymptomModel;
    private SymptomCommentModel mSymptomCommentModel;

    public SymptomPresenter(SymptomPresenterListener Listener) {
        mListener = Listener;
        mSymptomModel = new SymptomModel(this);
        mSymptomCommentModel = new SymptomCommentModel(this);
        addModel(mSymptomModel);
        addModel(mSymptomCommentModel);
    }

    public void getSymptomList(String userId) {
        mListener.showLoading("");
        mSymptomModel.getSymptomList(userId);
    }

    public void saveComment(String symptomId, String doctorId, String comment, boolean check) {
        mSymptomCommentModel.saveComment(symptomId, doctorId, comment, check);
    }

    @Override
    public void onReceiveSymptomSuccess(SymptomEntity entity) {
        mListener.hideLoading();
        if (null == entity.getRet_values()) {
            mListener.showEmptyView("没有获取到数据");
            return;
        }
        List<SymptomEntity.RetValuesEntity.ContentEntity> contentEntityList = entity.getRet_values().getContent();

        Observable.from(contentEntityList)
                .map(new Func1<SymptomEntity.RetValuesEntity.ContentEntity, SymptomBean>() {
                    @Override
                    public SymptomBean call(SymptomEntity.RetValuesEntity.ContentEntity contentEntity) {
                        SymptomBean bean = new SymptomBean();
                        bean.setComment(contentEntity.getDoctorRemark());
                        bean.setSymptomId(contentEntity.getId());
                        bean.setIsChecked(contentEntity.isChecked());
                        bean.setSymptomStr(contentEntity.getSymptomContent());
                        bean.setTimeStr(DateUtil.format(contentEntity.getRecordTime(), "yyyy-MM-dd"));
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SymptomBean>() {
                    List<SymptomBean> symptomBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        if (symptomBeanList.size() > 0) {
                            mListener.showSymptomList(symptomBeanList);
                        } else {
                            mListener.showEmptyView("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(SymptomBean symptomBean) {
                        symptomBeanList.add(symptomBean);
                    }
                });

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.showNetworkError(message);
    }

    @Override
    public void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity) {
        mListener.saveCommentSuccess();
    }

    public interface SymptomPresenterListener extends BasePresenterListener {
        void showSymptomList(List<SymptomBean> symptomBeanList);

        void saveCommentSuccess();
    }
}
