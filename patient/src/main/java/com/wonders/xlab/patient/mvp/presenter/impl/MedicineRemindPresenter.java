package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindBean;
import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindDataBean;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindEntity;
import com.wonders.xlab.patient.mvp.model.impl.MedicineRemindModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindPresenter extends BasePagePresenter implements MedicineRemindModel.MedicineRemindModelListener {

    private MedicineRemindPresenterListener mPresenterListener;
    private MedicineRemindModel mMedicineRemindModel;

    public MedicineRemindPresenter(MedicineRemindPresenterListener iMedicineRemindPresenter) {
        mPresenterListener = iMedicineRemindPresenter;
        mMedicineRemindModel = new MedicineRemindModel(this);
        addModel(mMedicineRemindModel);
    }

    public void getMedicineRemindList(String userId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }

        if (mIsLast) {
            mPresenterListener.hideLoading();
            mPresenterListener.showReachTheLastPageNotice("");
            return;
        }
//        mMedicineRemindModel.getMedicalRecordList(userId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
        //测试数据
        MedicineRemindEntity entity = new MedicineRemindEntity();
        List<MedicineRemindEntity.RetValuesEntity> ret_values = new ArrayList<>();
        MedicineRemindEntity.RetValuesEntity valuesEntity = new MedicineRemindEntity.RetValuesEntity();
        valuesEntity.setEndDate("2016-05-23");
        valuesEntity.setExpire(false);
        valuesEntity.setId(1);
        valuesEntity.setRemindersDesc("药不能停");
        valuesEntity.setRemindersTime("10:00");
        valuesEntity.setStartDate("2016-04-23");
        List<MedicineRemindEntity.RetValuesEntity.MedicationUsagesEntity> usagesEntities = new ArrayList<>();
        MedicineRemindEntity.RetValuesEntity.MedicationUsagesEntity usagesEntity = new MedicineRemindEntity.RetValuesEntity.MedicationUsagesEntity();
        usagesEntity.setId(1);
        usagesEntity.setMedicationName("阿司匹林");
        usagesEntity.setMedicationNum(3);
        usagesEntity.setPharmaceuticalUnit("片");
        usagesEntity.setUse(true);
        usagesEntities.add(usagesEntity);
        usagesEntities.add(usagesEntity);
        usagesEntities.add(usagesEntity);
        usagesEntities.add(usagesEntity);
        usagesEntities.add(usagesEntity);
        valuesEntity.setMedicationUsages(usagesEntities);
        ret_values.add(valuesEntity);
        ret_values.add(valuesEntity);
        entity.setRet_values(ret_values);
        onReceiveMedicalRecordSuccess(entity);
    }

    @Override
    public void onReceiveMedicalRecordSuccess(MedicineRemindEntity entity) {
        mPresenterListener.hideLoading();

        List<MedicineRemindBean> beanList = new ArrayList<>();

        for (int i = 0; i < entity.getRet_values().size(); i++) {
            MedicineRemindEntity.RetValuesEntity valueEntity = entity.getRet_values().get(i);
            MedicineRemindDataBean bean = new MedicineRemindDataBean();
            bean.setId(valueEntity.getId());
            String[] num = valueEntity.getRemindersTime().split(":");
            int hour = Integer.parseInt(num[0]);
            String time = "";
            if (hour > 12) {
                int temp = hour - 12;
                if (temp > 9) {
                    time = temp + ":" + num[1];
                } else {
                    time = "0" + temp + ":" + num[1];
                }
                bean.setTimePeriod("下午");
                bean.setTimeStr(time);
            } else if (hour == 12) {
                bean.setTimePeriod("中午");
                bean.setTimeStr(valueEntity.getRemindersTime());
            } else {

                int temp1 = hour;
                if (temp1 > 9) {
                    time = temp1 + ":" + num[1];
                } else {
                    time = "0" + temp1 + ":" + num[1];
                }
                bean.setTimePeriod("上午");
                bean.setTimeStr(time);
            }
            List<MedicineRemindDataBean.Medicine> medicines = new ArrayList<>();
            for (MedicineRemindEntity.RetValuesEntity.MedicationUsagesEntity usagesEntity : valueEntity.getMedicationUsages()) {
                MedicineRemindDataBean.Medicine medicine = new MedicineRemindDataBean.Medicine();
                medicine.setId(usagesEntity.getId());
                medicine.setMedicineName(usagesEntity.getMedicationName());
                medicine.setMedicineNum(usagesEntity.getMedicationNum());
                medicine.setMedicineUnit(usagesEntity.getPharmaceuticalUnit());
                medicine.setUse(usagesEntity.isUse());
                medicines.add(medicine);
            }

            bean.setMedicines(medicines);
            bean.setEndTime(valueEntity.getEndDate());
            bean.setRemind(valueEntity.isExpire());

            beanList.add(bean);
        }
        if (shouldAppend()) {
            mPresenterListener.appendMedicineRemindList(beanList);
        } else {
            mPresenterListener.showMedicineRemindList(beanList);
        }
    }

    @Override
    public void noMoreData(String message) {
        mPresenterListener.hideLoading();
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mPresenterListener.hideLoading();
        mPresenterListener.showNetworkError(message);
    }

    public interface MedicineRemindPresenterListener extends BasePresenterListener {
        void showMedicineRemindList(List<MedicineRemindBean> beanList);

        void appendMedicineRemindList(List<MedicineRemindBean> beanList);

        void showReachTheLastPageNotice(String message);
    }
}
