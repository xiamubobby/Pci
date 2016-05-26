package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/23.
 */
public interface DoctorDetailContract {
    interface ViewListener extends BasePresenterListener{
        void showBasicInfo(DoctorBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);

        void showMemberOrGroupOfDoctorRV();

        void hideMemberOrGroupOfDoctorRV();

        void startPayment(String charge);

        void refreshView();

        /**
         * 小组成员有变动，服务过期，需要重新获取列表，然后进入重新购买
         * @param message
         */
        void doctorGroupExpired(String message);
    }
}
