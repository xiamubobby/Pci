package com.wonders.xlab.patient.module.medicineremind.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.MedicineRemindEditActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.SaveRemindSuccessOtto;
import com.wonders.xlab.patient.module.medicineremind.list.adapter.MedicineRemindRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;
import com.wonders.xlab.patient.util.AlarmUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 用药提醒
 */
public class MedicineRemindActivity extends AppbarActivity implements MedicineRemindPresenterContract.ViewListener {

    @Bind(R.id.recycler_view_medicine_remind)
    CommonRecyclerView mRecyclerView;

    private MedicineRemindRVAdapter mMedicineRemindRVAdapter;

    private MedicineRemindPresenterContract.Actions mMedicineRemindPresenter;
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

        mMedicineRemindPresenter = DaggerMedicineRemindComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicineRemindModule(new MedicineRemindModule(this))
                .build()
                .getMedicineRemindPresenter();

        addPresenter(mMedicineRemindPresenter);

        mPatientId = AIManager.getInstance().getPatientId();

        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 10));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicineRemindPresenter.getMedicineReminds(true);
            }
        });

        mMedicineRemindPresenter.getMedicineReminds(true);
    }


    @Subscribe
    public void onSaveRemindSuccess(SaveRemindSuccessOtto otto) {
        mMedicineRemindPresenter.getMedicineReminds(true);
    }

    @Override
    public void showMedicineRemind(List<MedicineRemindBean> medicineRemindBeanList) {
        initMedicineRemindAdapter();
        mMedicineRemindRVAdapter.setDatas(medicineRemindBeanList);
    }

    @Override
    public void appendMedicineRemind(List<MedicineRemindBean> medicineRemindBeanList) {
        initMedicineRemindAdapter();
        mMedicineRemindRVAdapter.appendDatas(medicineRemindBeanList);
    }

    @Override
    public void changeRemindStateSuccess(String message) {

    }

    private void initMedicineRemindAdapter() {
        if (mMedicineRemindRVAdapter == null) {
            mMedicineRemindRVAdapter = new MedicineRemindRVAdapter();
            mMedicineRemindRVAdapter.setSwitchChangeListener(new MedicineRemindRVAdapter.OnSwitchChangeListener() {
                @Override
                public void onSwitchStateChange(int position) {
                    MedicineRemindBean bean = mMedicineRemindRVAdapter.getBean(position);
                    mMedicineRemindPresenter.changeRemindState(bean.id.get(), !bean.shouldAlarm.get());

                    AlarmUtil.newInstance().scheduleMedicineRemindAlarm(MedicineRemindActivity.this);
                }
            });
            mMedicineRemindRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(MedicineRemindActivity.this, MedicineRemindEditActivity.class);
                    intent.putExtra(MedicineRemindEditActivity.EXTRA_MEDICINE_REMIND_ID, mMedicineRemindRVAdapter.getBean(position).id.get());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(mMedicineRemindRVAdapter);
        }
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
                startActivity(new Intent(MedicineRemindActivity.this, MedicineRemindEditActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mMedicineRemindPresenter.getMedicineReminds(true);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mMedicineRemindPresenter.getMedicineReminds(true);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mMedicineRemindPresenter.getMedicineReminds(true);
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

}
