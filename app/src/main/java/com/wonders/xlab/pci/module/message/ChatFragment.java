package com.wonders.xlab.pci.module.message;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.recyclerview.LoadMoreRecyclerView;
import com.wonders.xlab.common.recyclerview.adapter.BaseBean;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.SPManager;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.message.adapter.ChatRVAdapter;
import com.wonders.xlab.pci.module.message.bean.ChatBean;
import com.wonders.xlab.pci.module.message.bean.MessageBean;
import com.wonders.xlab.pci.module.message.bean.NoticeBean;
import com.wonders.xlab.pci.module.message.mvn.model.ChatModel;
import com.wonders.xlab.pci.module.message.mvn.view.ChatView;
import com.wonders.xlab.pci.module.message.otto.BPClickBus;
import com.wonders.xlab.pci.module.message.otto.BSClickBus;
import com.wonders.xlab.pci.module.message.otto.MedicineClickBus;
import com.wonders.xlab.pci.module.message.otto.SymptomClickBus;
import com.wonders.xlab.pci.module.otto.ConnectStateBus;
import com.wonders.xlab.pci.module.task.AddSymptomActivity;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;
import com.wonders.xlab.pci.module.task.bp.AddBPActivity;
import com.wonders.xlab.pci.module.task.bp.MeasureBPGuideActivity;
import com.wonders.xlab.pci.module.task.bs.AddBSActivity;
import com.wonders.xlab.pci.module.task.bs.MeasureBSGuideActivity;
import com.wonders.xlab.pci.service.XEMChatService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ChatView {

    @Bind(R.id.rv_home)
    LoadMoreRecyclerView mRvHome;
    @Bind(R.id.container_home)
    FrameLayout mContainerHome;

    private ChatRVAdapter mChatRVAdapter;

    private ChatModel mChatModel;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatModel = new ChatModel(this);
        addModel(mChatModel);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
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
                mChatModel.getChatList(getActivity(), AIManager.getInstance(getActivity()).getUserId());
            }

            @Override
            public void loadMoreToTop() {
            }
        });
        mChatModel.getChatList(getActivity(), AIManager.getInstance(getActivity()).getUserId());

        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().startService(new Intent(getActivity(), XEMChatService.class));
            }
        }).start();
    }

    @Subscribe
    public void showNewTodayTask(NoticeBean bean) {
        if (mChatRVAdapter != null) {
            if (mChatRVAdapter.getItemCount() > 0) {
                BaseBean itemData = mChatRVAdapter.getItemData(0);
                if (itemData.getItemLayout() == ChatBean.ITEM_TODAY) {
                    NoticeBean noticeBean = (NoticeBean) itemData;
                    noticeBean.setTitle(bean.getTitle());
                    noticeBean.setPortrait(bean.getPortrait());
                    noticeBean.setUpdateTime(bean.getUpdateTime());
                    mChatRVAdapter.notifyDataSetChanged();
                } else {
                    mChatRVAdapter.addToTop(bean);
                }
            } else {
                mChatRVAdapter.addToTop(bean);
            }
        } else {
            mChatRVAdapter = new ChatRVAdapter();
            mRvHome.setAdapter(mChatRVAdapter);
            mChatRVAdapter.addToTop(bean);
        }
    }

    @Subscribe
    public void showNewHistoryTask(MessageBean bean) {
        if (mChatRVAdapter != null) {
            if (mChatRVAdapter.getItemCount() > 0) {
                BaseBean itemData = mChatRVAdapter.getItemData(0);
                if (itemData.getItemLayout() == ChatBean.ITEM_TODAY) {
                    mChatRVAdapter.addToPosition(bean, 1);
                } else {
                    mChatRVAdapter.addToTop(bean);
                }
            } else {
                mChatRVAdapter.addToTop(bean);
            }
        } else {
            mChatRVAdapter = new ChatRVAdapter();
            mRvHome.setAdapter(mChatRVAdapter);
            mChatRVAdapter.addToTop(bean);
        }
    }

    @Subscribe
    public void onConnectionChanged(ConnectStateBus bean) {
        if (bean.isConnected()) {
            //断线重连
            if (mChatRVAdapter != null) {
                return;
            }
            mChatModel.getChatList(getActivity(), AIManager.getInstance(getActivity()).getUserId());
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
        boolean useEquipment = SPManager.get(getActivity()).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            getActivity().startActivity(new Intent(getActivity(), MeasureBPGuideActivity.class));
        } else {
            getActivity().startActivity(new Intent(getActivity(), AddBPActivity.class));
        }

    }

    /**
     * 服药
     *
     * @param bean
     */
    @Subscribe
    public void onBSClick(BSClickBus bean) {
        boolean useEquipment = SPManager.get(getActivity()).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            getActivity().startActivity(new Intent(getActivity(), MeasureBSGuideActivity.class));
        } else {
            getActivity().startActivity(new Intent(getActivity(), AddBSActivity.class));
        }
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
    public void showHomeList(List<ChatBean> beanList) {
        if (mChatRVAdapter == null) {
            mChatRVAdapter = new ChatRVAdapter();
        } else {
            mChatRVAdapter.clear();
        }
        mRvHome.setAdapter(mChatRVAdapter);

        NoticeBean cache = null;

        if (cache != null) {
            NoticeBean notice = new NoticeBean();
            notice.setUpdateTime(cache.getUpdateTime());
            notice.setName(cache.getName());
            notice.setTitle(cache.getTitle());
            notice.setPortrait(cache.getPortrait());

            beanList.add(0, notice);
        }
        mChatRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendHomeList(List<ChatBean> beanList) {
        if (mChatRVAdapter == null) {
            mChatRVAdapter = new ChatRVAdapter();
        }
        mRvHome.setAdapter(mChatRVAdapter);
        mChatRVAdapter.appendDatas(beanList);
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

        //重置分页状态
        mChatModel.cancel();
    }

}