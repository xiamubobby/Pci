package com.wonders.xlab.pci.module.home;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.activeandroid.query.Select;
import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.recyclerview.LoadMoreRecyclerView;
import com.wonders.xlab.common.recyclerview.adapter.BaseBean;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.home.adapter.HomeRVAdapter;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.mvn.model.HomeModel;
import com.wonders.xlab.pci.module.home.mvn.view.HomeView;
import com.wonders.xlab.pci.module.home.rxbus.BPClickBus;
import com.wonders.xlab.pci.module.home.rxbus.BSClickBus;
import com.wonders.xlab.pci.module.home.rxbus.MedicineClickBus;
import com.wonders.xlab.pci.module.home.rxbus.SymptomClickBus;
import com.wonders.xlab.pci.module.rxbus.ConnectStateBus;
import com.wonders.xlab.pci.module.task.AddBPActivity;
import com.wonders.xlab.pci.module.task.AddBSActivity;
import com.wonders.xlab.pci.module.task.AddSymptomActivity;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;
import com.wonders.xlab.pci.service.XEMChatService;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Bind(R.id.rv_home)
    LoadMoreRecyclerView mRvHome;
    @Bind(R.id.container_home)
    FrameLayout mContainerHome;

    private HomeRVAdapter mHomeRVAdapter;

    private HomeModel mHomeModel;

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
        OttoManager.register(this);

        AIManager.getInstance(getActivity()).saveHomeShowing(true);

        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        mRvHome.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreToBottom() {
                mHomeModel.getHomeList(getActivity(), AIManager.getInstance(getActivity()).getUserId());
            }

            @Override
            public void loadMoreToTop() {
            }
        });
        mHomeModel.getHomeList(getActivity(), AIManager.getInstance(getActivity()).getUserId());

        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().startService(new Intent(getActivity(), XEMChatService.class));
            }
        }).start();
    }

    @Subscribe
    public void showNewTodayTask(TodayTaskBean bean) {
        if (mHomeRVAdapter != null) {
            if (mHomeRVAdapter.getItemCount() > 0) {
                BaseBean itemData = mHomeRVAdapter.getItemData(0);
                if (itemData.getItemLayout() == HomeTaskBean.ITEM_TODAY) {
                    TodayTaskBean todayTaskBean = (TodayTaskBean) itemData;
                    todayTaskBean.setTitle(bean.getTitle());
                    todayTaskBean.setPortrait(bean.getPortrait());
                    todayTaskBean.setUpdateTime(bean.getUpdateTime());
                    mHomeRVAdapter.notifyDataSetChanged();
                } else {
                    mHomeRVAdapter.addToTop(bean);
                }
            } else {
                mHomeRVAdapter.addToTop(bean);
            }
        } else {
            mHomeRVAdapter = new HomeRVAdapter(new WeakReference<Context>(getActivity()));
            mRvHome.setAdapter(mHomeRVAdapter);
            mHomeRVAdapter.addToTop(bean);
        }
    }

    @Subscribe
    public void showNewHistoryTask(HistoryTaskBean bean) {
        if (mHomeRVAdapter != null) {
            if (mHomeRVAdapter.getItemCount() > 0) {
                BaseBean itemData = mHomeRVAdapter.getItemData(0);
                if (itemData.getItemLayout() == HomeTaskBean.ITEM_TODAY) {
                    mHomeRVAdapter.addToPosition(bean, 1);
                } else {
                    mHomeRVAdapter.addToTop(bean);
                }
            } else {
                mHomeRVAdapter.addToTop(bean);
            }
        } else {
            mHomeRVAdapter = new HomeRVAdapter(new WeakReference<Context>(getActivity()));
            mRvHome.setAdapter(mHomeRVAdapter);
            mHomeRVAdapter.addToTop(bean);
        }
    }

    @Subscribe
    public void onConnectionChanged(ConnectStateBus bean) {
        if (bean.isConnected()) {
            //断线重连
            if (mHomeRVAdapter != null) {
                return;
            }
            mHomeModel.getHomeList(getActivity(), AIManager.getInstance(getActivity()).getUserId());
        } else {
            showSnackbar(mContainerHome, getResources().getString(R.string.network_disconnected));
        }
    }

    /**
     * 服药
     *
     * @param bean
     */
    @Subscribe
    public void onMedicineClick(MedicineClickBus bean) {
        getActivity().startActivity(new Intent(getActivity(), DailyTaskActivity.class));
    }

    /**
     * 服药
     *
     * @param bean
     */
    @Subscribe
    public void onBPClick(BPClickBus bean) {
        getActivity().startActivity(new Intent(getActivity(), AddBPActivity.class));
    }

    /**
     * 服药
     *
     * @param bean
     */
    @Subscribe
    public void onBSClick(BSClickBus bean) {
        getActivity().startActivity(new Intent(getActivity(), AddBSActivity.class));
    }

    /**
     * 服药
     *
     * @param bean
     */
    @Subscribe
    public void onSymptomClick(SymptomClickBus bean) {
        getActivity().startActivity(new Intent(getActivity(), AddSymptomActivity.class));
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
        showSnackbar(mContainerHome, message);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
        AIManager.getInstance(getActivity()).saveHomeShowing(false);
    }

}
