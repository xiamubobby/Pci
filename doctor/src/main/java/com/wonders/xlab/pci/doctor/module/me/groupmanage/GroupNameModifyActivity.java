package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupNameModifyActivity extends AppbarActivity {
    public final static int RESULT_CODE_SUCCESS = 12345;
    public final static String EXTRA_RESULT = "result";

    public final static String EXTRA_HINT = "hint";
    public final static String EXTRA_TEXT = "text";
    private String mHint;
    private String mText;

    @Bind(R.id.til_group_name_modify)
    TextInputLayout mTilGroupName;

    @Bind(R.id.et_group_name_modify)
    EditText mEtGroupName;

    @Override
    public int getContentLayout() {
        return R.layout.group_name_modify_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null != intent) {
            mHint = intent.getStringExtra(EXTRA_HINT);
            mText = intent.getStringExtra(EXTRA_TEXT);
            if (!TextUtils.isEmpty(mHint)) {
                mTilGroupName.setHint("请编辑" + mHint);
                mTilGroupName.setHintEnabled(true);
                setToolbarTitle(mHint);
            }
            if (!TextUtils.isEmpty(mText)) {
                mEtGroupName.setText(mText);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                String text = mEtGroupName.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    showShortToast("请输入" + mHint);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_RESULT, text);
                    setResult(RESULT_CODE_SUCCESS, intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String text = mEtGroupName.getText().toString();
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(mText) && !mText.equals(text)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setNegativeButton("继续编辑", null)
                    .setPositiveButton("舍弃", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setMessage("内容尚未保存，请选择继续编辑还是舍弃内容");
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }

}
