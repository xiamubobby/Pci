package com.wonders.xlab.patient.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.wonders.xlab.patient.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TextInputActivity extends AppbarActivity {
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_HINT = "hint";
    public final static String EXTRA_DEFAULT_VALUE = "defaultValue";
    public final static String EXTRA_INPUT_TYPE_NUMBER = "typeNumber";

    public final static String EXTRA_RESULT = "result";

    @Bind(R.id.et_text_input)
    EditText mEtTextInput;

    private String mTextTitle;
    private String mTextHint;
    private String mTextDefault;

    @Override
    public int getContentLayout() {
        return R.layout.text_input_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null == intent) {
            throw new IllegalArgumentException("you must set the intent");
        }
        Bundle data = intent.getExtras();
        if (null != data) {
            mTextTitle = data.getString(EXTRA_TITLE);
            mTextHint = data.getString(EXTRA_HINT);
            mTextDefault = data.getString(EXTRA_DEFAULT_VALUE);

            if (!TextUtils.isEmpty(mTextTitle)) {
                setToolbarTitle(mTextTitle);
            }
            if (!TextUtils.isEmpty(mTextHint)) {
                mEtTextInput.setHint(mTextHint);
            }
            if (!TextUtils.isEmpty(mTextDefault)) {
                mEtTextInput.setText(mTextDefault);
            }
            if (data.getBoolean(EXTRA_INPUT_TYPE_NUMBER, false)) {
                mEtTextInput.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            }
        }
        mEtTextInput.requestFocus();
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
                String result = mEtTextInput.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_RESULT,result);
                setResult(RESULT_OK, intent);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
