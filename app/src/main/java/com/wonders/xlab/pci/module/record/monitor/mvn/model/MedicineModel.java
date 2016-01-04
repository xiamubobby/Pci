package com.wonders.xlab.pci.module.record.monitor.mvn.model;

import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryChildBean;
import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.MedicineEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.MedicineView;
import com.wonders.xlab.pci.module.task.mvn.api.MedicineAPI;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineModel extends BaseModel<MedicineEntity> {
    private MedicineView mMedicineView;
    private MedicineAPI mMedicineAPI;

    public MedicineModel(MedicineView medicineView) {
        mMedicineView = medicineView;
        mMedicineAPI = mRetrofit.create(MedicineAPI.class);
    }

    public void getMedicineRecords(String userId) {
        setObservable(mMedicineAPI.getMedicines(userId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMedicineView.showLoading();
    }

    @Override
    protected void onSuccess(MedicineEntity response) {
        mMedicineView.hideLoading();
        List<MedicineEntity.RetValuesEntity> ret_values = response.getRet_values();
        if (ret_values == null) {
            mMedicineView.onFailed("获取数据失败，请重试!");
            return;
        }

        List<MedicineCategoryBean> categoryBeanList = new ArrayList<>();

        for (int i = 0; i < ret_values.size(); i++) {
            MedicineCategoryBean categoryBean = new MedicineCategoryBean();
            categoryBean.setTitle(ret_values.get(i).getName());

            List<MedicineEntity.RetValuesEntity.UserMedicineRecordsEntity> userMedicineRecords = ret_values.get(i).getUserMedicineRecords();
            if (userMedicineRecords != null) {
                List<MedicineCategoryChildBean> childBeanList = new ArrayList<>();

                for (int j = 0; j < userMedicineRecords.size(); j++) {
                    MedicineEntity.RetValuesEntity.UserMedicineRecordsEntity userMedicineRecordsEntity = userMedicineRecords.get(j);
                    MedicineCategoryChildBean childBean;
                    if (j == 0) {
                        childBean = new MedicineCategoryChildBean(true);
                    } else {
                        childBean = new MedicineCategoryChildBean(false);
                    }
                    childBean.setCounts(String.valueOf(userMedicineRecordsEntity.getDoseCount()));
                    childBean.setTime(userMedicineRecordsEntity.getRecordTime());
                    childBean.setValue(userMedicineRecordsEntity.getMeasurement());
                    childBeanList.add(childBean);
                }
                categoryBean.setChildBeanList(childBeanList);
            }
            categoryBeanList.add(categoryBean);
        }
        mMedicineView.showMedicineRecords(categoryBeanList);
    }

    @Override
    protected void onFailed(String message) {
        mMedicineView.hideLoading();
        mMedicineView.onFailed(message);
    }
}
