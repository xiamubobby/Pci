package com.wonders.xlab.patient.module.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.MainActivity;
import com.wonders.xlab.patient.module.auth.FinishLoginOtto;
import com.wonders.xlab.patient.module.auth.register.di.DaggerRegisterComponent;
import com.wonders.xlab.patient.module.auth.register.di.RegisterModule;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseActivity;
import im.hua.utils.KeyboardUtil;

public class RegisterActivity extends BaseActivity implements RegisterContract.ViewListener {

    @Bind(R.id.login_phone_number)
    EditText loginPhoneNumber;
    @Bind(R.id.et_register_cap)
    EditText etRegisterCap;
    @Bind(R.id.btn_register_cap)
    Button btnRegisterCap;
    @Bind(R.id.register_password)
    EditText registerPassword;

    private RegisterContract.Presenter mRegisterPresenter;

    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnRegisterCap.setText(String.format("%ss后重试", String.valueOf(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                btnRegisterCap.setEnabled(true);
                btnRegisterCap.setText("获取验证码");
            }
        };

        mRegisterPresenter = DaggerRegisterComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .registerModule(new RegisterModule(this))
                .build()
                .getRegisterPresenter();
    }

    @OnClick(R.id.btn_register_cap)
    public void getCapture() {
        String tel = loginPhoneNumber.getText().toString();
        mCountDownTimer.start();
        btnRegisterCap.setEnabled(false);
        mRegisterPresenter.getCaptcha(tel);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        String tel = loginPhoneNumber.getText().toString();
        String pwd = registerPassword.getText().toString();
        String cap = etRegisterCap.getText().toString();

        if (TextUtils.isEmpty(tel) && tel.equals("")) {
            showShortToast("请填写11位的手机号！");
            return;
        }
        if (TextUtils.isEmpty(cap) && cap.equals("")) {
            showShortToast("请填写验证码！");
            return;
        }
        if (TextUtils.isEmpty(pwd) && pwd.equals("")) {
            showShortToast("请填写您的密码！");
            return;
        }
        mRegisterPresenter.register(tel, pwd, cap);
    }

    @OnClick(R.id.container)
    public void hideKeyboard() {
        KeyboardUtil.hide(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register_go_login:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCaptchaSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void onRegisterSuccess(String message) {
        OttoManager.post(new FinishLoginOtto());
        showShortToast(message);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
        mCountDownTimer.cancel();
        btnRegisterCap.setEnabled(true);
        btnRegisterCap.setText("获取验证码");
    }

    @Override
    public void showServerError(String message) {
        showShortToast(message);
        mCountDownTimer.cancel();
        btnRegisterCap.setEnabled(true);
        btnRegisterCap.setText("获取验证码");
    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
        mCountDownTimer.cancel();
        btnRegisterCap.setEnabled(true);
        btnRegisterCap.setText("获取验证码");
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }
}
