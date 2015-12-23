package com.wonders.xlab.pci.module.home.mvn.model;

import android.support.annotation.NonNull;

import com.activeandroid.query.Delete;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;
import com.wonders.xlab.pci.module.home.mvn.api.HomeAPI;
import com.wonders.xlab.pci.module.home.mvn.entity.HomeEntity;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class HomeModel extends BaseModel<HomeEntity> {
    private HomeView mHomeView;
    private HomeAPI mHomeAPI;
    private int page;
    private boolean isLastPage = false;

    public HomeModel(HomeView homeView) {
        mHomeView = homeView;
        mHomeAPI = mRetrofit.create(HomeAPI.class);
        page = -1;
    }

    public void getHomeList(String userId) {
        if (!isLastPage) {
            setObservable(mHomeAPI.getHomeList(userId,page + 1));
        }
    }

    @Override
    protected void onSuccess(@NonNull HomeEntity response) {
        mHomeView.hideLoading();
        HomeEntity.RetValuesEntity valuesEntity = response.getRet_values();

        if (valuesEntity == null) {
            mHomeView.showError("获取数据失败，请重试！");
            return;
        }

        page = valuesEntity.getNumber();
        isLastPage = valuesEntity.isLast();

        List<HomeEntity.RetValuesEntity.ContentEntity> content = valuesEntity.getContent();
        if (content == null) {
            mHomeView.showError("获取数据失败，请重试！");
            return;
        }

        //delete cache first
        new Delete().from(YesterdayTaskBean.class).execute();

        List<HomeTaskBean> beanList = new ArrayList<>();

        for (HomeEntity.RetValuesEntity.ContentEntity contentEntity : content) {

            YesterdayTaskBean yesterdayTaskBean = new YesterdayTaskBean();
            yesterdayTaskBean.setContent(contentEntity.getContent());
            yesterdayTaskBean.setTitle(contentEntity.getTitle());
            yesterdayTaskBean.setUpdateTime(contentEntity.getLastModifiedDate());
            yesterdayTaskBean.setName(contentEntity.getName());
            yesterdayTaskBean.setPortrait(contentEntity.getPortrait());
            beanList.add(yesterdayTaskBean);

            yesterdayTaskBean.save();

        }

        if (page > 0) {
            mHomeView.appendHomeList(beanList);
        } else {
            mHomeView.showHomeList(beanList);
        }
    }

    @Override
    protected void onFailed(String message) {
        mHomeView.hideLoading();
        mHomeView.showError(message);
    }
}
