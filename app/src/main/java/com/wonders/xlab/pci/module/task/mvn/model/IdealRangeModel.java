package com.wonders.xlab.pci.module.task.mvn.model;

import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.task.mvn.api.IdealRangeAPI;
import com.wonders.xlab.pci.module.task.mvn.view.IdealRangeView;

/**
 * Created by hua on 16/1/6.
 */
public class IdealRangeModel extends BaseModel<SimpleEntity> {
    private IdealRangeAPI mIdealRangeAPI;

    private IdealRangeView mIdealRangeView;

    public IdealRangeModel(IdealRangeView idealRangeView) {
        mIdealRangeView = idealRangeView;
        mIdealRangeAPI = mRetrofit.create(IdealRangeAPI.class);

    }

    /**
     * 获取血压理想范围
     *
     * @param userId
     */
    public void fetchIdealBPRange(String userId) {
        setObservable(mIdealRangeAPI.fetchIdealBPRange(userId));
    }

    /**
     * 获取血糖理想范围
     *
     * @param userId
     */
    public void fetchIdealBSRange(String userId) {
        setObservable(mIdealRangeAPI.fetchIdealBSRange(userId));
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        if (response.getRet_code() == 0) {
            mIdealRangeView.showRange(response.getRet_values());
        } else {
            mIdealRangeView.fetchIdealRangeFailed(response.getMessage());
        }
    }

    @Override
    protected void onFailed(String message) {
        mIdealRangeView.fetchIdealRangeFailed(message);
    }
}
