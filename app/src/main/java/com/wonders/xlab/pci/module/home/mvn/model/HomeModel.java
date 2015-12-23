package com.wonders.xlab.pci.module.home.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;
import com.wonders.xlab.pci.module.home.mvn.api.HomeAPI;
import com.wonders.xlab.pci.module.home.mvn.entity.HomeEntity;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.Calendar;
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

        List<HomeTaskBean> beanList = new ArrayList<>();

        TodayTaskBean todayTaskBean = new TodayTaskBean();
        todayTaskBean.setName("六二");
        todayTaskBean.setTitle("提醒");
        todayTaskBean.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        todayTaskBean.setPortrait(Constant.TEST_PORTRAIT);

        beanList.add(todayTaskBean);

        for (HomeEntity.RetValuesEntity.ContentEntity contentEntity : content) {

            YesterdayTaskBean yesterdayTaskBean = new YesterdayTaskBean();
            yesterdayTaskBean.setContent(contentEntity.getContent());
            yesterdayTaskBean.setTitle(contentEntity.getTitle());
            yesterdayTaskBean.setUpdateTime(contentEntity.getLastModifiedDate());
            yesterdayTaskBean.setName(contentEntity.getName());
            yesterdayTaskBean.setPortrait(contentEntity.getPortrait());
            beanList.add(yesterdayTaskBean);

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
