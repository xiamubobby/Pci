package com.wonders.xlab.pci.doctor.module.chatroom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ChatRoomPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.otto.ChatRoomRecordInsertOtto;
import com.wonders.xlab.pci.doctor.util.RealmUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;
import im.hua.utils.NotifyUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ChatRoomPresenter.ChatRoomPresenterListener {
    public final static String ARG_PATIENT_ID = "PATIENT_ID";
    public final static String ARG_PATIENT_NAME = "PATIENT_NAME";
    public final static String ARG_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";
    /**
     * 患者和医生所在聊天组的id
     */
    public final static String ARG_OWNER_ID = "ownerId";
    public final static String ARG_IM_GROUP_ID = "imGroupId";
    public final static String ARG_GROUP_NAME = "groupName";

    private String patientId;
    private String imGroupId;
    private String ownerId;
    private String groupName;
    private String patientName;
    private String patientTel;

    @Bind(R.id.recycler_view_chat_room)
    CommonRecyclerView mRecyclerView;
    @Bind(R.id.et_chat_room_input)
    EditText mEtChatRoomInput;

    private ChatRoomRVAdapter mChatRoomRVAdapter;

    private ChatRoomPresenter mChatRoomPresenter;

    private boolean mIsPaused;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String patientId, String patientName, String patientTel, String ownerId, String imGroupId, String groupName) {
        Bundle data = new Bundle();
        data.putString(ARG_OWNER_ID, ownerId);
        data.putString(ARG_GROUP_NAME, groupName);
        data.putString(ARG_IM_GROUP_ID, imGroupId);
        data.putString(ARG_PATIENT_ID, patientId);
        data.putString(ARG_PATIENT_NAME, patientName);
        data.putString(ARG_PATIENT_PHONE_NUMBER, patientTel);

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(data);
        return chatFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        ownerId = data.getString(ARG_OWNER_ID);
        groupName = data.getString(ARG_GROUP_NAME);
        imGroupId = data.getString(ARG_IM_GROUP_ID);
        patientId = data.getString(ARG_PATIENT_ID);
        patientName = data.getString(ARG_PATIENT_NAME);
        patientTel = data.getString(ARG_PATIENT_PHONE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        //cancel notification
        NotifyUtil.cancel(getActivity(), (int) Long.parseLong(imGroupId));

        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mChatRoomPresenter.getChatList(imGroupId);
            }
        });

        mChatRoomPresenter = new ChatRoomPresenter(this, AIManager.getInstance().getDoctorId());
        addPresenter(mChatRoomPresenter);

        mChatRoomPresenter.getChatList(imGroupId);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPaused = false;
        if (TextUtils.isDigitsOnly(imGroupId)) {
            NotifyUtil.cancel(getActivity(), (int) Long.parseLong(imGroupId));
        }
        RealmUtil.readMessage(imGroupId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPaused = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        mChatRoomRVAdapter = null;
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_chat_room_send)
    public void sendMessage() {
        String message = mEtChatRoomInput.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            long sendTime = Calendar.getInstance().getTimeInMillis();

            MeChatRoomBean bean = new MeChatRoomBean();
            bean.text.set(message);
            bean.portraitUrl.set(AIManager.getInstance().getDoctorPortraitUrl());
            bean.recordTime.set(DateUtil.format(sendTime, "HH:mm"));
            bean.recordTimeInMill.set(sendTime);
            bean.isSending.set(true);
            mChatRoomRVAdapter.insertToTop(bean);

            mRecyclerView.getRecyclerView().smoothScrollToPosition(0);

            mEtChatRoomInput.setText("");

            mChatRoomPresenter.sendMessage(message, AIManager.getInstance().getDoctorTel(), ownerId, groupName, imGroupId, patientId, patientName, patientTel, sendTime, AIManager.getInstance().getDoctorPortraitUrl(), AIManager.getInstance().getDoctorName());
        }
    }

    private void initChatRoomAdapter() {
        if (mChatRoomRVAdapter == null) {
            mChatRoomRVAdapter = new ChatRoomRVAdapter();
            mRecyclerView.setAdapter(mChatRoomRVAdapter);
        }
    }

    @Override
    public void showChatMessageList(List<ChatRoomBean> chatRoomBeanList) {
        initChatRoomAdapter();
        mChatRoomRVAdapter.setDatas(chatRoomBeanList);
    }

    @Override
    public void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList) {
        initChatRoomAdapter();
        mChatRoomRVAdapter.appendDatas(chatRoomBeanList);
        mRecyclerView.getRecyclerView().smoothScrollToPosition(mChatRoomRVAdapter.getItemCount() - chatRoomBeanList.size());
    }

    @Override
    public void sendMessageSuccess(long time) {
        for (int i = 0; i < mChatRoomRVAdapter.getItemCount(); i++) {
            ChatRoomBean bean = mChatRoomRVAdapter.getItemData(i);
            switch (mChatRoomRVAdapter.getItemViewType(i)) {
                case ChatRoomBean.ITEM_LAYOUT_ME:
                    MeChatRoomBean meChatRoomBean = (MeChatRoomBean) bean;
                    if (meChatRoomBean.isSending.get() && null != meChatRoomBean.recordTimeInMill && meChatRoomBean.recordTimeInMill.get() == time) {
                        meChatRoomBean.isSending.set(false);
                        return;
                    }
                    break;
            }
        }
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {

            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {

            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {

            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    /**
     * 接收其他人发给我的通知，加入显示列表，并且移除通知栏的该条通知
     *
     * @param otto
     */
    @Subscribe
    public void receiveNotifyForUpdate(ChatRoomRecordInsertOtto otto) {
        if (imGroupId.equals(otto.getImGroupId())) {
            /**
             * remove the notify with groupid as it's notify id
             */
            if (!mIsPaused) {
                if (TextUtils.isDigitsOnly(otto.getImGroupId())) {
                    NotifyUtil.cancel(getActivity(), (int) Long.parseLong(otto.getImGroupId()));
                }
                RealmUtil.readMessage(imGroupId);
            }

            OthersChatRoomBean bean = new OthersChatRoomBean();
            bean.name.set(otto.getFromWhoName());
            bean.text.set(otto.getTxtContent());
            bean.recordTime.set(DateUtil.format(otto.getMessageTime(), "HH:mm"));
            bean.portraitUrl.set(otto.getFromWhoAvatarUrl());

            initChatRoomAdapter();
            mChatRoomRVAdapter.insertToTop(bean);
            mRecyclerView.getRecyclerView().scrollToPosition(0);
        }
    }
}
