package com.wonders.xlab.pci.doctor.module.patient.model;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.module.networkentity.PatientEntity;
import com.wonders.xlab.pci.doctor.module.patient.api.PatientAPI;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.module.patient.view.PatientView;

import java.util.ArrayList;

/**
 * Created by hua on 16/2/19.
 */
public class PatientModel extends DoctorBaseModel<PatientEntity> {

    private PatientAPI mPatientAPI;
    private PatientView mPatientView;

    public PatientModel(PatientView patientView) {
        mPatientView = patientView;
        mPatientAPI = mRetrofit.create(PatientAPI.class);
    }

    public void getPatientList() {
        onSuccess(null);
    }

    @Override
    protected void onSuccess(PatientEntity response) {

        ArrayList<PatientBean> patientBeen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PatientBean bean = new PatientBean();
            bean.setPatientId("" + i);
            bean.setAge("1" + i);
            bean.setGender("男");
            bean.setHistory("稳定性心绞痛");
            bean.setPortrait(Constant.DEFAULT_PORTRAIT);
            bean.setUsername("张三");
            bean.setTimeAfterSurgery("术后六个月");

            patientBeen.add(bean);
        }

        mPatientView.showPatientList(patientBeen);

    }

    @Override
    protected void onFailed(String message) {

    }
}
