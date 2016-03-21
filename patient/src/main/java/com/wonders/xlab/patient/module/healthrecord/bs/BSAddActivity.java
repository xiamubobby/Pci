package com.wonders.xlab.patient.module.healthrecord.bs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.RecordSavePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import im.hua.utils.KeyboardUtil;

/**
 * 手动测量血糖
 */
public class BSAddActivity extends AppbarActivity implements RecordSavePresenter.RecordSavePresenterListener {

    @Bind(R.id.tv_add_date)
    TextView mTvAddBsDate;
    @Bind(R.id.tv_add_time)
    TextView mTvAddBsTime;
    @Bind(R.id.et_add_bs)
    EditText mEtAddBs;
    @Bind(R.id.fab_add_bs)
    FloatingActionButton mFabAddBs;
    @Bind(R.id.sp_add_bs_period)
    Spinner mSpAddBsPeriod;

    private Calendar mCalendar = Calendar.getInstance();

    private IRecordSavePresenter mRecordSavePresenter;

    private ProgressDialog dialog;

    @Override
    public int getContentLayout() {
        return R.layout.bs_add_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRecordSavePresenter = new RecordSavePresenter(this);
        addPresenter(mRecordSavePresenter);

        initView();

        mRecordSavePresenter.getBSPeriodDic();
    }

    private void initView() {
        mTvAddBsDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddBsTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
    }

    @OnClick(R.id.fab_add_bs)
    public void save() {
        mFabAddBs.setClickable(false);

        KeyboardUtil.hide(this, mContentView.getWindowToken());

        String dateStr = mTvAddBsDate.getText().toString();
        String timeStr = mTvAddBsTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), DateUtil.DEFAULT_FORMAT_FULL);

        if (DateUtil.isBiggerThenToday(date)) {
            Toast.makeText(this, "不能选择今天之后的日期", Toast.LENGTH_SHORT).show();
            mFabAddBs.setClickable(true);
            return;
        }

        String bloodSugar = mEtAddBs.getText().toString();
        if (TextUtils.isEmpty(bloodSugar)) {
            Toast.makeText(this, "请输入血糖", Toast.LENGTH_SHORT).show();
            mFabAddBs.setClickable(true);
            return;
        }

        int periodIndex = mSpAddBsPeriod.getSelectedItemPosition();

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();
        mRecordSavePresenter.saveBSSingle(AIManager.getInstance(this).getPatientId(), date, periodIndex, Float.parseFloat(bloodSugar));
    }

    @OnClick(R.id.tv_add_date)
    public void onDateClick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvAddBsDate.setText(String.format("%s-%s-%s", year, monthOfYear + 1, dayOfMonth));
                    }
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_add_time)
    public void onTimeClick() {
        TimePickerDialog dialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTvAddBsTime.setText(String.format("%s:%s", hourOfDay, minute));
                    }
                },
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true);
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSaveRecordSuccess(String message) {
        mFabAddBs.setClickable(false);
        Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    @Override
    public void showBSPeriodDicList(List<String> periodList, int currentPeriodIndex) {
        List<HashMap<String, String>> mPeriodList = new ArrayList<>();

        for (String periodStr : periodList) {
            HashMap<String, String> periodMap = new HashMap<>();
            periodMap.put("name", periodStr);
            mPeriodList.add(periodMap);
        }

        mSpAddBsPeriod.setAdapter(new SimpleAdapter(this, mPeriodList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));
        mSpAddBsPeriod.setSelection(currentPeriodIndex);

    }

    @Override
    public void showError(String message) {
        mFabAddBs.setClickable(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
