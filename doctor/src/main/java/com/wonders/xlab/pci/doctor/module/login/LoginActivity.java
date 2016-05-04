package com.wonders.xlab.pci.doctor.module.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.XApplication;
import com.wonders.xlab.pci.doctor.module.MainActivity;
import com.wonders.xlab.pci.doctor.mvp.presenter.LoginPresenterContract;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseActivity;
import im.hua.utils.KeyboardUtil;
import im.hua.utils.ViewHelper;

public class LoginActivity extends BaseActivity implements LoginPresenterContract.ViewListener {

    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private LoginPresenterContract.Actions mLoginPresenter;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        Constant.setBaseUrl(this);

        mToolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText editText = new EditText(LoginActivity.this);
                editText.setText(SPManager.get(LoginActivity.this).getString(Constant.PREF_KEY_BASE_URL,"http://xlab-tech.com:45675/pci-doctor/v1/"));
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!TextUtils.isEmpty(editText.getText().toString())) {
                                    SPManager.get(LoginActivity.this).putString(Constant.PREF_KEY_BASE_URL,editText.getText().toString());
                                    Constant.setBaseUrl(LoginActivity.this);
                                    LoginActivity.this.recreate();
                                }
                            }
                        })
                        .create();
                alertDialog.show();
                return false;
            }
        });

        mLoginPresenter = DaggerLoginComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
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
    public void loginSuccess(String message) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    }
}
