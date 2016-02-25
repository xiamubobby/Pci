package com.wonders.xlab.pci.doctor.module.symptom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.symptom.adapter.SymptomRVAdapter;
import com.wonders.xlab.pci.doctor.module.symptom.bean.SymptomBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.SymptomPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ISymptomPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SymptomActivity extends AppbarActivity implements ISymptomPresenter{

    @Bind(R.id.recycler_view_symptom)
    PullLoadMoreRecyclerView mRecyclerView;

    private SymptomRVAdapter mSymptomRVAdapter;

    private SymptomPresenter mSymptomPresenter;

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

        mRecyclerView.setLinearLayout(false);

        mSymptomPresenter = new SymptomPresenter(this);
        addPresenter(mSymptomPresenter);

        mSymptomPresenter.getSymptomList();

    }

    private void showCommentEditDialog(@NonNull final SymptomBean bean) {
        View view = LayoutInflater.from(SymptomActivity.this).inflate(R.layout.symptom_edit_dialog, null, false);
        final AlertDialog mDialog = new AlertDialog.Builder(SymptomActivity.this)
                .setView(view)
                .setNegativeButton(getResources().getString(R.string.btn_abandon), null)
                .setPositiveButton(getResources().getString(R.string.btn_save), null)
                .create();

        final EditText text = (EditText) view.findViewById(R.id.et_symptom_edit_dialog);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_symptom_edit_dialog);
        text.setText(bean.getComment());
        checkBox.setChecked(bean.getIsChecked());

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bean.setComment(text.getText().toString());
                        bean.setIsChecked(checkBox.isChecked());
                        mDialog.dismiss();
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
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
