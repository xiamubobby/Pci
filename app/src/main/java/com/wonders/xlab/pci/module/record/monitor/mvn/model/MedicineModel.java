package com.wonders.xlab.pci.module.record.monitor.mvn.model;

import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryChildBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.MedicineEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.MedicineView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineModel extends BaseModel<MedicineEntity> {
    private MedicineView mMedicineView;

    public MedicineModel(MedicineView medicineView) {
        mMedicineView = medicineView;
    }

    public void getMedicineRecords() {
        onSuccess(null);
    }

    @Override
    protected void onSuccess(MedicineEntity response) {
        Calendar calendar = Calendar.getInstance();
        List<MedicineCategoryBean> categoryBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MedicineCategoryBean categoryBean = new MedicineCategoryBean();
            List<MedicineCategoryChildBean> childBeanList = new ArrayList<>();
            for (int j = 0; j < i + 4; j++) {
                MedicineCategoryChildBean childBean;
                if (j == 0) {
                    childBean = new MedicineCategoryChildBean(true);
                } else {
                    childBean = new MedicineCategoryChildBean(false);
                }
                childBean.setCounts(String.valueOf(j));
                childBean.setTime(calendar.getTimeInMillis());
                childBean.setValue(String.valueOf(j));
                childBeanList.add(childBean);
            }
            categoryBean.setChildBeanList(childBeanList);
            categoryBean.setTitle("title" + i);
            categoryBeanList.add(categoryBean);
        }
        mMedicineView.showMedicineRecords(categoryBeanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
