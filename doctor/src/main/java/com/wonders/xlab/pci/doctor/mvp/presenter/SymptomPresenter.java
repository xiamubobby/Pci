package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.symptom.bean.SymptomBean;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.SymptomCommentModel;
import com.wonders.xlab.pci.doctor.mvp.model.SymptomModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SymptomCommentModelListener;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SymptomModelListener;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.SymptomPresenterListener;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/2/24.
 */
public class SymptomPresenter extends BasePresenter implements SymptomModelListener, SymptomCommentModelListener {
    private SymptomPresenterListener mISymptomPresenter;
    private SymptomModel mSymptomModel;
    private SymptomCommentModel mSymptomCommentModel;

    public SymptomPresenter(SymptomPresenterListener ISymptomPresenter) {
        mISymptomPresenter = ISymptomPresenter;
        mSymptomModel = new SymptomModel(this);
        mSymptomCommentModel = new SymptomCommentModel(this);
        addModel(mSymptomModel);
        addModel(mSymptomCommentModel);
    }

    public void getSymptomList(String userId) {
        mSymptomModel.getSymptomList(userId);
    }

    public void saveComment(String symptomId,String doctorId,String comment,boolean check) {
        mSymptomCommentModel.saveComment(symptomId,doctorId,comment,check);
    }

    @Override
    public void onReceiveSymptomSuccess(SymptomEntity entity) {
        if (null == entity.getRet_values()) {
            mISymptomPresenter.showError("获取数据出错，请重试！");
            return;
        }
        List<SymptomEntity.RetValuesEntity.ContentEntity> contentEntityList = entity.getRet_values().getContent();

        List<SymptomBean> symptomBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            SymptomEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);
            SymptomBean bean = new SymptomBean();
            bean.setComment(contentEntity.getDoctorRemark());
            bean.setSymptomId(contentEntity.getId());
            bean.setIsChecked(contentEntity.isChecked());
            bean.setSymptomStr(contentEntity.getSymptomContent());
            bean.setTimeStr(DateUtil.format(contentEntity.getRecordTime(),"yyyy-MM-dd"));

            symptomBeanList.add(bean);
        }

        mISymptomPresenter.showSymptomList(symptomBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mISymptomPresenter.showError(message);
    }

    @Override
    public void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity) {
        mISymptomPresenter.saveCommentSuccess();
    }
}
