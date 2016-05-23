package com.wonders.xlab.patient.module.dailyreport.datarecord.symptom;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.auth.login.LoginActivity;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.presenter.ISymptomPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.SymptomRecordPresenter;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 不适症状选择
 */
public class SymptomActivity extends AppbarActivity implements SymptomRecordPresenter.SymptomPresenterListener {


    @Bind(R.id.container_add_symptom)
    LinearLayout mContainerAddSymptom;
    @Bind(R.id.refresh_add_symptom)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.empty)
    View mEmpty;
    @Bind(R.id.tv_add_symptom_tip)
    TextView mTvTip;

    private ISymptomPresenter mSymptomPresenter;

    private LayoutInflater mInflater;

    private HashMap<String, String> mSelectedSymptomMap = new HashMap<>();

    @Override
    public int getContentLayout() {
        return R.layout.symptom_add_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "不适症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AIManager.getInstance().hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        ButterKnife.bind(this);

        mEmpty.setVisibility(View.GONE);

        mSymptomPresenter = new SymptomRecordPresenter(this);
        addPresenter(mSymptomPresenter);

        mInflater = LayoutInflater.from(this);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomPresenter.getSymptoms();
            }
        });

        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
            }
        });
        mSymptomPresenter.getSymptoms();
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
            LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.symptom_record_item, mContainerAddSymptom, false);
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

                            Glide.with(SymptomActivity.this)
                                    .load(url)
                                    .crossFade()
                                    .fitCenter()
                                    .into(imageView);

                            final Dialog dialog = new Dialog(SymptomActivity.this, R.style.dialog);
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

    @Override
    public void onSaveSymptomSuccess(String message) {
        Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
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
    public void showToast(String message) {

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
        dismissProgressDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_symptom,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_symptom_save:
                if (mSelectedSymptomMap.size() > 0) {
                    String[] symptomStr = new String[mSelectedSymptomMap.size()];
                    for (int i = 0; i < mSelectedSymptomMap.values().size(); i++) {
                        symptomStr[i] = mSelectedSymptomMap.values().toArray()[i].toString();
                    }

                    showProgressDialog("","正在保存，请稍候...", null);

                    mSymptomPresenter.saveSymptom(AIManager.getInstance().getPatientId(), symptomStr);
                } else {
                    showShortToast("请选择您的症状");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
