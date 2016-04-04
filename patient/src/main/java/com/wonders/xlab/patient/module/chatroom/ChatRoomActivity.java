package com.wonders.xlab.patient.module.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.module.main.doctors.detail.DoctorDetailActivity;
import com.wonders.xlab.patient.module.main.doctors.otto.DoctorNotifyCountOtto;
import com.wonders.xlab.patient.mvp.presenter.IChatRoomPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.ChatRoomPresenter;
import com.wonders.xlab.patient.receiver.otto.EMChatMessageOtto;
import com.wonders.xlab.patient.util.UnreadMessageUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import im.hua.utils.NotifyUtil;

/**
 * 聊天界面
 */
public class ChatRoomActivity extends AppbarActivity implements ChatRoomPresenter.ChatRoomPresenterListener {
    public final static String EXTRA_GROUP_NAME = "groupName";
    /**
     * 患者和医生所在聊天组的id(不是环信id)
     */
    public final static String EXTRA_GROUP_ID = "groupId";
    public final static String EXTRA_IM_GROUP_ID = "imGroupId";

    /**
     * 该小组提供的服务是否已过期
     */
    public final static String EXTRA_CAN_CHAT = "canChat";

    private String groupId;
    private String imGroupId;
    private String groupName;
    private boolean canChat;

    @Bind(R.id.ll_chat_room_loading)
    LinearLayout mLoadingView;

    @Bind(R.id.rl_chat_room_input_area)
    RelativeLayout mRlChatRoomInputArea;

    @Bind(R.id.btn_chat_room_buy_again)
    Button mBtnChatRoomBuyAgain;

    @Bind(R.id.et_chat_room_input)
    EditText mEtChatRoomInput;

    private IChatRoomPresenter mChatRoomPresenter;

    @Bind(R.id.recycler_view_chat_room)
    PullLoadMoreRecyclerView mRecyclerView;

    private ChatRoomRVAdapter mChatRoomRVAdapter;

    private boolean mIsPaused = false;

    @Override
    public int getContentLayout() {
        return R.layout.chat_room_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "聊天";
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OttoManager.register(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            showShortToast(getResources().getString(R.string.query_patient_info_failed));
            finish();
            return;
        }
        groupId = intent.getStringExtra(EXTRA_GROUP_ID);
        imGroupId = intent.getStringExtra(EXTRA_IM_GROUP_ID);
        if (TextUtils.isEmpty(groupId) || TextUtils.isEmpty(imGroupId)) {
            showShortToast(getResources().getString(R.string.query_patient_info_failed));
            finish();
            return;
        }
        groupName = intent.getStringExtra(EXTRA_GROUP_NAME);
        canChat = intent.getBooleanExtra(EXTRA_CAN_CHAT, false);

        //如果有的话，移除通知栏对应的通知，以groupId为通知id
        new NotifyUtil().cancel(this, Integer.parseInt(groupId));
        UnreadMessageUtil.readMessage(imGroupId);
        //通知主界面更新未读通知数显示
        OttoManager.post(new DoctorNotifyCountOtto());

        initServiceStatus(canChat);

        setToolbarTitle(groupName);

        mLoadingView.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setLinearLayout(true);
        mRecyclerView.setPullRefreshEnable(false);
        mRecyclerView.setPushRefreshEnable(true);
        mRecyclerView.showHeaderOrFooterView(false);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mLoadingView.setVisibility(View.VISIBLE);
                mChatRoomPresenter.getChatList(imGroupId, false);
            }
        });

        mChatRoomPresenter = new ChatRoomPresenter(this, AIManager.getInstance().getPatientId());
        addPresenter(mChatRoomPresenter);

        mChatRoomPresenter.getChatList(imGroupId, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsPaused = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat_room_detail:
                goToDoctorDetailActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToDoctorDetailActivity() {
        if (!TextUtils.isEmpty(groupName) && !TextUtils.isEmpty(groupId)) {
            Intent intent = new Intent(ChatRoomActivity.this, DoctorDetailActivity.class);
            intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, groupName);
            intent.putExtra(DoctorDetailActivity.EXTRA_ID, groupId);
            startActivity(intent);
        } else {
            showShortToast("请重新进入聊天");
        }
    }

    /**
     * 初始化底部为聊天或者提示为重新购买
     *
     * @param canChat 是否可以继续聊天
     */
    private void initServiceStatus(boolean canChat) {
        if (canChat) {
            mRlChatRoomInputArea.setVisibility(View.VISIBLE);
            mBtnChatRoomBuyAgain.setVisibility(View.GONE);
        } else {
            mRlChatRoomInputArea.setVisibility(View.GONE);
            mBtnChatRoomBuyAgain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_chat_room_buy_again)
    public void buyAgain() {
        goToDoctorDetailActivity();
    }

    @OnClick(R.id.btn_chat_room_send)
    public void sendMessage() {
        String message = mEtChatRoomInput.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            long sendTime = Calendar.getInstance().getTimeInMillis();

            MeChatRoomBean bean = new MeChatRoomBean();
            bean.text.set(message);
            bean.portraitUrl.set(AIManager.getInstance().getPatientPortraitUrl());
            bean.recordTimeInStr.set(DateUtil.format(sendTime, "HH:mm"));
            bean.recordTimeInMill.set(sendTime);
            bean.isSending.set(true);

            initChatRoomAdapter();
            mChatRoomRVAdapter.insertToTop(bean);

            mRecyclerView.getRecyclerView().smoothScrollToPosition(0);

            mEtChatRoomInput.setText("");

            mChatRoomPresenter.sendMessage(message, AIManager.getInstance().getPatientTel(), imGroupId, groupId, groupName, sendTime);
        }
    }

    private void initChatRoomAdapter() {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mChatRoomRVAdapter == null) {
            mChatRoomRVAdapter = new ChatRoomRVAdapter();
        }
        mRecyclerView.setAdapter(mChatRoomRVAdapter);
    }

    /**
     * 接收其他人发给我的通知，加入显示列表，并且移除通知栏的该条通知
     * @param otto
     */
    @Subscribe
    public void receiveNotifyForUpdate(EMChatMessageOtto otto) {
        if (groupId.equals(otto.getGroupId())) {
            /**
             * remove the notify with groupid as it's notify id
             */
            if (!mIsPaused) {
                new NotifyUtil().cancel(this, Integer.parseInt(otto.getGroupId()));
            }

            OthersChatRoomBean bean = new OthersChatRoomBean();
            bean.isSending.set(false);
            bean.name.set(otto.getFromWhoName());
            bean.text.set(otto.getTxtContent());
            bean.recordTimeInMill.set(otto.getMessageTime());
            bean.portraitUrl.set(otto.getFromWhoAvatarUrl());

            mChatRoomRVAdapter.insertToTop(bean);
            mRecyclerView.scrollToTop();
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
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
        mRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPaused = true;
    }
}
