package com.wonders.xlab.pci.module.record.monitor.mvn.model;


import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.module.record.monitor.bean.BpBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.api.BpAPI;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.BPEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;
import com.wonders.xlab.pci.mvn.model.BaseModel;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/21.
 */
public class BPModel extends BaseModel<BPEntity> {
    private BPView mBpView;
    private BpAPI mBpAPI;
    private int page = -1;
    private boolean isLast = false;

    public BPModel(BPView bpView){
        mBpView = bpView;
        mBpAPI = mRetrofit.create(BpAPI.class);
    }
    public void getBpData(String userId){
        if(!isLast){
            setObservable(mBpAPI.getBp(userId));
        }
    }

    @Override
    protected void onSuccess(BPEntity response) {
        BPEntity.RetValuesEntity entity = response.getRet_values();
        if (entity == null) {
           mBpView.onFailed("获取数据失败，请重试！");
            return;
        }
        isLast = entity.isLast();
        List<BpBean> list = new ArrayList<BpBean>();
        for (BPEntity.RetValuesEntity.ContentEntity contentEntity: entity.getContent()){
            BpBean bpBean = new BpBean();
            bpBean.setDiastolicPressure(contentEntity.getDiastolicPressure()+"");
            bpBean.setSystolicPressure(contentEntity.getSystolicPressure() + "");
            bpBean.setHeartRate(contentEntity.getHeartRate() + "");
            bpBean.setUserId(contentEntity.getId() + "");
            bpBean.setRecordTime(contentEntity.getRecordTime());
            list.add(bpBean);
        }
        long headerId = 0;
        for(int i = 0;i<20;i++){
            Long num = 1450690020000L-i*24*3600000;
            BpBean bpBean = new BpBean();
            bpBean.setDiastolicPressure("");
            bpBean.setSystolicPressure("");
            bpBean.setHeartRate("");
            bpBean.setUserId("");
            bpBean.setRecordTime(num);
            if (list != null) {
                for (BpBean bean : list) {
                    if (DateUtil.isTheSameDay(num, bean.getRecordTime())) {
                        bpBean.setHeaderId(bean.getHeaderId());
                        break;
                    } else {
                        if(headerId == 6){
                            bpBean.setHeaderId(0);
                        }else {
                            bpBean.setHeaderId(headerId + 1);
                        }
                    }
                }
            } else {
                bpBean.setHeaderId(headerId);
            }
            headerId++;
            if(headerId == 7){
                headerId = 0;
            }
            list.add(bpBean);
        }
        mBpView.showBplist(list);
    }

    @Override
    protected void onFailed(String message) {

    }
}