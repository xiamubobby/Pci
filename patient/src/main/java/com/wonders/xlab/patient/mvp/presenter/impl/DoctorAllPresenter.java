package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorAllModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;
import com.wonders.xlab.patient.mvp.model.listener.DoctorAllModelListener;
import com.wonders.xlab.patient.mvp.presenter.IDoctorAllPresenter;
import com.wonders.xlab.patient.mvp.presenter.listener.DoctorAllPresenterListener;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/3/15.
 */
public class DoctorAllPresenter extends BasePresenter implements IDoctorAllPresenter, DoctorAllModelListener {

    private DoctorAllPresenterListener mDoctorAllListener;
    private IDoctorAllModel mDoctorAllModel;

    public DoctorAllPresenter(DoctorAllPresenterListener doctorAllListener) {
        mDoctorAllListener = doctorAllListener;
        mDoctorAllModel = new DoctorAllModel(this);
    }

    public void getAllDoctors(String patientId) {
//        mDoctorAllModel.getAllDoctorList(patientId);
        onReceiveAllDoctorListSuccess(null);
    }

    @Override
    public void onReceiveAllDoctorListSuccess(List<DoctorAllEntity.RetValuesEntity> valuesEntity) {
        mDoctorAllListener.hideLoading();
        ArrayList<AllDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();

       /* for (DoctorAllEntity.RetValuesEntity entity : valuesEntity) {
            AllDoctorItemBean itemBean = new AllDoctorItemBean();

        }*/

        for (int i = 0; i < 20; i++) {
            AllDoctorItemBean itemBean = new AllDoctorItemBean();
            if (i % 3 == 0) {
                itemBean.setPersonal(true);
            } else {
                itemBean.setDoctorGroupName("刘" + i + "医师小组");
            }
            if (i < 3) {
                itemBean.setTagStr("已购买");
            } else if (i < 8) {
                itemBean.setTagStr("已过期");
            }

            itemBean.setAdminName("刘" + i + "医师");
            itemBean.setTitle("副主任医师");
            itemBean.setDepartment("心血管科室");
            itemBean.setHospital("XXX医院");
            itemBean.setPortraitUrl(Constant.DEFAULT_PORTRAIT);

            ArrayList<String> serviceIconUrlList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                serviceIconUrlList.add("http://www.easyicon.net/api/resizeApi.php?id=1182002&size=72");
            }
            itemBean.setServiceIconUrl(serviceIconUrlList);

            doctorItemBeanArrayList.add(itemBean);
        }

        mDoctorAllListener.showAllDoctorList(doctorItemBeanArrayList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorAllListener.hideLoading();
        mDoctorAllListener.showError(message);
    }
}
