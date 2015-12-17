package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.wonders.xlab.common.utils.ViewHelper;
import com.wonders.xlab.pci.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.login_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String tel = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ViewHelper.shakeEdit(mEtPhoneNumber, this);
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ViewHelper.shakeEdit(mEtPassword, this);
        }
        startActivity(new Intent(this, MainActivity.class));
    }
}

