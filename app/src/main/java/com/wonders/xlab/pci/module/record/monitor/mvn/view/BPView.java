package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.module.record.bean.BpBean;
import com.wonders.xlab.pci.mvn.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/21.
 */
public interface BPView extends BaseView {


    void showBplist(List<BpBean> bpBeanList);

    void onFailed(String message);
}
