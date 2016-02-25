package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.bp.BloodPressureActivity;
import com.wonders.xlab.pci.doctor.module.bs.BloodSugarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.MedicalRecordActivity;
import com.wonders.xlab.pci.doctor.module.symptom.SymptomActivity;
import com.wonders.xlab.pci.doctor.module.userinfo.UserInfoActivity;
import com.wonders.xlab.pci.doctor.mvp.presenter.ChatRoomPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IChatRoomPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.BadgeView;

public class ChatRoomActivity extends AppbarActivity implements IChatRoomPresenter {
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";
    public final static String EXTRA_PATIENT_GROUP_ID = "GROUP_ID";

    @Bind(R.id.iv_chat_room_record)
    ImageView mIvChatRoomRecord;

    private ChatRoomPresenter mChatRoomPresenter;

    @Bind(R.id.recycler_view_chat_room)
    RecyclerView mRecyclerView;

    private ChatRoomRVAdapter mChatRoomRVAdapter;

    private String patientId;
    private String groupId;
    private String patientName;
    private String patientPhoneNumber;

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
            throw new NullPointerException("getIntent() is null");
        }
        patientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        groupId = intent.getStringExtra(EXTRA_PATIENT_GROUP_ID);
        if (TextUtils.isEmpty(patientId)) {
            throw new NullPointerException("EXTRA_PATIENT_ID is null");
        }
        patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);
        patientPhoneNumber = intent.getStringExtra(EXTRA_PATIENT_PHONE_NUMBER);

        setupToolbar();

        BadgeView badgeView = new BadgeView(this, mIvChatRoomRecord);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setText("2");
        badgeView.show();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));

        mChatRoomPresenter = new ChatRoomPresenter(this);
        addPresenter(mChatRoomPresenter);

        mChatRoomPresenter.getChatList(groupId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_chat_room_send)
    public void sendMessage() {
        ChatRoomBean itemData = mChatRoomRVAdapter.getItemData(0);
        if (itemData instanceof MeChatRoomBean) {
            MeChatRoomBean bean = (MeChatRoomBean) itemData;
            Toast.makeText(this, "bean.text:" + bean.text.get(), Toast.LENGTH_SHORT).show();
        } else {
            OthersChatRoomBean bean = (OthersChatRoomBean) itemData;
            bean.name.set("修改了");
        }
    }

    @OnClick(R.id.iv_chat_room_bp)
    public void onBPClick() {
        startActivity(new Intent(this, BloodPressureActivity.class));
    }

    @OnClick(R.id.iv_chat_room_bs)
    public void onBSClick() {
        startActivity(new Intent(this, BloodSugarActivity.class));
    }

    @OnClick(R.id.iv_chat_room_user_info)
    public void onUserInfoClick() {
        startActivity(new Intent(this, UserInfoActivity.class));
    }

    @OnClick(R.id.iv_chat_room_symptom)
    public void onSymptomClick() {
        startActivity(new Intent(this, SymptomActivity.class));
    }

    @OnClick(R.id.iv_chat_room_record)
    public void onMedicalRecordClick() {
        startActivity(new Intent(this, MedicalRecordActivity.class));
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
        }
        mRecyclerView.setAdapter(mChatRoomRVAdapter);
    }

    @Override
    public void showChatMessageList(List<ChatRoomBean> chatRoomBeanList) {
        initChatRoomAdapter();
        mChatRoomRVAdapter.setDatas(chatRoomBeanList);
    }

    @Override
    public void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList) {
        initChatRoomAdapter();
        mChatRoomRVAdapter.insertDatasToTop(chatRoomBeanList);
    }

    @Override
    public void showError(String message) {

    }
}
