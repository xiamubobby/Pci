package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.MedicineBean;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchAllRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchHistoryRVAdapter;
import com.wonders.xlab.patient.mvp.presenter.MedicineSearchPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;

/**
 * 如果需要接收选择的结果，通过注册otto监听{@link MedicineBean}即可
 */
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
        mRecyclerViewAllMedicine.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewHistory.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
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
        KeyboardUtil.hide(this);
        mPresenter.search(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     *显示剂量输入对话框
     * @param bean
     */
    private void showInputAlertDialog(final MedicineBean bean) {
        final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(MedicineSearchActivity.this).inflate(R.layout.medicine_search_dialog, null, false);
        final EditText editText = (EditText) linearLayout.findViewById(R.id.et_medicine_search_dialog);
        final TextView textView = (TextView) linearLayout.findViewById(R.id.tv_medicine_search_dialog);
        textView.setText(bean.getFormOfDrug());
        final AlertDialog alertDialog = new AlertDialog.Builder(MedicineSearchActivity.this)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .setView(linearLayout).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dose = editText.getText().toString();
                        if (!TextUtils.isEmpty(dose) || dose.equals("0")) {
                            /**
                             * post threw the otto event bus
                             */
                            bean.setDose(dose);
                            OttoManager.post(bean);
                            alertDialog.dismiss();
                            KeyboardUtil.hide(MedicineSearchActivity.this);
                            MedicineSearchActivity.this.finish();
                        } else {
                            showShortToast("请输入每次服用剂量");
                        }
                    }
                });
            }
        });
        alertDialog.show();
        editText.requestFocus();
    }

    private void initMedicineSearchAllAdapter() {
        if (null == mAllRVAdapter) {
            mAllRVAdapter = new MedicineSearchAllRVAdapter();
            mAllRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(final int position) {
                    showInputAlertDialog(mAllRVAdapter.getBean(position));
                }
            });
            mRecyclerViewAllMedicine.setAdapter(mAllRVAdapter);
        }
    }

    @Override
    public void showMedicineList(List<MedicineBean> beanList) {
        initMedicineSearchAllAdapter();
        mAllRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendMedicineList(List<MedicineBean> beanList) {
        initMedicineSearchAllAdapter();
        mAllRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showSearchHistoryList(List<MedicineBean> beanList) {
        if (null == mHistoryRVAdapter) {
            mHistoryRVAdapter = new MedicineSearchHistoryRVAdapter();
            mHistoryRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    showInputAlertDialog(mHistoryRVAdapter.getBean(position));
                }
            });
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
