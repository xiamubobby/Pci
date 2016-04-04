package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.BloodPressureActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.BloodSugarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.MedicalRecordActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.SymptomActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.UserInfoActivity;
import com.wonders.xlab.pci.doctor.mvp.presenter.ChatRoomPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.listener.ChatRoomPresenterListener;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.BadgeView;
import im.hua.utils.DateUtil;
import im.hua.utils.NotifyUtil;

public class ChatRoomActivity extends AppbarActivity implements ChatRoomPresenterListener {
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";
    /**
     * 患者和医生所在聊天组的id
     */
    public final static String EXTRA_GROUP_ID = "GROUP_ID";
    public final static String EXTRA_IM_GROUP_ID = "IM_GROUP_ID";
    public final static String EXTRA_GROUP_NAME = "GROUP_NAME";

    @Bind(R.id.ll_chat_room_loading)
    LinearLayout mLoadingView;

    private String patientId;
    private String imGroupId;
    private String groupId;
    private String groupName;
    private String patientName;
    private String patientPhoneNumber;

    @Bind(R.id.iv_chat_room_record)
    ImageView mIvChatRoomRecord;
    @Bind(R.id.et_chat_room_input)
    EditText mEtChatRoomInput;

    private ChatRoomPresenter mChatRoomPresenter;

    @Bind(R.id.recycler_view_chat_room)
    PullLoadMoreRecyclerView mRecyclerView;

    private ChatRoomRVAdapter mChatRoomRVAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.chat_room_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "聊天";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
//            throw new NullPointerException("getIntent() is null");
            Toast.makeText(this, "获取患者信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        patientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        groupId = intent.getStringExtra(EXTRA_GROUP_ID);
        imGroupId = intent.getStringExtra(EXTRA_IM_GROUP_ID);
        groupName = intent.getStringExtra(EXTRA_GROUP_NAME);
        if (TextUtils.isEmpty(patientId) || TextUtils.isEmpty(groupName) || TextUtils.isEmpty(imGroupId)) {
            Toast.makeText(this, "获取患者信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);
        patientPhoneNumber = intent.getStringExtra(EXTRA_PATIENT_PHONE_NUMBER);

        //cancel notification
        new NotifyUtil().cancel(this, Integer.parseInt(groupId));

        setupToolbar();

        BadgeView badgeView = new BadgeView(this, mIvChatRoomRecord);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setText("2");
        badgeView.show();

        mLoadingView.setVisibility(View.GONE);

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
                mChatRoomPresenter.getChatList(imGroupId);
            }
        });

        mChatRoomPresenter = new ChatRoomPresenter(this, AIManager.getInstance(this).getUserId());
        addPresenter(mChatRoomPresenter);

        mChatRoomPresenter.getChatList(imGroupId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_chat_room_send)
    public void sendMessage() {
        String message = mEtChatRoomInput.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            long sendTime = Calendar.getInstance().getTimeInMillis();

            MeChatRoomBean bean = new MeChatRoomBean();
            bean.text.set(message);
            bean.portraitUrl.set(AIManager.getInstance(this).getAvatarUrl());
            bean.recordTime.set(DateUtil.format(sendTime, "yyyy-MM-dd HH:mm"));
            bean.recordTimeInMill.set(sendTime);
            bean.isSending.set(true);
            mChatRoomRVAdapter.insertToTop(bean);

            mRecyclerView.getRecyclerView().smoothScrollToPosition(0);

            mEtChatRoomInput.setText("");

            mChatRoomPresenter.sendMessage(message, AIManager.getInstance(this).getUserTel(), groupId, groupName, imGroupId, patientId, patientName, patientPhoneNumber, sendTime, AIManager.getInstance(this).getAvatarUrl(),AIManager.getInstance(this).getUserName());
        }
    }

    @OnClick(R.id.iv_chat_room_bp)
    public void onBPClick() {
        Intent intent = new Intent(this, BloodPressureActivity.class);
        goToActivity(intent, BloodPressureActivity.EXTRA_PATIENT_ID);
    }

    private void goToActivity(Intent intent, String extraPatientId) {
        intent.putExtra(extraPatientId, patientId);
        startActivity(intent);
    }

    @OnClick(R.id.iv_chat_room_bs)
    public void onBSClick() {
        Intent intent = new Intent(this, BloodSugarActivity.class);
        goToActivity(intent, SymptomActivity.EXTRA_PATIENT_ID);
    }

    @OnClick(R.id.iv_chat_room_user_info)
    public void onUserInfoClick() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        goToActivity(intent, UserInfoActivity.EXTRA_PATIENT_ID);
    }

    @OnClick(R.id.iv_chat_room_symptom)
    public void onSymptomClick() {
        Intent intent = new Intent(this, SymptomActivity.class);
        goToActivity(intent, SymptomActivity.EXTRA_PATIENT_ID);
    }

    @OnClick(R.id.iv_chat_room_record)
    public void onMedicalRecordClick() {
        Intent intent = new Intent(this, MedicalRecordActivity.class);
        goToActivity(intent, MedicalRecordActivity.EXTRA_PATIENT_ID);
    }

    private void setupToolbar() {
        setToolbarTitle(patientName);

        getToolbar().inflateMenu(R.menu.menu_chat_room);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_chat_room_phone:
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + patientPhoneNumber));
                        startActivity(dialIntent);

                        break;
                    case R.id.menu_chat_room_export:
                        Toast.makeText(ChatRoomActivity.this, "即将开放，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
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
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
        mRecyclerView.setPullLoadMoreCompleted();
    }
}
