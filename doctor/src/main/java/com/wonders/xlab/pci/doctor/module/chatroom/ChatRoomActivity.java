package com.wonders.xlab.pci.doctor.module.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.BloodPressureActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.BloodSugarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.chat.ChatFragment;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.MedicalRecordActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.SymptomActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.UserInfoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatRoomActivity extends AppbarActivity {
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

    @Bind(R.id.view_pager_chat_room)
    ViewPager mViewPager;
    @Bind(R.id.stl_chat_room)
    SlidingTabLayout mStlChatRoomTop;

    private FragmentVPAdapter mVPAdapter;

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
            showShortToast("获取患者信息失败，请重试！");
            finish();
            return;
        }
        patientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        groupId = intent.getStringExtra(EXTRA_GROUP_ID);
        imGroupId = intent.getStringExtra(EXTRA_IM_GROUP_ID);
        groupName = intent.getStringExtra(EXTRA_GROUP_NAME);
        if (TextUtils.isEmpty(patientId) || TextUtils.isEmpty(groupName) || TextUtils.isEmpty(imGroupId)) {
            showShortToast("获取患者信息失败，请重试！");
            finish();
            return;
        }
        patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);
        patientPhoneNumber = intent.getStringExtra(EXTRA_PATIENT_PHONE_NUMBER);

        setToolbarTitle(patientName);

        mVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mVPAdapter.addFragment(ChatFragment.newInstance(patientId, patientName, patientPhoneNumber, groupId, imGroupId, groupName), "聊天");
        mViewPager.setAdapter(mVPAdapter);

        mStlChatRoomTop.setViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    public void onBPClick() {
        Intent intent = new Intent(this, BloodPressureActivity.class);
        goToActivity(intent, BloodPressureActivity.EXTRA_PATIENT_ID);
    }

    private void goToActivity(Intent intent, String extraPatientId) {
        intent.putExtra(extraPatientId, patientId);
        startActivity(intent);
    }

    public void onBSClick() {
        Intent intent = new Intent(this, BloodSugarActivity.class);
        goToActivity(intent, SymptomActivity.EXTRA_PATIENT_ID);
    }

    public void onUserInfoClick() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        goToActivity(intent, UserInfoActivity.EXTRA_PATIENT_ID);
    }

    public void onSymptomClick() {
        Intent intent = new Intent(this, SymptomActivity.class);
        goToActivity(intent, SymptomActivity.EXTRA_PATIENT_ID);
    }

    public void onMedicalRecordClick() {
        Intent intent = new Intent(this, MedicalRecordActivity.class);
        goToActivity(intent, MedicalRecordActivity.EXTRA_PATIENT_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
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
