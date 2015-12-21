package com.wonders.xlab.pci.mvn.model.task;

import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.task.DailyTaskAPI;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.mvn.view.task.TakeMedicineView;

/**
 * Created by hua on 15/12/21.
 */
public class TakeMedicineModel extends BaseModel<SimpleEntity> {
    private TakeMedicineView mMedicineView;
    private DailyTaskAPI mDailyTaskAPI;

    public TakeMedicineModel(TakeMedicineView medicineView) {
        mMedicineView = medicineView;
        mDailyTaskAPI = mRetrofit.create(DailyTaskAPI.class);
    }

    public void takeMedicine(String medicineId) {
        setObservable(mDailyTaskAPI.takeMedicine(medicineId));
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
    }

    @Override
    protected void onFailed(String message) {
        mMedicineView.takeMedicineFailed("保存失败，请重试！");
    }
}
