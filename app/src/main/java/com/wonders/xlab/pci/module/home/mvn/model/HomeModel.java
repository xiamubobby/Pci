package com.wonders.xlab.pci.module.home.mvn.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.wonders.xlab.common.utils.NetworkUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.mvn.api.HomeAPI;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;
import com.wonders.xlab.pci.module.base.mvn.entity.home.HomeEntity;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

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

    public void getHomeList(final Context context, final String userId) {

        if (page == -1) {
            List<HomeTaskBean> beanList = new ArrayList<>();
            List<HistoryTaskBean> yesterdayBeanList = new Select().from(HistoryTaskBean.class).orderBy("updateTime DESC").execute();
            if (yesterdayBeanList != null && yesterdayBeanList.size() > 0) {
                beanList.addAll(yesterdayBeanList);
                mHomeView.showHomeList(beanList);
            }
        }

        if (!NetworkUtil.hasNetwork(context)) {
            mHomeView.showError(context.getString(R.string.error_no_network));
            return;
        }
        if (!isLastPage) {
            setObservable(mHomeAPI.getHomeList(userId, page + 1));
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
        new Delete().from(HistoryTaskBean.class).execute();

        List<HomeTaskBean> beanList = new ArrayList<>();

        for (HomeEntity.RetValuesEntity.ContentEntity contentEntity : content) {

            HistoryTaskBean historyTaskBean = new HistoryTaskBean();
            historyTaskBean.setContent(contentEntity.getContent());
            historyTaskBean.setTitle(contentEntity.getTitle());
            historyTaskBean.setUpdateTime(contentEntity.getRecordTime());
            historyTaskBean.setName(contentEntity.getName());
            historyTaskBean.setPortrait(contentEntity.getPortrait());
            beanList.add(historyTaskBean);

            historyTaskBean.save();

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
