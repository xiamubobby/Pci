package com.wonders.xlab.patient.module.me.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.mvp.entity.HospitalDicEntity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenter;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;

public class UserInfoActivity extends AppbarActivity implements UserInfoPresenterContract.ViewListener {

    private final int REQUEST_CODE = 1;
    private final int REQUEST_CODE_DOCTOR = REQUEST_CODE << 1;
    private final int REQUEST_CODE_NUMBER = REQUEST_CODE << 2;
    private final int REQUEST_CODE_HISTORY = REQUEST_CODE << 3;
    private final int REQUEST_CODE_ADDRESS = REQUEST_CODE << 4;

    @Bind(R.id.tv_user_info_hospital)
    TextView mTvUserInfoHospital;
    @Bind(R.id.tv_user_info_doctor)
    TextView mTvUserInfoDoctor;
    @Bind(R.id.tv_user_info_surgery_time)
    TextView mTvUserInfoSurgeryTime;
    @Bind(R.id.tv_user_info_number)
    TextView mTvUserInfoNumber;
    @Bind(R.id.tv_user_info_history)
    TextView mTvUserInfoHistory;
    @Bind(R.id.tv_user_info_address)
    TextView mTvUserInfoAddress;

    private UserInfoPresenter mPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mPresenter = DaggerUserInfoComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .userInfoModule(new UserInfoModule(this))
                .build()
                .getUserInfoPresenter();

        mPresenter.getAllHospitals();
        mPresenter.getUserInfo();
    }

    @Override
    public void showUserInfo(UserInfoEntity.RetValuesEntity entity) {
        mTvUserInfoDoctor.setText(entity.getSurgeon());
        String timeStr = "";
        if (0 != entity.getLastOperationDate()) {
            timeStr = DateUtil.format(entity.getLastOperationDate(), "yyyy-MM-dd");
        }
        mTvUserInfoSurgeryTime.setText(timeStr);
        mTvUserInfoNumber.setText(entity.getBracketNum());
        mTvUserInfoHistory.setText(entity.getCaseHistory());
        mTvUserInfoAddress.setText(entity.getAddress());
    }

    @OnClick(R.id.tv_user_info_hospital)
    public void selectHospital() {
    }

    @OnClick(R.id.tv_user_info_doctor)
    public void editDoctor() {
        goToTextInputActivity(mTvUserInfoDoctor.getText().toString(), "请输入手术医生姓名", "手术医生", REQUEST_CODE_DOCTOR, false);
    }

    @OnClick(R.id.tv_user_info_number)
    public void editNumber() {
        goToTextInputActivity(mTvUserInfoNumber.getText().toString(), "请输入支架个数", "支架个数", REQUEST_CODE_NUMBER, true);
    }

    @OnClick(R.id.tv_user_info_history)
    public void editHistory() {
        goToTextInputActivity(mTvUserInfoHistory.getText().toString(), "请输入病史", "病史", REQUEST_CODE_HISTORY, false);
    }

    @OnClick(R.id.tv_user_info_address)
    public void editAddress() {
        goToTextInputActivity(mTvUserInfoAddress.getText().toString(), "请输入家庭住址", "住址", REQUEST_CODE_ADDRESS, false);
    }

    private void goToTextInputActivity(String defaultValue, String hint, String title, int requestCode, boolean isNumber) {
        Intent intent = new Intent(this, TextInputActivity.class);
        intent.putExtra(TextInputActivity.EXTRA_DEFAULT_VALUE, defaultValue);
        intent.putExtra(TextInputActivity.EXTRA_HINT, hint);
        intent.putExtra(TextInputActivity.EXTRA_TITLE, title);
        intent.putExtra(TextInputActivity.EXTRA_INPUT_TYPE_NUMBER, isNumber);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        String result = data.getStringExtra(TextInputActivity.EXTRA_RESULT);
        switch (requestCode) {
            case REQUEST_CODE_DOCTOR:
                mTvUserInfoDoctor.setText(result);
                break;
            case REQUEST_CODE_ADDRESS:
                mTvUserInfoAddress.setText(result);
                break;
            case REQUEST_CODE_HISTORY:
                mTvUserInfoHistory.setText(result);
                break;
            case REQUEST_CODE_NUMBER:
                mTvUserInfoNumber.setText(result);
                break;
        }
    }

    @Override
    public void modifyUserInfoSuccess(String message) {

    }

    @Override
    public void showHospitalList(List<HospitalDicEntity> dicEntityList) {
        Observable.from(dicEntityList)
                .subscribe(new Subscriber<HospitalDicEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HospitalDicEntity hospitalDicEntity) {
                    }
                });
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
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
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }
}
