package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.HomeAPI;
import com.wonders.xlab.pci.mvn.entity.home.HomeEntity;
import com.wonders.xlab.pci.mvn.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class HomeModel extends BaseModel<HomeEntity> {
    private HomeView mHomeView;
    private HomeAPI mHomeAPI;

    public HomeModel(HomeView homeView) {
        mHomeView = homeView;
        mHomeAPI = mRetrofit.create(HomeAPI.class);
    }

    public void getHomeList() {
        setObservable(mHomeAPI.getHomeList());
//        onSuccess(null);
    }

    @Override
    protected void onSuccess(@NonNull HomeEntity response) {
        mHomeView.hideLoading();
        HomeEntity.RetValuesEntity valuesEntity = response.getRet_values();

        if (valuesEntity == null) {
            return;
        }
        List<HomeEntity.RetValuesEntity.ContentEntity> content = valuesEntity.getContent();
        if (content == null) {
            return;
        }

        List<HomeTaskBean> beanList = new ArrayList<>();

        for (HomeEntity.RetValuesEntity.ContentEntity contentEntity : content) {

            YesterdayTaskBean yesterdayTaskBean = new YesterdayTaskBean();
            yesterdayTaskBean.setContent(contentEntity.getContent());
            yesterdayTaskBean.setTitle(contentEntity.getTitle());
            yesterdayTaskBean.setUpdateTime(contentEntity.getLastModifiedDate());
            yesterdayTaskBean.setName(contentEntity.getName());
            yesterdayTaskBean.setPortrait(contentEntity.getPortrait());
            beanList.add(yesterdayTaskBean);

        }
        mHomeView.showHomeList(beanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
