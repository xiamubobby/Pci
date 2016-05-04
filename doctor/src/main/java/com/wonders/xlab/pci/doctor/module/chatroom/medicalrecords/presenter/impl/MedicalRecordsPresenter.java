package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.presenter.impl;

import android.databinding.ObservableField;

import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.presenter.IMedicalRecordsPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsPresenter extends BasePagePresenter implements IMedicalRecordsPresenter {

    private MedicalRecordsPresenterListener mListener;

    public MedicalRecordsPresenter(MedicalRecordsPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getMedicalRecordsList(String patientId, String doctorId) {
        List<MedicalRecordsBean> medicalRecordsBeanList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            MedicalRecordsBean bean = new MedicalRecordsBean();
            bean.setTime(new ObservableField<>(calendar.getTimeInMillis()));
            bean.setTag(new ObservableField<>("普通门诊"));
            bean.setHospitalName(new ObservableField<>("就诊医院：上海市闵行区卫生服务中心"));
            bean.setDepartmentName(new ObservableField<>("就诊部门：华坪全科"));
            bean.setMedicalResult(new ObservableField<>("就诊结果：果酸可以帮助消化，如果你消化不良,那我鼓励你饭后吃个水果。但如果你消化功能良好,饭后可能会帮倒忙.因为这不仅会加重消化负担。果酸可以帮助消化。如果你消化不良,那我鼓励你饭后吃个水果。\\n但如果你消化功能良好,饭后可能会帮倒忙.因为这不仅会加重消化负担。"));
            medicalRecordsBeanList.add(bean);
        }
        mListener.showMedicalRecordsList(medicalRecordsBeanList);
    }

    public interface MedicalRecordsPresenterListener extends BasePagePresenterListener {
        void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList);
    }
}
