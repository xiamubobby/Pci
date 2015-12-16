package com.wonders.xlab.pci.mvn.view;

import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.mvn.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public interface DailyTaskView extends BaseView{
    void initWeekView(long today);

    void initMedicineRecordView(List<MedicineRecordBean> recordBeanList);

    void initSymptomView(List<SymptomBean> beanList);

    void initBloodPressure(List<BloodPressureBean> beanList);
}
