package com.wonders.xlab.pci.doctor.mvp.presenter.listener;

import com.github.mikephil.charting.data.CombinedData;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.bean.BPListBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public interface BPPresenterListener extends BasePresenterListener {
    void showBloodPressureList(ArrayList<BPListBean> bpListBeanList, CombinedData combinedData);
}
