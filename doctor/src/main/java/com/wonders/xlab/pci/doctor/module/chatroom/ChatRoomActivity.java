package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.BloodPressureActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.BloodSugarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.MedicalRecordActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.otto.ChatRoomRecordInsertOtto;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.SymptomActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.UserInfoActivity;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ChatRoomPresenter;
import com.wonders.xlab.pci.doctor.util.UnReadMessageUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;
import im.hua.utils.NotifyUtil;

public class ChatRoomActivity extends AppbarActivity implements ChatRoomPresenter.ChatRoomPresenterListener {
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";
    /**
     * 患者和医生所在聊天组的id
     */
    public final static String EXTRA_GROUP_ID = "GROUP_ID";
    public final static String EXTRA_IM_GROUP_ID = "IM_GROUP_ID";
    public final static String EXTRA_GROUP_NAME = "GROUP_NAME";

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
    CommonRecyclerView mRecyclerView;

    private ChatRoomRVAdapter mChatRoomRVAdapter;
    private boolean mIsPaused;

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
        OttoManager.register(this);
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

        setToolbarTitle(patientName);

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
    protected void onResume() {
        super.onResume();
        mIsPaused = false;
        if (TextUtils.isDigitsOnly(imGroupId)) {
            new NotifyUtil().cancel(this, (int) Long.parseLong(imGroupId));
        }
        UnReadMessageUtil.readMessage(imGroupId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPaused = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
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

            mChatRoomPresenter.sendMessage(message, AIManager.getInstance().getDoctorTel(), groupId, groupName, imGroupId, patientId, patientName, patientPhoneNumber, sendTime, AIManager.getInstance().getDoctorPortraitUrl(),AIManager.getInstance().getDoctorName());
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
        mRecyclerView.hideRefreshOrLoadMore(true,true);
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
                    new NotifyUtil().cancel(this, (int) Long.parseLong(otto.getImGroupId()));
                }
                UnReadMessageUtil.readMessage(imGroupId);
            }

            OthersChatRoomBean bean = new OthersChatRoomBean();
            bean.name.set(otto.getFromWhoName());
            bean.text.set(otto.getTxtContent());
            bean.recordTime.set(DateUtil.format(otto.getMessageTime(),"HH:mm"));
            bean.portraitUrl.set(otto.getFromWhoAvatarUrl());

            initChatRoomAdapter();
            mChatRoomRVAdapter.insertToTop(bean);
            mRecyclerView.getRecyclerView().scrollToPosition(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_room,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat_room_phone:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + patientPhoneNumber));
                startActivity(dialIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
