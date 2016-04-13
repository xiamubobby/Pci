package com.wonders.xlab.pci.doctor.module.chatroom.symptom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.adapter.SymptomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.SymptomPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.KeyboardUtil;

public class SymptomActivity extends AppbarActivity implements SymptomPresenter.SymptomPresenterListener {
    public static final String EXTRA_PATIENT_ID = "patientId";
    @Bind(R.id.coordinate)
    CoordinatorLayout mCoordinate;

    private String mPatientId;

    @Bind(R.id.recycler_view_symptom)
    PullLoadMoreRecyclerView mRecyclerView;

    private SymptomRVAdapter mSymptomRVAdapter;

    private SymptomPresenter mSymptomPresenter;

    /**
     * 正在修改的主诉症状
     */
    private SymptomBean mEditingSymptomBean;
    /**
     * 缓存正在修改的主诉症状(包括备注)
     */
    private SymptomBean mTmpSymptomBean = new SymptomBean();

    private AlertDialog mDialog;

    private ProgressDialog mProgressDialog;

    @Override
    public int getContentLayout() {
        return R.layout.symptom_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "不适症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mPatientId = getIntent().getExtras().getString(EXTRA_PATIENT_ID);

        mRecyclerView.setLinearLayout(false);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mSymptomPresenter.getSymptomList(mPatientId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mSymptomPresenter = new SymptomPresenter(this);
        addPresenter(mSymptomPresenter);

        mRecyclerView.setRefreshing(true);
        mSymptomPresenter.getSymptomList(mPatientId);
    }

    private void showCommentEditDialog(SymptomBean bean) {
        mEditingSymptomBean = bean;
        View view = LayoutInflater.from(SymptomActivity.this).inflate(R.layout.symptom_edit_dialog, null, false);
        mDialog = new AlertDialog.Builder(SymptomActivity.this)
                .setView(view)
                .setNegativeButton(getResources().getString(R.string.btn_abandon), null)
                .setPositiveButton(getResources().getString(R.string.btn_save), null)
                .create();

        final EditText text = (EditText) view.findViewById(R.id.et_symptom_edit_dialog);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_symptom_edit_dialog);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditingSymptomBean.getIsChecked()) {
                    if (null != s && s.length() > 0) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        text.setText(mEditingSymptomBean.getComment());
        checkBox.setChecked(mEditingSymptomBean.getIsChecked());
        if (mEditingSymptomBean.getIsChecked()) {
            checkBox.setClickable(false);
        }

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProgressDialog("", "正在保存，请稍候...");

                        mSymptomPresenter.saveComment(mEditingSymptomBean.getSymptomId(), AIManager.getInstance().getDoctorId(), text.getText().toString(), checkBox.isChecked());

                        mTmpSymptomBean.setComment(text.getText().toString());
                        mTmpSymptomBean.setIsChecked(checkBox.isChecked());

                    }
                });
            }
        });

        mDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showSymptomList(List<SymptomBean> symptomBeanList) {
        mRecyclerView.setPullLoadMoreCompleted();
        if (null == mSymptomRVAdapter) {
            mSymptomRVAdapter = new SymptomRVAdapter();
            mSymptomRVAdapter.setOnCommentClickListener(new SymptomRVAdapter.OnCommentClickListener() {
                @Override
                public void onBtnClick(SymptomBean bean) {
                    showCommentEditDialog(bean);
                }

                @Override
                public void onCommentClick(SymptomBean bean) {
                    showCommentEditDialog(bean);
                }
            });
        }
        mSymptomRVAdapter.setDatas(symptomBeanList);

        mRecyclerView.setAdapter(mSymptomRVAdapter);
    }

    @Override
    public void saveCommentSuccess() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        KeyboardUtil.hide(this);
        dismissProgressDialog();

        mEditingSymptomBean.setIsChecked(mTmpSymptomBean.getIsChecked());
        mEditingSymptomBean.setComment(mTmpSymptomBean.getComment());
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.setPullLoadMoreCompleted();
        if (null != mDialog) {
            mDialog.dismiss();
        }
        KeyboardUtil.hide(this);
        dismissProgressDialog();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void hideLoading() {

    }
}
