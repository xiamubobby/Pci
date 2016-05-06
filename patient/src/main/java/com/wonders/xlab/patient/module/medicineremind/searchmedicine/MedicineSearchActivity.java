package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchAllRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchHistoryRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.bean.MedicineSearchAllBean;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.bean.MedicineSearchHistoryBean;
import com.wonders.xlab.patient.mvp.presenter.MedicineSearchPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.crv.CommonRecyclerView;

public class MedicineSearchActivity extends AppbarActivity implements MedicineSearchPresenterContract.ViewListener {

    @Bind(R.id.et_medicine_search_name)
    EditText mEtMedicineSearchName;
    @Bind(R.id.recycler_view_medicine_search_history)
    RecyclerView mRecyclerViewHistory;
    @Bind(R.id.recycler_view_medicine_search_all_medicine)
    CommonRecyclerView mRecyclerViewAllMedicine;

    private MedicineSearchAllRVAdapter mAllRVAdapter;
    private MedicineSearchHistoryRVAdapter mHistoryRVAdapter;

    private MedicineSearchPresenterContract.Actions mPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_search_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerViewAllMedicine.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerViewHistory.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerViewHistory.setItemAnimator(new DefaultItemAnimator());

        mPresenter = DaggerMedicineSearchComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicineSearchModule(new MedicineSearchModule(this))
                .build()
                .getMedicineSearchPresenter();
        mPresenter.getSearchHistory();
        mPresenter.getAllMedicines();
    }

    @OnClick(R.id.btn_medicine_search)
    public void search() {
        String name = mEtMedicineSearchName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        mPresenter.search(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMedicineList(List<MedicineSearchAllBean> beanList) {
        if (null == mAllRVAdapter) {
            mAllRVAdapter = new MedicineSearchAllRVAdapter();
            mRecyclerViewAllMedicine.setAdapter(mAllRVAdapter);
        }
        mAllRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendMedicineList(List<MedicineSearchAllBean> beanList) {
        if (null == mAllRVAdapter) {
            mAllRVAdapter = new MedicineSearchAllRVAdapter();
            mRecyclerViewAllMedicine.setAdapter(mAllRVAdapter);
        }
        mAllRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showSearchHistoryList(List<MedicineSearchHistoryBean> beanList) {
        if (null == mHistoryRVAdapter) {
            mHistoryRVAdapter = new MedicineSearchHistoryRVAdapter();
            mRecyclerViewHistory.setAdapter(mHistoryRVAdapter);
        }
        mHistoryRVAdapter.setDatas(beanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

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

    }
}
