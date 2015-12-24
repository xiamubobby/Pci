package com.wonders.xlab.pci.module.record.monitor.mvn.model;

import com.wonders.xlab.pci.module.record.monitor.mvn.api.BSAPI;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.BSEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BSView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSModel extends BaseModel<BSEntity> {
    private BSAPI mBsAPI;
    private BSView bsView;

    public BSModel(BSView bsView){
        this.bsView = bsView;
        mBsAPI = mRetrofit.create(BSAPI.class);

    }


    @Override
    protected void onSuccess(BSEntity response) {

    }

    @Override
    protected void onFailed(String message) {

    }
}
