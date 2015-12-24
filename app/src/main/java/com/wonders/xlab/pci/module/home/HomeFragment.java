package com.wonders.xlab.pci.module.home;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.wonders.xlab.common.recyclerview.LoadMoreRecyclerView;
import com.wonders.xlab.common.recyclerview.adapter.BaseBean;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.home.adapter.HomeRVAdapter;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.mvn.model.HomeModel;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Bind(R.id.rv_home)
    LoadMoreRecyclerView mRvHome;

    private HomeRVAdapter mHomeRVAdapter;

    private HomeModel mHomeModel;
    private CompositeSubscription mSubscription;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeModel = new HomeModel(this);
        addModel(mHomeModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        mRvHome.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreToBottom() {

            }

            @Override
            public void loadMoreToTop() {
                mHomeModel.getHomeList(AIManager.getInstance(getActivity()).getUserId());
            }
        });
        mHomeModel.getHomeList(AIManager.getInstance(getActivity()).getUserId());

        initRxBusListener();
    }

    private void initRxBusListener() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof TodayTaskBean) {
                    TodayTaskBean bus = (TodayTaskBean) o;

                    if (mHomeRVAdapter != null) {
                        if (mHomeRVAdapter.getItemCount() > 0) {
                            BaseBean itemData = mHomeRVAdapter.getItemData(0);
                            if (itemData.getItemLayout() == HomeTaskBean.ITEM_TODAY) {
                                TodayTaskBean todayTaskBean = (TodayTaskBean) itemData;
                                todayTaskBean.setTitle(bus.getTitle());
                                todayTaskBean.setUpdateTime(bus.getUpdateTime());
                                mHomeRVAdapter.notifyDataSetChanged();
                            }
                        } else {
                            mHomeRVAdapter.addToTop(bus);
                        }
                    } else {
                        mHomeRVAdapter = new HomeRVAdapter(new WeakReference<Context>(getActivity()));
                        mRvHome.setAdapter(mHomeRVAdapter);
                        mHomeRVAdapter.addToTop(bus);
                    }
                }
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void showHomeList(List<HomeTaskBean> beanList) {
        if (mHomeRVAdapter == null) {
            mHomeRVAdapter = new HomeRVAdapter(new WeakReference<Context>(getActivity()));
            mRvHome.setAdapter(mHomeRVAdapter);
        } else {
            mHomeRVAdapter.clear();
        }

        TodayTaskBean cache = new Select().from(TodayTaskBean.class).executeSingle();

        if (cache != null) {
            TodayTaskBean notice = new TodayTaskBean();
            notice.setUpdateTime(cache.getUpdateTime());
            notice.setName(cache.getName());
            notice.setTitle(cache.getTitle());
            notice.setPortrait(cache.getPortrait());

            beanList.add(0, notice);
        }

        mHomeRVAdapter.setDatas(beanList);

        new Delete().from(TodayTaskBean.class).execute();
    }

    @Override
    public void appendHomeList(List<HomeTaskBean> beanList) {
        if (mHomeRVAdapter == null) {
            mHomeRVAdapter = new HomeRVAdapter(new WeakReference<Context>(getActivity()));
            mRvHome.setAdapter(mHomeRVAdapter);
        }
        mHomeRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mRvHome, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }
}
