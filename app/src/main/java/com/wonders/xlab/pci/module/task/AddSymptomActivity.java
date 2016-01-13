package com.wonders.xlab.pci.module.task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.base.mvn.entity.task.SymptomEntity;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.mvn.model.SymptomModel;
import com.wonders.xlab.pci.module.task.mvn.view.SymptomView;
import com.wonders.xlab.pci.module.task.otto.TaskRefreshOtto;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSymptomActivity extends AppbarActivity implements SymptomView, MeasureResultView {

    @Bind(R.id.container_add_symptom)
    LinearLayout mContainerAddSymptom;
    @Bind(R.id.fab_add_symptom)
    FloatingActionButton mFabAddSymptom;
    @Bind(R.id.refresh_add_symptom)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.empty)
    View mEmpty;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @Bind(R.id.tv_add_symptom_tip)
    TextView mTvTip;

    private SymptomModel mSymptomModel;

    private AddRecordModel mAddRecordModel;

    private LayoutInflater mInflater;

    private HashMap<String, String> mSelectedSymptomMap = new HashMap<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_symptom;
    }

    @Override
    public String getToolbarTitle() {
        return "主诉症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mEmpty.setVisibility(View.GONE);

        mSymptomModel = new SymptomModel(this);
        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);
        addModel(mSymptomModel);

        mInflater = LayoutInflater.from(this);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomModel.getSymptoms();
            }
        });

        mSymptomModel.getSymptoms();
    }

    @OnClick(R.id.fab_add_symptom)
    public void save() {
        if (mSelectedSymptomMap.size() > 0) {
            String[] symptomStrs = new String[mSelectedSymptomMap.size()];
            for (int i = 0; i < mSelectedSymptomMap.values().size(); i++) {
                symptomStrs[i] = mSelectedSymptomMap.values().toArray()[i].toString();
            }
            mAddRecordModel.saveSymptom(AIManager.getInstance(this).getUserId(), symptomStrs);
        } else {
            showSnackbar(mCoordinator, "请选择您的症状", true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private List<TextView> labelViews = new ArrayList<>();
    private TextView labelClear;//以上症状全无

    @Override
    public void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity) {
        mEmpty.setVisibility(View.GONE);
        mContainerAddSymptom.removeAllViews();
        mTvTip.setText(valuesEntity.getTips());
        for (SymptomEntity.RetValuesEntity.SymptomDtosEntity entity : valuesEntity.getSymptomDtos()) {
            LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.item_symptom, mContainerAddSymptom, false);
            TextView title = (TextView) itemView.findViewById(R.id.tv_item_symptom_title);
            title.setText(entity.getName());

            FlowLayout contents = (FlowLayout) itemView.findViewById(R.id.fl_item_symptom);
            for (SymptomEntity.RetValuesEntity.SymptomDtosEntity.SymptomsEntity symptomEntity : entity.getSymptoms()) {
                View view = mInflater.inflate(R.layout.item_symptom_content_label, itemView, false);

                final TextView labelView = (TextView) view.findViewById(R.id.tv_item_symptom_content_label);

                final TextView questionView = (TextView) view.findViewById(R.id.tv_item_symptom_question);
                if (!TextUtils.isEmpty(symptomEntity.getSymptomUrl())) {
                    questionView.setVisibility(View.VISIBLE);
                    questionView.setTag(symptomEntity.getSymptomUrl());
                    questionView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = String.valueOf(v.getTag());

                            View dialogView = mInflater.inflate(R.layout.dialog_add_symptom_guide, null, false);
                            ImageView imageView = (ImageView) dialogView.findViewById(R.id.iv_dialog_add_symptom);

                            Glide.with(AddSymptomActivity.this)
                                    .load(url)
                                    .crossFade()
                                    .fitCenter()
                                    .into(imageView);

                            final Dialog dialog = new Dialog(AddSymptomActivity.this, R.style.dialog);
                            dialogView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_UP:
                                            dialog.dismiss();
                                            break;
                                    }
                                    return false;
                                }
                            });
                            dialog.setContentView(dialogView);
                            dialog.show();
                        }
                    });
                } else {
                    questionView.setVisibility(View.GONE);
                }

                labelView.setText(symptomEntity.getName());
                //将标签id设置在label的tag中
                labelView.setTag(symptomEntity.getId());

                if (symptomEntity.getName().contains("症状全无")) {
                    labelClear = labelView;
                }

                labelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (labelClear == v) {
                            //点击以上全无
                            boolean isSelected = labelClear.isSelected();
                            //1、clear all
                            mSelectedSymptomMap.clear();
                            for (int i = 0; i < labelViews.size(); i++) {
                                labelViews.get(i).setSelected(false);
                            }

                            labelClear.setSelected(isSelected);
                            //2、add new
                            if (!v.isSelected()) {
                                mSelectedSymptomMap.put(v.getTag().toString(), v.getTag().toString());
                            } else {
                                mSelectedSymptomMap.remove(v.getTag().toString());
                            }
                            labelClear.setSelected(!labelClear.isSelected());
                        } else {
                            if (!labelClear.isSelected()) {
                                if (!v.isSelected()) {
                                    mSelectedSymptomMap.put(v.getTag().toString(), v.getTag().toString());
                                } else {
                                    mSelectedSymptomMap.remove(v.getTag().toString());
                                }
                                labelView.setSelected(!labelView.isSelected());
                            }
                        }

                    }
                });

                contents.addView(view);

                labelViews.add(labelView);
            }
            mContainerAddSymptom.addView(itemView);
        }
    }

    @Override
    public void getSymptomsFailed(String message) {
        mEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void saveSuccess() {

    }

    @Override
    public void saveFailed(String message) {
        mEmpty.setVisibility(View.GONE);
    }

    @Override
    public void svShowLoading() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();
    }

    @Override
    public void svHideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void svSuccess() {
        mFabAddSymptom.setClickable(false);
        showSnackbar(mCoordinator, "数据保存成功", true);
        RxBus.getInstance().send(new TaskRefreshOtto());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    @Override
    public void svDuplicate() {

    }

    @Override
    public void svFailed(String message) {
        mFabAddSymptom.setClickable(true);
        showSnackbar(mCoordinator, message, true);
    }

    private ProgressDialog dialog;

    @Override
    public void showLoading() {
        if (mRefresh != null) {
            mRefresh.setRefreshing(true);
        }
        mEmpty.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                if (mRefresh != null) {
                    mRefresh.setRefreshing(false);
                }
            }
        });
    }
}
