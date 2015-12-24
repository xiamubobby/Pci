package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.module.record.monitor.bean.BSBean;
import com.wonders.xlab.pci.mvn.view.BaseView;

import java.util.List;

/**
 * Created by sjy on 2015/12/23.
 */
public interface BSView extends BaseView {

    void showBSlist(List<BSBean> bpBeanList);

    void onFailed(String message);
}
