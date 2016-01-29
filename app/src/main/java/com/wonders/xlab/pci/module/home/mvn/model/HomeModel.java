package com.wonders.xlab.pci.module.home.mvn.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.wonders.xlab.pci.module.base.mvn.entity.home.HomeEntity;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.mvn.api.HomeAPI;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
            Observable.just(HistoryTaskBean.class)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<Class<HistoryTaskBean>, List<HistoryTaskBean>>() {
                        @Override
                        public List<HistoryTaskBean> call(Class<HistoryTaskBean> historyTaskBeanClass) {
                            return new Select().from(historyTaskBeanClass).orderBy("updateTime DESC").execute();
                        }
                    })
                    .filter(new Func1<List<HistoryTaskBean>, Boolean>() {
                        @Override
                        public Boolean call(List<HistoryTaskBean> historyTaskBeen) {
                            return historyTaskBeen != null && historyTaskBeen.size() > 0;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<HistoryTaskBean>>() {
                        @Override
                        public void call(List<HistoryTaskBean> historyTaskBeen) {
                            List beanList = new ArrayList<>();
                            beanList.addAll(historyTaskBeen);
                            mHomeView.showHomeList(beanList);
                        }
                    });

        }

        /*if (!NetworkUtil.hasNetwork(context)) {
            mHomeView.showError(context.getString(R.string.error_no_network));
            return;
        }*/
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
