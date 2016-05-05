package com.wonders.xlab.pci.doctor.module.patientinfo;

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
import com.wonders.xlab.pci.doctor.module.chatroom.ChatFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.medicalrecords.MedicalRecordsFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodpressure.BPFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodsugar.BSFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.TestIndicatorFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.medicalpicture.MedicalRecordFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.prescription.PrescriptionFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.SurgicalHistoryFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.symptom.SymptomHRFragment;
import com.wonders.xlab.pci.doctor.module.patientinfo.basicinfo.UserInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.KeyboardUtil;

public class PatientInfoContainerActivity extends AppbarActivity {
    public final static int TAB_POSITION_SYMPTOM = 2;
    public final static int TAB_POSITION_MEDICAL = 3;
    public final static int TAB_POSITION_BP = 4;
    public final static int TAB_POSITION_BS = 5;

    public final static String EXTRA_TAB_POSITION = "tabPosition";
    public final static String EXTRA_PATIENT_ID = "PATIENT_ID";
    public final static String EXTRA_PATIENT_NAME = "PATIENT_NAME";
    public final static String EXTRA_PATIENT_PHONE_NUMBER = "PATIENT_NUMBER";
    /**
     * 患者和医生所在聊天组的id
     */
    public final static String EXTRA_OWNER_ID = "ownerId";
    public final static String EXTRA_IM_GROUP_ID = "IM_GROUP_ID";
    public final static String EXTRA_GROUP_NAME = "GROUP_NAME";

    private int tabPosition;
    private String patientId;
    private String imGroupId;
    private String ownerId;
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
        tabPosition = intent.getIntExtra(EXTRA_TAB_POSITION, 0);
        patientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        ownerId = intent.getStringExtra(EXTRA_OWNER_ID);
        imGroupId = intent.getStringExtra(EXTRA_IM_GROUP_ID);
        groupName = intent.getStringExtra(EXTRA_GROUP_NAME);
        if (TextUtils.isEmpty(patientId) || TextUtils.isEmpty(groupName) || TextUtils.isEmpty(imGroupId)) {
            showShortToast("获取患者信息失败，请重试！");
            finish();
            return;
        }
        patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);
        patientPhoneNumber = intent.getStringExtra(EXTRA_PATIENT_PHONE_NUMBER);

        setToolbarTitle("患者:" + patientName);

        mVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mVPAdapter.addFragment(ChatFragment.newInstance(patientId, patientName, patientPhoneNumber, ownerId, imGroupId, groupName), "聊天");
        mVPAdapter.addFragment(MedicalRecordsFragment.newInstance(patientId), "就诊记录");
        mVPAdapter.addFragment(SurgicalHistoryFragment.newInstance(patientId), "住院手术史");
        mVPAdapter.addFragment(TestIndicatorFragment.newInstance(patientId), "检验指标");
        mVPAdapter.addFragment(PrescriptionFragment.newInstance(patientId), "处方清单");
        mVPAdapter.addFragment(SymptomHRFragment.newInstance(patientId), "不适症状");
        mVPAdapter.addFragment(BPFragment.newInstance(patientId), "血压");
        mVPAdapter.addFragment(BSFragment.newInstance(patientId), "血糖");
        mVPAdapter.addFragment(UserInfoFragment.newInstance(patientId), "基本信息");
        mVPAdapter.addFragment(MedicalRecordFragment.newInstance(patientId), "自传资料");
        mViewPager.setAdapter(mVPAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                KeyboardUtil.hide(PatientInfoContainerActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mStlChatRoomTop.setViewPager(mViewPager);
        mViewPager.setCurrentItem(tabPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
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