package com.wonders.xlab.patient.module.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.MainActivity;
import com.wonders.xlab.patient.module.auth.register.RegisterActivity;
import com.wonders.xlab.patient.mvp.presenter.LoginPresenterContract;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseActivity;
import im.hua.utils.KeyboardUtil;
import im.hua.utils.ViewHelper;

public class LoginActivity extends BaseActivity implements LoginPresenterContract.ViewListener, TextView.OnEditorActionListener {

    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.container)
    LinearLayout mContainer;

    private LoginPresenterContract.Actions mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mEtPassword.setOnEditorActionListener(this);

        mLoginPresenter = DaggerLoginComponent.builder()
                .applicationComponent(((XApplication)getApplication()).getComponent())
                .loginModule(new LoginModule(this))
                .build()
                .getLoginPresenter();


        addPresenter(mLoginPresenter);
    }

    @OnClick(R.id.container)
    public void hideKeyboard() {
        KeyboardUtil.hide(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        KeyboardUtil.hide(this);
        String tel = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
            showShortToast("手机号不能为空！");
            return;
        }
        if (tel.length() != 11) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
            showShortToast("请输入11位的手机号！");
            return;
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ViewHelper.shakeEdit(mEtPassword, this);
            showShortToast("请输入密码！");
            return;
        }

        mLoginPresenter.login(tel, password);
    }

    @OnClick(R.id.tv_login_go_register)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                login();
                break;
        }
        return false;
    }
}
