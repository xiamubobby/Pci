package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

public class ChatActivity extends AppbarActivity {
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";

    private String patientId;
    private String patientName;
    private String patientPhoneNumber;

    @Override
    public int getContentLayout() {
        return R.layout.chat_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "聊天";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                        Toast.makeText(ChatActivity.this, "即将开放，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

}
