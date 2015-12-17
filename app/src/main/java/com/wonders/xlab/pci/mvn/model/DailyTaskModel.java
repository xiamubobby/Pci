package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.BloodSugarBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SmokeBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.bean.WineBean;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.entity.DailyTaskEntity;
import com.wonders.xlab.pci.mvn.view.DailyTaskView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public class DailyTaskModel extends BaseModel<DailyTaskEntity> {
    private DailyTaskView mDailyTaskView;

    public DailyTaskModel(DailyTaskView dailyTaskView) {
        mDailyTaskView = dailyTaskView;
    }

    public void fetchData() {
        onSuccess(new DailyTaskEntity());
    }

    @Override
    protected void onSuccess(@NonNull DailyTaskEntity response) {

        //日期
        mDailyTaskView.initWeekView(Calendar.getInstance().getTimeInMillis());

        //药物记录
        List<MedicineRecordBean> recordBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MedicineRecordBean bean = new MedicineRecordBean("name" + i, String.valueOf(i));
            recordBeanList.add(bean);
        }
        mDailyTaskView.initMedicineRecordView(recordBeanList);

        //主诉症状
        List<SymptomBean> symptomBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SymptomBean bean = new SymptomBean("symptom" + i);
            symptomBeanList.add(bean);
        }
        mDailyTaskView.initSymptomView(symptomBeanList);

        Calendar calendar = Calendar.getInstance();
        long nowTime = calendar.getTimeInMillis();
        //血压
        List<BloodPressureBean> pressureBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BloodPressureBean bean = new BloodPressureBean((float) Math.random() * 150, (float) Math.random() * 150, (float) Math.random() * 150, (nowTime + i * 60000));
            pressureBeanList.add(bean);
        }
        mDailyTaskView.initBloodPressure(pressureBeanList);

        //血糖
        List<BloodSugarBean> bloodSugarBeen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BloodSugarBean bean = new BloodSugarBean((float) Math.random() * 150, (nowTime + i * 60000));
            bloodSugarBeen.add(bean);
        }
        mDailyTaskView.initBloodSugar(bloodSugarBeen);

        //吸烟
        List<SmokeBean> smokeBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SmokeBean bean = new SmokeBean(calendar.getTimeInMillis(), i + "根香烟");
            smokeBeanList.add(bean);
        }
        mDailyTaskView.initSmokeView(smokeBeanList);
        //喝酒
        List<WineBean> wineBeanList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WineBean bean = new WineBean(calendar.getTimeInMillis(), i + "杯白酒");
            wineBeanList.add(bean);
        }
        mDailyTaskView.initWineView(wineBeanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
