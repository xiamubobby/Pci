package com.wonders.xlab.pci.doctor.module.chatroom.symptom;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.data.presenter.impl.SymptomPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.adapter.SymptomRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomFragment extends BaseFragment implements SymptomPresenter.SymptomPresenterListener {
    public static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_symptom)
    CommonRecyclerView mRecyclerView;

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

    public SymptomFragment() {
        // Required empty public constructor
    }

    public static SymptomFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        SymptomFragment fragment = new SymptomFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.symptom_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomPresenter.getSymptomList(mPatientId);
            }
        });
        mSymptomPresenter = new SymptomPresenter(this);
        addPresenter(mSymptomPresenter);

        mSymptomPresenter.getSymptomList(mPatientId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSymptomRVAdapter = null;
        ButterKnife.unbind(this);
    }

    private void showCommentEditDialog(SymptomBean bean) {
        mEditingSymptomBean = bean;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.symptom_edit_dialog, null, false);
        mDialog = new AlertDialog.Builder(getActivity())
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
                        showProgressDialog("", "正在保存，请稍候...", null);

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
    public void saveCommentSuccess() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        KeyboardUtil.hide(getActivity());
        dismissProgressDialog();

        mEditingSymptomBean.setIsChecked(mTmpSymptomBean.getIsChecked());
        mEditingSymptomBean.setComment(mTmpSymptomBean.getComment());
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        KeyboardUtil.hide(getActivity());
        dismissProgressDialog();

        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }
}
