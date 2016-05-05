package com.wonders.xlab.patient.module.medicineremind.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindDataBean;
import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 用药提醒
 */
public class MedicineRemindEditActivity extends AppbarActivity {

    public static final String REMIND_ID = "remind_id";
    private static final int REQUEST_CODE = 2334;


    @Bind(R.id.recycler_view_medicine_remind_edit)
    CommonRecyclerView mRecyclerView;

    private int remindId;

    private MedicineRemindEditRVAdapter medicineRemindEditRVAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_remind_edit_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "提醒时间";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OttoManager.register(this);
        ButterKnife.bind(this);

        remindId = getIntent().getIntExtra(REMIND_ID,0);

        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.bg_gray), 1));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        medicineRemindEditRVAdapter = new MedicineRemindEditRVAdapter(this);
        MedicineRemindDataBean bean = new MedicineRemindDataBean();
        bean.setRemind(true);
        bean.setId(1);
        bean.setTimePeriod("上午");
        bean.setTimeStr("10:00");
        bean.setStartTime("2016-04-23");
        bean.setEndTime("2016-05-23");
        bean.setRemind(true);
        bean.setReminderDesc("药不能停");
        MedicineRemindDataBean.Medicine medicine = new MedicineRemindDataBean.Medicine();
        medicine.setId(1);
        medicine.setMedicineName("阿司匹林");
        medicine.setMedicineNum(3);
        medicine.setMedicineUnit("片");
        medicine.setUse(true);
        List<MedicineRemindDataBean.Medicine> medicines = new ArrayList<>();
        medicines.add(medicine);
        medicines.add(medicine);
        bean.setMedicines(medicines);

        medicineRemindEditRVAdapter.setMedicineRemindDataBean(bean);
        mRecyclerView.setAdapter(medicineRemindEditRVAdapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_medicine_remind_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_medicine_remind_edit:

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
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
