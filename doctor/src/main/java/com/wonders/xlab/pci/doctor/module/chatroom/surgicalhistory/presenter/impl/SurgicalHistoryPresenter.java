package com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.presenter.impl;

import android.databinding.ObservableField;

import com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.presenter.ISurgicalHistoryPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/3.
 */
public class SurgicalHistoryPresenter extends BasePagePresenter implements ISurgicalHistoryPresenter {

    private SurgicalHistoryPresenterListener mlistener;

    public SurgicalHistoryPresenter(SurgicalHistoryPresenterListener listener) {
        mlistener = listener;
    }

    @Override
    public void getSurgicalHistoryList(String patientId, String doctorId) {
        Calendar calendar = Calendar.getInstance();
        List<SurgicalHistoryBean> surgicalHistoryBeanList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SurgicalHistoryBean bean = new SurgicalHistoryBean();
            bean.setSurgicalHistoryBeginTime(new ObservableField<>(calendar.getTimeInMillis()));
            bean.setSurgicalHistoryBeginTime(new ObservableField<>(calendar.getTimeInMillis()));
            bean.setHospitalName(new ObservableField<>("医院名称：上海市第六人民医院临港新城分院"));
            bean.setDepartmentName(new ObservableField<>("科室名称：内分泌代谢科"));
            bean.setLeaveHospitalDiagnostics(new ObservableField<>("医院诊断：急性上呼吸道感染"));
            bean.setDoctorSuggestion(new ObservableField<>("医生建议：果酸可以帮助消化，如果你消化不良,那我鼓励你饭后吃个水果。但如果你消化功能良好,饭后可能会帮倒忙.因为这不仅会加重消化负担。果酸可以帮助消化。如果你消化不良,那我鼓励你饭后吃个水果。\\n但如果你消化功能良好,饭后可能会帮倒忙.因为这不仅会加重消化负担。"));
            surgicalHistoryBeanList.add(bean);
        }
        mlistener.showSurgicalHistoryList(surgicalHistoryBeanList);

    }

    public interface SurgicalHistoryPresenterListener extends BasePagePresenterListener {
        void showSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList);
    }

}
