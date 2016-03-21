package com.wonders.xlab.patient.module.healthrecord.bp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.RecordSavePresenter;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import im.hua.utils.KeyboardUtil;

/**
 * 手动录入血压数据
 */
public class BPAddActivity extends AppbarActivity implements RecordSavePresenter.RecordSavePresenterListener {

    @Bind(R.id.tv_add_bp_date)
    TextView mTvAddBpDate;
    @Bind(R.id.tv_add_bp_time)
    TextView mTvAddBpTime;
    @Bind(R.id.et_add_bp_ssy)
    EditText mEtAddBpSsy;
    @Bind(R.id.et_add_bp_szy)
    EditText mEtAddBpSzy;
    @Bind(R.id.et_add_bp_rate)
    EditText mEtAddBpRate;
    @Bind(R.id.fab_add_bp)
    FloatingActionButton mFabAddBp;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private Calendar mCalendar = Calendar.getInstance();

    private IRecordSavePresenter mRecordSavePresenter;
    private ProgressDialog dialog;

    @Override
    public int getContentLayout() {
        return R.layout.bp_add_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRecordSavePresenter = new RecordSavePresenter(this);
        addPresenter(mRecordSavePresenter);

        initView();
    }

    private void initView() {
        mTvAddBpDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddBpTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
    }

    @OnClick(R.id.fab_add_bp)
    public void save() {
        mFabAddBp.setClickable(false);

        KeyboardUtil.hide(this, mContentView.getWindowToken());

        String dateStr = mTvAddBpDate.getText().toString();
        String timeStr = mTvAddBpTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), DateUtil.DEFAULT_FORMAT_FULL);
        if (DateUtil.isBiggerThenToday(date)) {
            Toast.makeText(this, "不能选择今天之后的日期", Toast.LENGTH_SHORT).show();
            mFabAddBp.setClickable(true);
            return;
        }

        String systolicPressure = mEtAddBpSsy.getText().toString();
        if (TextUtils.isEmpty(systolicPressure)) {
            Toast.makeText(this, "请输入收缩压", Toast.LENGTH_SHORT).show();
            mFabAddBp.setClickable(true);
            return;
        }
        String diastolicPressure = mEtAddBpSzy.getText().toString();
        if (TextUtils.isEmpty(diastolicPressure)) {
            Toast.makeText(this, "请输入舒张压", Toast.LENGTH_SHORT).show();
            mFabAddBp.setClickable(true);
            return;
        }
        String heartRate = mEtAddBpRate.getText().toString();

        if (TextUtils.isEmpty(heartRate)) {
            Toast.makeText(this, "请输入心率", Toast.LENGTH_SHORT).show();
            mFabAddBp.setClickable(true);
            return;
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();

        mRecordSavePresenter.saveBPSingle(AIManager.getInstance(this).getPatientId(), date, Integer.valueOf(heartRate), Integer.valueOf(systolicPressure), Integer.valueOf(diastolicPressure));
    }

    @OnClick(R.id.tv_add_bp_date)
    public void onDateClick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvAddBpDate.setText(String.format("%s-%s-%s", year, monthOfYear + 1, dayOfMonth));
                    }
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_add_bp_time)
    public void onTimeClick() {
        TimePickerDialog dialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTvAddBpTime.setText(String.format("%s:%s", hourOfDay, minute));
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
        mFabAddBp.setClickable(false);
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

    }

    @Override
    public void showError(String message) {
        mFabAddBp.setClickable(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
