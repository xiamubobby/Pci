package com.wonders.xlab.patient.module.healthrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.auth.authorize.AuthorizeActivity;
import com.wonders.xlab.patient.module.healthrecord.medicalrecords.MedicalRecordsActivity;
import com.wonders.xlab.patient.module.healthrecord.prescription.PrescriptionActivity;
import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.SurgicalHistoryActivity;
import com.wonders.xlab.patient.module.healthrecord.testindicator.TestIndicatorActivity;
import com.wonders.xlab.patient.module.home.adapter.HomeRVAdapter;
import com.wonders.xlab.patient.module.home.adapter.bean.HomeItemBean;
import com.wonders.xlab.patient.module.medicalpicture.MedicalPictureActivity;
import com.wonders.xlab.patient.mvp.presenter.HealthRecordPresenter;
import com.wonders.xlab.patient.mvp.presenter.HealthRecordPresenterContract;
import com.wonders.xlab.patient.util.ImageViewManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthRecordActivity extends AppbarActivity implements HealthRecordPresenterContract.ViewListener {

    public static final int REQUEST_AUTHORIZE_CODE = 1 << 11;

    @Bind(R.id.recycler_view_health_record)
    RecyclerView mRecyclerView;
    @Bind(R.id.btn_health_record_authorize)
    Button mBtnAuthorize;
    @Bind(R.id.tv_health_record_notice)
    TextView mTvAuthorizeNotice;
    @Bind(R.id.rl_health_record_authorized)
    RelativeLayout mRlAuthorized;
    @Bind(R.id.iv_health_record_portrait)
    ImageView mIvPortrait;


    private HomeRVAdapter homeRVAdapter;

    private HealthRecordPresenter mHealthRecordPresenter;

    private boolean canAuthorize = true;

    @Override
    public int getContentLayout() {
        return R.layout.health_record_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        ImageViewManager.setImageViewWithUrl(this, mIvPortrait, AIManager.getInstance().getPatientPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setupBottomFunctionView();

        mHealthRecordPresenter = DaggerHealthRecordComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .healthRecordModule(new HealthRecordModule(this))
                .build()
                .getHealthRecordPresenter();
        addPresenter(mHealthRecordPresenter);
        mHealthRecordPresenter.getValidateResult();

    }

    @OnClick(R.id.btn_health_record_authorize)
    public void authorize() {
        if (canAuthorize) {
            Intent intent = new Intent(HealthRecordActivity.this, AuthorizeActivity.class);
            startActivityForResult(intent, REQUEST_AUTHORIZE_CODE);
        }
    }

    private void setupBottomFunctionView() {
        if (null == homeRVAdapter) {
            homeRVAdapter = new HomeRVAdapter();
            homeRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(HealthRecordActivity.this, TestIndicatorActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(HealthRecordActivity.this, MedicalRecordsActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(HealthRecordActivity.this, SurgicalHistoryActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(HealthRecordActivity.this, PrescriptionActivity.class));
                            break;
                        case 4:
                            startActivity(new Intent(HealthRecordActivity.this, MedicalPictureActivity.class));
                            break;

                    }
                }
            });
            ArrayList<HomeItemBean> beanArrayList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                HomeItemBean homeItemBean = new HomeItemBean();
                int drawableResId = R.drawable.ic_home_daily_record;
                int backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                String title = null;

                switch (i) {
                    case 0:
                        backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                        drawableResId = R.drawable.ic_home_daily_record;
                        title = "检验指标";
                        break;
                    case 1:
                        backgroundDrawableId = R.drawable.shape_home_label_medical_report;
                        drawableResId = R.drawable.ic_home_medical_record;
                        title = "就诊记录";
                        break;
                    case 2:
                        backgroundDrawableId = R.drawable.shape_home_label_medicine_remind;
                        drawableResId = R.drawable.ic_home_medicine_remind;
                        title = "住院手术史";
                        break;
                    case 3:
                        backgroundDrawableId = R.drawable.shape_home_label_health_report;
                        drawableResId = R.drawable.ic_home_health_report;
                        title = "处方清单";
                        break;
                    case 4:
                        backgroundDrawableId = R.drawable.shape_home_label_medical_report;
                        drawableResId = R.drawable.ic_home_medical_record;
                        title = "补充资料";
                        break;
                }
                homeItemBean.setBackgroundDrawableId(backgroundDrawableId);
                homeItemBean.setDrawableResId(drawableResId);
                homeItemBean.setTitle(title);

                beanArrayList.add(homeItemBean);
            }
            homeRVAdapter.setDatas(beanArrayList);
        }
        mRecyclerView.setAdapter(homeRVAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showResultMessage(String message) {

    }


    @Override
    public void showValidateButton(int state) {
        showStateView(state);

       /* if (show) {
            int measuredHeight = mTvNotice.getMeasuredHeight();
            mTvNotice.setTop(-measuredHeight);
//            ObjectAnimator animator = ObjectAnimator.ofFloat();
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            translateAnimation.setDuration(800);
            translateAnimation.setFillEnabled(true);
            translateAnimation.setFillAfter(true);
            translateAnimation.setInterpolator(new AccelerateInterpolator());
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mBtnAuthorize.setVisibility(View.VISIBLE);
                    mTvNotice.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mTvNotice.startAnimation(translateAnimation);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        if (requestCode == REQUEST_AUTHORIZE_CODE) {
            int state = data.getIntExtra(AuthorizeActivity.AUTHORIZE_STATE, 0);
            showStateView(state);

        }
    }

    private void showStateView(int state) {
        if (state == 0) {
            canAuthorize = true;
            mBtnAuthorize.setText(getString(R.string.health_record_btn_un_authorize));
            mBtnAuthorize.setBackgroundResource(R.drawable.selector_confirm_button_red);
            mTvAuthorizeNotice.setText(getString(R.string.text_un_authorize_notice));
            mTvAuthorizeNotice.setVisibility(View.VISIBLE);
        } else if (state == 1) {
            canAuthorize = false;
            mBtnAuthorize.setText(getString(R.string.health_record_btn_authorized));
            mBtnAuthorize.setBackgroundResource(R.drawable.selector_confirm_button_blue);
            mRlAuthorized.setVisibility(View.VISIBLE);
        } else if (state == 2) {
            canAuthorize = false;
            mBtnAuthorize.setText(getString(R.string.health_record_btn_authorizing));
            mBtnAuthorize.setBackgroundResource(R.drawable.selector_confirm_button_blue);
            mTvAuthorizeNotice.setText(getString(R.string.text_authorizing_notice));
            mTvAuthorizeNotice.setVisibility(View.VISIBLE);
        } else if (state == 3) {
            canAuthorize = true;
            mBtnAuthorize.setText(getString(R.string.health_record_btn_authorized_failed));
            mBtnAuthorize.setBackgroundResource(R.drawable.selector_confirm_button_red);
            mTvAuthorizeNotice.setText(getString(R.string.text_authorized_fail_notice));
            mTvAuthorizeNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading(String message) {
        showShortToast(message);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView(String message) {
        showShortToast(message);
    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }
}
