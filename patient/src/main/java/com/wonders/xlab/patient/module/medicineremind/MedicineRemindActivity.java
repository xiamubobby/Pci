package com.wonders.xlab.patient.module.medicineremind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.adapter.MedicineRemindRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindBean;
import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindDataBean;
import com.wonders.xlab.patient.mvp.presenter.impl.MedicineRemindPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 用药提醒
 */
public class MedicineRemindActivity extends AppbarActivity implements MedicineRemindPresenter.MedicineRemindPresenterListener {

    public static final String EXTRA_PATIENT_ID = "patientId";

    private static final int REQUEST_CODE = 2333;

    @Bind(R.id.recycler_view_medicine_remind)
    CommonRecyclerView mRecyclerView;

    private MedicineRemindRVAdapter mMedicineRemindRVAdapter;

    private MedicineRemindPresenter mMedicineRemindPresenter;

    private String mPatientId;

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

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取用药提醒失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取用药提醒失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 5));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMedicineRemindPresenter.getMedicineRemindList(mPatientId, false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicineRemindPresenter.getMedicineRemindList(mPatientId, true);
            }
        });
        mMedicineRemindPresenter = new MedicineRemindPresenter(this);
        addPresenter(mMedicineRemindPresenter);

        mRecyclerView.setRefreshing(true);
        mMedicineRemindPresenter.getMedicineRemindList(mPatientId, true);
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
                startActivityForResult(new Intent(this, MedicineRemindEditActivity.class), REQUEST_CODE);
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

    private void initMedicineRemindRVAdapter() {
        if (null == mMedicineRemindRVAdapter) {
            mMedicineRemindRVAdapter = new MedicineRemindRVAdapter();
            mMedicineRemindRVAdapter.setOnItemClickListener(new MultiRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    MedicineRemindDataBean bean = (MedicineRemindDataBean) mMedicineRemindRVAdapter.getItemData(position);
                    Intent intent = new Intent(MedicineRemindActivity.this, MedicineRemindEditActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);


                }
            });
        }
    }

    @Override
    public void showMedicineRemindList(List<MedicineRemindBean> beanList) {
        initMedicineRemindRVAdapter();
        mRecyclerView.setAdapter(mMedicineRemindRVAdapter);
        mMedicineRemindRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendMedicineRemindList(List<MedicineRemindBean> beanList) {
        initMedicineRemindRVAdapter();
        mRecyclerView.setAdapter(mMedicineRemindRVAdapter);
        mMedicineRemindRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
        dismissProgressDialog();
    }
}
