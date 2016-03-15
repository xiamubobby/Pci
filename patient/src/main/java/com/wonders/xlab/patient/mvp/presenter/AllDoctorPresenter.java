package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.impl.IAllDoctorPresenter;

import java.util.ArrayList;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/15.
 */
public class AllDoctorPresenter extends BasePresenter {

    private IAllDoctorPresenter mIAllDoctorPresenter;

    public AllDoctorPresenter(IAllDoctorPresenter iAllDoctorPresenter) {
        mIAllDoctorPresenter = iAllDoctorPresenter;
    }

    public void getAllDoctor() {
        ArrayList<AllDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();
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

        mIAllDoctorPresenter.showAllDoctorList(doctorItemBeanArrayList);
    }
}
