package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public interface MedicineView extends BaseView {
    void showMedicineRecords(List<MedicineCategoryBean> categoryBeanList);

    void onFailed(String message);
}
