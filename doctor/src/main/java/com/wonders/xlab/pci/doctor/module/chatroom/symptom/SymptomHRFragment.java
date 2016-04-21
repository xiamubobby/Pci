package com.wonders.xlab.pci.doctor.module.chatroom.symptom;

import android.content.DialogInterface;
import android.os.Bundle;
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

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomReportBean;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.presenter.ISymptomReportPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.presenter.impl.SymptomReportPresenter;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;
import im.hua.utils.KeyboardUtil;

public class SymptomHRFragment extends BaseFragment implements SymptomReportPresenter.SymptomReportPresenterListener {
    public static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_symptom)
    CommonRecyclerView mRecyclerView;

    private SymptomAdapter adapter;

    private ISymptomReportPresenter mSymptomReportPresenter;

    /**
     * 正在修改的主诉症状
     */
    private SymptomReportBean mEditingSymptomBean;
    /**
     * 缓存正在修改的主诉症状(包括备注)
     */
    private SymptomReportBean mTmpSymptomBean = new SymptomReportBean();

    private AlertDialog mDialog;

    public SymptomHRFragment() {
        // Required empty public constructor
    }

    public static SymptomHRFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        SymptomHRFragment fragment = new SymptomHRFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.symptom_fragment, container, false);
        ButterKnife.bind(this, view);

        mSymptomReportPresenter = new SymptomReportPresenter(this);
        addPresenter(mSymptomReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * TODO addItemDecoration必须要在setItemAnimator后调用才有效果，不知道为什么，回头看下源码
         */
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 8));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomReportPresenter.getSymptomList(mPatientId, 0, Calendar.getInstance().getTimeInMillis(), true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mSymptomReportPresenter.getSymptomList(mPatientId, DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday(), false);
            }
        });
        mRecyclerView.setRefreshing(true);
        mSymptomReportPresenter.getSymptomList(mPatientId, 0, Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    private void showCommentEditDialog(SymptomReportBean bean) {
        mEditingSymptomBean = bean;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.symptom_edit_dialog, null, false);
        mDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setNegativeButton(getResources().getString(R.string.btn_abandon), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KeyboardUtil.hide(getActivity());
                    }
                })
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
                if (!mEditingSymptomBean.isHasConfirmed()) {
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
        text.setText(mEditingSymptomBean.getAdvice());
        checkBox.setChecked(mEditingSymptomBean.isHasConfirmed());
        if (mEditingSymptomBean.isHasConfirmed()) {
            checkBox.setClickable(false);
        }

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtil.hide(getActivity());

                        showProgressDialog("", "正在保存，请稍候...", null);

                        mSymptomReportPresenter.saveComment(mEditingSymptomBean.getId(), AIManager.getInstance().getDoctorId(), text.getText().toString(), checkBox.isChecked());

                        mTmpSymptomBean.setAdvice(text.getText().toString());
                        mTmpSymptomBean.setHasConfirmed(checkBox.isChecked());

                    }
                });
            }
        });

        mDialog.show();
    }

    @Override
    public void showSymptomList(List<SymptomReportBean> reportBeanList) {
        initAdapter();
        adapter.setDatas(reportBeanList);
    }

    @Override
    public void appendSymptomList(List<SymptomReportBean> reportBeanList) {
        initAdapter();
        adapter.appendDatas(reportBeanList);
    }

    @Override
    public void saveCommentSuccess() {
        KeyboardUtil.hide(getActivity());
        if (null != mDialog) {
            mDialog.dismiss();
        }
        dismissProgressDialog();

        mEditingSymptomBean.setHasConfirmed(mTmpSymptomBean.isHasConfirmed());
        mEditingSymptomBean.setAdvice(mTmpSymptomBean.getAdvice());
    }

    private void initAdapter() {
        if (null == adapter) {
            adapter = new SymptomAdapter();
            adapter.setOnCommentClickListener(new SymptomAdapter.OnCommentClickListener() {
                @Override
                public void onBtnClick(SymptomReportBean bean) {
                    showCommentEditDialog(bean);
                }
            });
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(String message) {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {

            }
        });
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

            }
        });
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
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}
