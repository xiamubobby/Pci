package com.wonders.xlab.pci.module.record.monitor.mvn.model;


import android.util.Log;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.module.record.monitor.bean.BpBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.api.BpAPI;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.NewBPEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;
import com.wonders.xlab.pci.mvn.model.BaseModel;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hua on 15/12/21.
 */
public class BPModel extends BaseModel<NewBPEntity> {
    private BPView mBpView;
    private BpAPI mBpAPI;

    public BPModel(BPView bpView){
        mBpView = bpView;
        mBpAPI = mRetrofit.create(BpAPI.class);
    }
    public void getBpData(String userId,Long startDate,Long endDate){
            setObservable(mBpAPI.getBp(userId, startDate,endDate));            ;
    }

    @Override
    protected void onSuccess(NewBPEntity response) {
       List<NewBPEntity.RetValuesEntity> entity = response.getRet_values();
        if (entity == null) {
           mBpView.onFailed("获取数据失败，请重试！");
            return;
        }
        long headerId = 0;
        List<BpBean> list = new ArrayList<BpBean>();
        for (NewBPEntity.RetValuesEntity contentEntity: entity){
            BpBean bpBean = new BpBean();
            bpBean.setDiastolicPressure(contentEntity.getDiastolicPressure() + "");
            bpBean.setSystolicPressure(contentEntity.getSystolicPressure() + "");
            bpBean.setHeartRate(contentEntity.getHeartRate() + "");
            bpBean.setUserId(contentEntity.getId() + "");
            bpBean.setRecordTime(contentEntity.getRecordTime());
            bpBean.setHeaderId(headerId);
           /* if(list!= null){
                if(DateUtil.isTheSameDay(contentEntity.getRecordTime(),list.get(list.size()-1).getRecordTime())){
                    bpBean.setHeaderId(headerId);
                }
                else {
                    bpBean.setHeaderId(headerId+1);
                    headerId++;
                }
            }
            else {
                bpBean.setHeaderId(headerId);
                headerId++;
            }*/
            Log.i("111", bpBean.toString());
            list.add(bpBean);
        }

        mBpView.showBplist(list);
    }


    @Override
    protected void onFailed(String message) {

    }
}
