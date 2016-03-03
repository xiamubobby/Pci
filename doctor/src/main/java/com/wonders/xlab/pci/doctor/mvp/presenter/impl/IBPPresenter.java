package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.github.mikephil.charting.data.CombinedData;
import com.wonders.xlab.pci.doctor.module.bp.bean.BPListBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public interface IBPPresenter extends IBasePresenter {
    void showBloodPressureList(ArrayList<BPListBean> bpListBeanList, CombinedData combinedData);
}
