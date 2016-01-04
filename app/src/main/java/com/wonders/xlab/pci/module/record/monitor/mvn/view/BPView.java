package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.module.record.monitor.bean.BPBean;
import com.wonders.xlab.pci.module.base.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/21.
 */
public interface BPView extends BaseView {

    void showBPlist(List<BPBean> bpBeanList);

    void onFailed(String message);
}
