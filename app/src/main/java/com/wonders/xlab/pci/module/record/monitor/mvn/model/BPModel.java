package com.wonders.xlab.pci.module.record.monitor.mvn.model;


import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.module.record.monitor.bean.BPNBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.api.BPNAPI;
import com.wonders.xlab.pci.mvn.entity.record.monitor.BPEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/21.
 */
public class BPModel extends BaseModel<BPEntity> {
    private BPView mBpView;
    private BPNAPI mBPAPI;

    public BPModel(BPView bpView) {
        mBpView = bpView;
        mBPAPI = mRetrofit.create(BPNAPI.class);
    }

    public void getBpData(String userId, Long startDate, Long endDate) {
        setObservable(mBPAPI.getBp(userId, startDate, endDate));
    }

    @Override
    protected void onSuccess(BPEntity response) {
        List<BPEntity.RetValuesEntity> entity = response.getRet_values();
        if (entity == null) {
            mBpView.onFailed("获取数据失败，请重试！");
            return;
        }
        long headerId = 0;
        List<BPNBean> list = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            BPEntity.RetValuesEntity contentEntity = entity.get(i);

            BPNBean bpBean = new BPNBean();
            bpBean.setDiastolicPressure(contentEntity.getDiastolicPressure() + "");
            bpBean.setSystolicPressure(contentEntity.getSystolicPressure() + "");
            bpBean.setHeartRate(contentEntity.getHeartRate() + "");
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

        mBpView.showBPlist(list);
    }


    @Override
    protected void onFailed(String message) {

    }
}
