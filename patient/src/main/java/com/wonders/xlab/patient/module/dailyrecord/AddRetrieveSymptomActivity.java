package com.wonders.xlab.patient.module.dailyrecord;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.AddRecordPresenter;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.RetrieveSymptomPresenter;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl.IAddRecordPresenter;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl.IRetrieveSymptomPresenter;
import com.wonders.xlab.patient.module.dailyrecord.otto.TaskRefreshOtto;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRetrieveSymptomActivity extends AppbarActivity implements IRetrieveSymptomPresenter, IAddRecordPresenter {

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

    private RetrieveSymptomPresenter mRetrieveSymptomPresenter;

    private AddRecordPresenter mAddRecordPresenter;

    private LayoutInflater mInflater;

    private HashMap<String, String> mSelectedSymptomMap = new HashMap<>();

    @Override
    public int getContentLayout() {
        return R.layout.symptom_add_activity;
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

        mAddRecordPresenter = new AddRecordPresenter(this);
        addPresenter(mAddRecordPresenter);

        mRetrieveSymptomPresenter = new RetrieveSymptomPresenter(this);
        addPresenter(mRetrieveSymptomPresenter);

        mInflater = LayoutInflater.from(this);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRetrieveSymptomPresenter.getSymptoms();
            }
        });

        mRetrieveSymptomPresenter.getSymptoms();
    }

    @OnClick(R.id.fab_add_symptom)
    public void save() {
        if (mSelectedSymptomMap.size() > 0) {
            String[] symptomStr = new String[mSelectedSymptomMap.size()];
            for (int i = 0; i < mSelectedSymptomMap.values().size(); i++) {
                symptomStr[i] = mSelectedSymptomMap.values().toArray()[i].toString();
            }

            dialog = new ProgressDialog(this);
            dialog.setMessage("正在保存，请稍候...");
            dialog.show();

            mAddRecordPresenter.saveSymptom(AIManager.getInstance(this).getUserId(), symptomStr);
        } else {
            Toast.makeText(this, "请选择您的症状", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity) {
        mEmpty.setVisibility(View.GONE);
        mContainerAddSymptom.removeAllViews();
        mTvTip.setText(valuesEntity.getTips());
        for (SymptomEntity.RetValuesEntity.SymptomDtoEntity entity : valuesEntity.getSymptomDtos()) {
            LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.symptom_item, mContainerAddSymptom, false);
            TextView title = (TextView) itemView.findViewById(R.id.tv_item_symptom_title);
            title.setText(entity.getName());

            FlowLayout contents = (FlowLayout) itemView.findViewById(R.id.fl_item_symptom);
            for (SymptomEntity.RetValuesEntity.SymptomDtoEntity.SymptomsEntity symptomEntity : entity.getSymptoms()) {
                View view = mInflater.inflate(R.layout.symptom_content_label_item, itemView, false);

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

                            Glide.with(AddRetrieveSymptomActivity.this)
                                    .load(url)
                                    .crossFade()
                                    .fitCenter()
                                    .into(imageView);

                            final Dialog dialog = new Dialog(AddRetrieveSymptomActivity.this, R.style.dialog);
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

                labelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!v.isSelected()) {
                            mSelectedSymptomMap.put(v.getTag().toString(), v.getTag().toString());
                        } else {
                            mSelectedSymptomMap.remove(v.getTag().toString());
                        }
                        labelView.setSelected(!labelView.isSelected());

                    }
                });

                contents.addView(view);

            }
            mContainerAddSymptom.addView(itemView);
        }
    }

    private ProgressDialog dialog;

    @Override
    public void showError(String message) {
        mFabAddSymptom.setClickable(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSaveRecordSuccess(String message) {
        mFabAddSymptom.setClickable(false);
        Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();

        OttoManager.post(new TaskRefreshOtto());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }
}
