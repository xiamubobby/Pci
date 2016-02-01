package com.wonders.xlab.pci.module.mydoctor.mvn;

import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

/**
 * Created by hua on 16/1/28.
 */
public class MyDoctorModel extends BaseModel<MyDoctorEntity> {
    private MyDoctorAPI mMyDoctorAPI;
    private MyDoctorView mMyDoctorView;

    public MyDoctorModel(MyDoctorView myDoctorView) {
        mMyDoctorView = myDoctorView;
        mMyDoctorAPI = mRetrofit.create(MyDoctorAPI.class);
    }

    public void getDoctorList(String userId, String tel) {

       /* List<DoctorInfoEntity> doctorInfoEntityList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            DoctorInfoEntity doctorInfo = new DoctorInfoEntity();
            doctorInfo.setName("六二");
            doctorInfo.setHospitalname("瑞金医院");
            doctorInfo.setParentdepartname("心内科");
            doctorInfo.setTitle("住院医师");
            doctorInfo.setDes("医师简介");
            doctorInfo.setPic("http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg");

            doctorInfoEntityList.add(doctorInfo);
        }

        mMyDoctorView.showDoctorList(doctorInfoEntityList);
*/
        setObservable(mMyDoctorAPI.getDoctorList(userId, tel));
    }

    @Override
    protected void onSuccess(MyDoctorEntity response) {
        mMyDoctorView.hideLoading();
        if (response.getRet_code() == 0) {
            mMyDoctorView.showDoctorList(response.getRet_values());
        } else {
            mMyDoctorView.showError(response.getMessage());
        }
    }

    @Override
    protected void onFailed(String message) {
        mMyDoctorView.hideLoading();
        mMyDoctorView.showError(message);
    }
}
