package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.ChatRoomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.model.ChatRoomModel;
import com.wonders.xlab.pci.doctor.module.chatroom.view.ChatRoomView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatRoomActivity extends AppbarActivity implements ChatRoomView {
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";

    private ChatRoomModel mChatRoomModel;

    @Bind(R.id.recycler_view_chat_room)
    RecyclerView mRecyclerViewChatRoom;

    private ChatRoomRVAdapter mChatRoomRVAdapter;

    private String patientId;
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
        if (TextUtils.isEmpty(patientId)) {
            throw new NullPointerException("EXTRA_PATIENT_ID is null");
        }
        patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);
        patientPhoneNumber = intent.getStringExtra(EXTRA_PATIENT_PHONE_NUMBER);

        setupToolbar();

        mRecyclerViewChatRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        mChatRoomModel = new ChatRoomModel(this);
        addModel(mChatRoomModel);
        mChatRoomModel.getChatList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
        mRecyclerViewChatRoom.setAdapter(mChatRoomRVAdapter);
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }
}
