package com.wonders.xlab.pci.doctor.module.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.MainActivity;
import com.wonders.xlab.pci.doctor.mvp.presenter.LoginPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ILoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseActivity;
import im.hua.utils.KeyboardUtil;
import im.hua.utils.ViewHelper;

public class LoginActivity extends BaseActivity implements ILoginPresenter {

    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.coordinate)
    CoordinatorLayout mCoordinate;

    private LoginPresenter mLoginPresenter;

    private ProgressDialog mDialog;

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
        KeyboardUtil.hide(this, mCoordinate.getWindowToken());
        String tel = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ViewHelper.shakeEdit(mEtPassword, this);
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (null == mDialog) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("正在登录，请稍候...");
        }
        mDialog.show();

        mLoginPresenter.login(tel, password);
    }

    @Override
    public void loginSuccess(String userId, String tel, String avatarUrl) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        AIManager.getInstance(this).saveUserInfo(userId, tel, avatarUrl);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showError(String message) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
