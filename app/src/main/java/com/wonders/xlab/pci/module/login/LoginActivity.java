package com.wonders.xlab.pci.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.wonders.xlab.common.utils.KeyboardUtil;
import com.wonders.xlab.common.utils.ViewHelper;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.MainActivity;
import com.wonders.xlab.pci.module.base.BaseActivity;
import com.wonders.xlab.pci.module.login.mvn.entity.LoginEntity;
import com.wonders.xlab.pci.module.login.mvn.model.LoginModel;
import com.wonders.xlab.pci.module.login.mvn.view.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.container)
    CoordinatorLayout mContainer;

    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (AIManager.getInstance(this).hasLogin()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            mLoginModel = new LoginModel(this);
            addModel(mLoginModel);
        }

    }

    @OnClick(R.id.btn_login)
    public void login() {
        KeyboardUtil.hide(this, mContainer.getWindowToken());
        String tel = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
            return;
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ViewHelper.shakeEdit(mEtPassword, this);
            return;
        }
        mLoginModel.login(tel, password);
    }

    @Override
    public void loginSuccess(LoginEntity.RetValuesEntity value) {
        AIManager.getInstance(this).saveUserInfo(String.valueOf(value.getId()), value.getTel(), value.getMedicareCard());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String message) {
        Snackbar.make(mContainer, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Snackbar.make(mContainer, "正在登录，请稍候...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }
}

