package com.wonders.xlab.patient.module.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.main.MainActivity;
import com.wonders.xlab.patient.mvp.presenter.LoginPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.ILoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseActivity;
import im.hua.utils.KeyboardUtil;
import im.hua.utils.ViewHelper;

public class LoginActivity extends BaseActivity implements ILoginPresenter {

    private LoginPresenter mLoginPresenter;
    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.container)
    LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        addPresenter(mLoginPresenter);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        KeyboardUtil.hide(this, mContainer.getWindowToken());
        String tel = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
            showShortToast("请输入手机号！");
            return;
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ViewHelper.shakeEdit(mEtPassword, this);
            showShortToast("请输入密码！");
            return;
        }

        showProgressDialog("", "正在登陆，请稍候...");
        mLoginPresenter.login(tel, password);
    }


    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void loginSuccess(String patientId, String tel, String portraitUrl, String patientName) {
        AIManager.getInstance(this).savePatientInfo(patientId, tel, portraitUrl, patientName);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
