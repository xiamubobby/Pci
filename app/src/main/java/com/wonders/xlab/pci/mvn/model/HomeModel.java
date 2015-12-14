package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.HomeAPI;
import com.wonders.xlab.pci.mvn.entity.home.HomeEntity;
import com.wonders.xlab.pci.mvn.view.HomeView;

import java.util.ArrayList;
import java.util.Calendar;
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
//        setObservable(mHomeAPI.getHomeList());
        onSuccess(null);
    }

    @Override
    protected void onSuccess(@NonNull HomeEntity response) {

        List<HomeTaskBean> beanList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                TodayTaskBean todayTaskBean = new TodayTaskBean();
                todayTaskBean.setTitle("今日任务(早晨)");
                todayTaskBean.setUpdateTime(Calendar.getInstance().getTimeInMillis());
                todayTaskBean.setName("刘医生");

                beanList.add(todayTaskBean);
            } else {
                YesterdayTaskBean yesterdayTaskBean = new YesterdayTaskBean();
                yesterdayTaskBean.setContent("jlsajdlfkjasldkfjlaksjdlkfjalskjdlfjslakdjf\nalksjdflka");
                yesterdayTaskBean.setTitle("昨日健康任务小结");
                yesterdayTaskBean.setUpdateTime(Calendar.getInstance().getTimeInMillis());
                yesterdayTaskBean.setName("刘医生");
                beanList.add(yesterdayTaskBean);
            }

        }
        mHomeView.showHomeList(beanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
