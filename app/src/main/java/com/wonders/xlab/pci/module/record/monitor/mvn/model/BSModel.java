package com.wonders.xlab.pci.module.record.monitor.mvn.model;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.module.record.monitor.bean.BSBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.api.BSAPI;
import com.wonders.xlab.pci.mvn.entity.record.monitor.BSEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BSView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSModel extends BaseModel<BSEntity> {
    private BSAPI mBSAPI;
    private BSView mBSView;

    public BSModel(BSView mBSView){
        this.mBSView = mBSView;
        mBSAPI = mRetrofit.create(BSAPI.class);

    }

    public void getBSData(String userId, Long startDate, Long endDate) {
        setObservable(mBSAPI.getBS(userId, startDate, endDate));
    }

    @Override
    protected void onSuccess(BSEntity response) {
        List<BSEntity.RetValuesEntity> entity = response.getRet_values();
        if (entity == null) {
            mBSView.onFailed("获取数据失败，请重试！");
            return;
        }
        long headerId = 0;
        List<BSBean> list = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            BSEntity.RetValuesEntity contentEntity = entity.get(i);

            BSBean bpBean = new BSBean();
            bpBean.setBs(contentEntity.getBloodSugar() + "");
            bpBean.setRecordTime(contentEntity.getRecordTime());

            if (list.size() > 0) {
                if (DateUtil.isTheSameDay(contentEntity.getRecordTime(), list.get(i - 1).getRecordTime())) {
                    bpBean.setHeaderId(headerId - 1);
                } else {
                    bpBean.setHeaderId(headerId++);
                }
            } else {
                bpBean.setHeaderId(headerId++);
            }
            list.add(bpBean);
        }

        mBSView.showBSlist(list);
    }

    @Override
    protected void onFailed(String message) {

    }
}
