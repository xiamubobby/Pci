package com.wonders.xlab.pci.mvn.view;

import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.BloodSugarBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SmokeBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.bean.WineBean;
import com.wonders.xlab.pci.mvn.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public interface DailyTaskView extends BaseView {
    void initWeekView(long today);

    void initMedicineRecordView(List<MedicineRecordBean> recordBeanList);

    void initSymptomView(List<SymptomBean> beanList);

    void initBloodPressure(List<BloodPressureBean> beanList);

    void initBloodSugar(List<BloodSugarBean> beanList);

    void initSmokeView(List<SmokeBean> beanList);

    void initWineView(List<WineBean> beanList);
}
