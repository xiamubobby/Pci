package com.wonders.xlab.pci.mvn.model.task;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.BloodSugarBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SmokeBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.bean.WineBean;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.task.DailyTaskAPI;
import com.wonders.xlab.pci.mvn.entity.task.DailyTaskEntity;
import com.wonders.xlab.pci.mvn.view.task.DailyTaskView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public class DailyTaskModel extends BaseModel<DailyTaskEntity> {
    private DailyTaskView mDailyTaskView;
    private DailyTaskAPI mDailyTaskAPI;

    public DailyTaskModel(DailyTaskView dailyTaskView) {
        mDailyTaskView = dailyTaskView;
        mDailyTaskAPI = mRetrofit.create(DailyTaskAPI.class);
    }

    public void fetchData(String userId, Long date) {
        setObservable(mDailyTaskAPI.getDailyTask(userId, date));
    }

    @Override
    protected void onSuccess(@NonNull DailyTaskEntity response) {
        mDailyTaskView.hideLoading();

        DailyTaskEntity.RetValuesEntity valuesEntity = response.getRet_values();
        if (valuesEntity == null) {
            return;
        }
        //日期
        mDailyTaskView.initWeekView(valuesEntity.getCurrentDate(), String.valueOf(valuesEntity.getWeekCount()));

        DailyTaskEntity.RetValuesEntity.UserMedicineMapEntity medicineEntity = valuesEntity.getUserMedicineMap();
        if (medicineEntity != null) {
            //药物记录
            //早
            List<MedicineRecordBean> morningMedicine = new ArrayList<>();
            List<DailyTaskEntity.RetValuesEntity.UserMedicineMapEntity.MorningEntity> morning = medicineEntity.getMorning();
            if (morning != null) {
                for (int i = 0;i < morning.size(); i++) {
                    MedicineRecordBean bean = new MedicineRecordBean(String.valueOf(morning.get(i).getId()),morning.get(i).getMedicineName(), morning.get(i).getMeasurement());
                    bean.setChecked(morning.get(i).isTake());
                    morningMedicine.add(bean);
                }
            }

            //午
            List<MedicineRecordBean> noonMedicine = new ArrayList<>();
            List<DailyTaskEntity.RetValuesEntity.UserMedicineMapEntity.NoonEntity> noon = medicineEntity.getNoon();
            if (noon != null) {
                for (int i = 0; i < noon.size(); i++) {
                    MedicineRecordBean bean = new MedicineRecordBean(String.valueOf(noon.get(i).getId()),noon.get(i).getMedicineName(), noon.get(i).getMeasurement());
                    bean.setChecked(noon.get(i).isTake());
                    noonMedicine.add(bean);
                }
            }

            //晚
            List<MedicineRecordBean> nightMedicine = new ArrayList<>();
            List<DailyTaskEntity.RetValuesEntity.UserMedicineMapEntity.EveningEntity> night = medicineEntity.getEvening();
            if (night != null) {
                for (int i = 0; i < night.size(); i++) {
                    MedicineRecordBean bean = new MedicineRecordBean(String.valueOf(night.get(i).getId()),night.get(i).getMedicineName(), night.get(i).getMeasurement());
                    bean.setChecked(night.get(i).isTake());
                    nightMedicine.add(bean);
                }
            }
            mDailyTaskView.initMedicineRecordView(morningMedicine, noonMedicine, nightMedicine);
        }

        //主诉症状
        List<SymptomBean> symptomBeanList = new ArrayList<>();
        List<String> symptoms = valuesEntity.getSymptoms();
        if (symptoms != null) {
            for (int i = 0; i < symptoms.size(); i++) {
                SymptomBean bean = new SymptomBean(symptoms.get(i));
                symptomBeanList.add(bean);
            }
        }
        mDailyTaskView.initSymptomView(symptomBeanList);

        //血压
        List<BloodPressureBean> pressureBeanList = new ArrayList<>();
        List<DailyTaskEntity.RetValuesEntity.UserBloodPressuresEntity> userBloodPressures = valuesEntity.getUserBloodPressures();
        if (userBloodPressures != null) {
            for (int i = 0; i < userBloodPressures.size(); i++) {
                BloodPressureBean bean = new BloodPressureBean(userBloodPressures.get(i).getSystolicPressure(), userBloodPressures.get(i).getDiastolicPressure(), userBloodPressures.get(i).getHeartRate(), userBloodPressures.get(i).getRecordTime());
                pressureBeanList.add(bean);
            }
        }

        mDailyTaskView.initBloodPressure(pressureBeanList);

        //血糖
        List<BloodSugarBean> bloodSugarBeen = new ArrayList<>();
        List<DailyTaskEntity.RetValuesEntity.UserBloodSugarsEntity> sugarsEntityList = valuesEntity.getUserBloodSugars();
        if (sugarsEntityList != null) {
            for (int i = 0; i < sugarsEntityList.size(); i++) {
                BloodSugarBean bean = new BloodSugarBean(sugarsEntityList.get(i).getBloodSuger(), sugarsEntityList.get(i).getRecordTime());
                bloodSugarBeen.add(bean);
            }
        }
        mDailyTaskView.initBloodSugar(bloodSugarBeen);

        //吸烟
        List<SmokeBean> smokeBeanList = new ArrayList<>();
        List<DailyTaskEntity.RetValuesEntity.UserSmokeDtosEntity> smokeEntityList = valuesEntity.getUserSmokeDtos();
        if (smokeEntityList != null) {
            for (int i = 0; i < smokeEntityList.size(); i++) {
                SmokeBean bean = new SmokeBean(smokeEntityList.get(i).getRecordTime(), smokeEntityList.get(i).getValue());
                smokeBeanList.add(bean);
            }
        }
        mDailyTaskView.initSmokeView(smokeBeanList);
        //喝酒
        List<WineBean> wineBeanList = new ArrayList<>();
        List<DailyTaskEntity.RetValuesEntity.UserWineDtosEntity> wineEntityList = valuesEntity.getUserWineDtos();
        if (wineEntityList != null) {
            for (int i = 0; i < wineEntityList.size(); i++) {
                WineBean bean = new WineBean(wineEntityList.get(i).getRecordTime(), wineEntityList.get(i).getValue());
                wineBeanList.add(bean);
            }
        }
        mDailyTaskView.initWineView(wineBeanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
