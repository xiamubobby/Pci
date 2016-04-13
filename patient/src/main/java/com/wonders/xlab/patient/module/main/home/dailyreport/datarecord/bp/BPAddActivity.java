package com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.mvp.presenter.IBPSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BPSavePresenter;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import im.hua.utils.KeyboardUtil;

/**
 * 手动录入血压数据
 */
public class BPAddActivity extends AppbarActivity implements BPSavePresenter.RecordSavePresenterListener {

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
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private Calendar mCalendar = Calendar.getInstance();

    private IBPSavePresenter mIBPSavePresenter;
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

        mIBPSavePresenter = new BPSavePresenter(this);
        addPresenter(mIBPSavePresenter);

        initView();
    }

    private void initView() {
        mTvAddBpDate.setText(DateUtil.format(mCalendar.getTimeInMillis(),"yyyy.MM.dd"));
        mTvAddBpTime.setText(DateUtil.format(mCalendar.getTimeInMillis(),"HH:mm"));
    }

    private void save() {
        KeyboardUtil.hide(this);

        String dateStr = mTvAddBpDate.getText().toString();
        String timeStr = mTvAddBpTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), "yyyy.MM.dd HH:mm");

        String systolicPressure = mEtAddBpSsy.getText().toString();
        if (TextUtils.isEmpty(systolicPressure)) {
            showShortToast("请输入高压");
            return;
        }else if(TextUtils.isDigitsOnly(systolicPressure) && Integer.parseInt(systolicPressure) == 0){
            showShortToast("请输入正确的高压值");
            return;
        }
        String diastolicPressure = mEtAddBpSzy.getText().toString();
        if (TextUtils.isEmpty(diastolicPressure)) {
            showShortToast("请输入低压");
            return;
        }else if(TextUtils.isDigitsOnly(diastolicPressure) && Integer.parseInt(diastolicPressure) == 0){
            showShortToast("请输入正确的低压值");
            return;
        }
        String heartRate = mEtAddBpRate.getText().toString();
        if (TextUtils.isEmpty(heartRate)) {
            showShortToast("请输入心率");
            return;
        }else if(TextUtils.isDigitsOnly(heartRate) && Integer.parseInt(heartRate) == 0){
            showShortToast("请输入正确的心率值");
            return;
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();

        mIBPSavePresenter.saveBPSingle(AIManager.getInstance().getPatientId(), date, Integer.valueOf(heartRate), Integer.valueOf(systolicPressure), Integer.valueOf(diastolicPressure));
    }

    private boolean mIsToday = true;
    @OnClick(R.id.tv_add_bp_date)
    public void onDateClick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year,monthOfYear,dayOfMonth);
                        mIsToday = DateUtils.isToday(calendar.getTimeInMillis());
                        mTvAddBpDate.setText(DateUtil.format(calendar.getTimeInMillis(),"yyyy.MM.dd"));
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
        int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = mCalendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();

                        if (mIsToday) {
                            int maxHour = calendar.get(Calendar.HOUR_OF_DAY);
                            int maxMinute = calendar.get(Calendar.MINUTE);
                            if (hourOfDay >= maxHour) {
                                if (hourOfDay > maxHour) {
                                    showShortToast("不能选择大于当前的时间");
                                }
                                hourOfDay = maxHour;
                                if (minute > maxMinute) {
                                    minute = maxMinute;
                                }
                            }
                        }
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        mTvAddBpTime.setText(DateUtil.format(calendar.getTimeInMillis(),"HH:mm"));
                    }
                },
                currentHour,
                currentMinute,
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
    public void showNetworkError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSaveBPSuccess(String message) {
        showShortToast(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bp_add));
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bp_add));
        MobclickAgent.onPause(this);
    }
}
