package com.wonders.xlab.patient.module.main.home.medicineremind;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 用药提醒
 */
public class MedicineRemindActivity extends AppbarActivity {

    private static final int REQUEST_CODE = 2333;

    @Bind(R.id.recycler_view_medicine_remind)
    CommonRecyclerView mRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_remind_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "用药提醒";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OttoManager.register(this);
        ButterKnife.bind(this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medicine_remind, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_medicine_remind_add:
                startActivityForResult(new Intent(this,MedicineRemindEditActivity.class),REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //TODO 刷新页面
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_medicine_remind));
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_medicine_remind));
        MobclickAgent.onPause(this);
    }
}
