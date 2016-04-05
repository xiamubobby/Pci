package com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
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
import com.wonders.xlab.patient.mvp.presenter.IBSSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BSSavePresenter;

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
public class BSAddActivity extends AppbarActivity implements BSSavePresenter.BSSavePresenterListener {

    @Bind(R.id.tv_add_date)
    TextView mTvAddBsDate;
    @Bind(R.id.tv_add_time)
    TextView mTvAddBsTime;
    @Bind(R.id.et_add_bs)
    EditText mEtAddBs;
    @Bind(R.id.sp_add_bs_period)
    Spinner mSpAddBsPeriod;

    private IBSSavePresenter mRecordSavePresenter;

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

        mRecordSavePresenter = new BSSavePresenter(this);
        addPresenter(mRecordSavePresenter);

        initView();

        mRecordSavePresenter.getBSPeriodDic();
    }

    private void initView() {
        Calendar mCalendar = Calendar.getInstance();
        mTvAddBsDate.setText(DateUtil.format(mCalendar.getTimeInMillis(),"yyyy.MM.dd"));
        mTvAddBsTime.setText(DateUtil.format(mCalendar.getTimeInMillis(),"HH:mm"));
    }

    public void save() {

        KeyboardUtil.hide(this);

        String dateStr = mTvAddBsDate.getText().toString();
        String timeStr = mTvAddBsTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), "yyyy.MM.dd HH:mm");

        String bloodSugar = mEtAddBs.getText().toString();
        if (TextUtils.isEmpty(bloodSugar)) {
            showShortToast("请输入血糖");
            return;
        }

        int periodIndex = mSpAddBsPeriod.getSelectedItemPosition();

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();
        mRecordSavePresenter.saveBSSingle(AIManager.getInstance().getPatientId(), date, periodIndex, Float.parseFloat(bloodSugar));
    }

    private boolean mIsToday = true;
    @OnClick(R.id.tv_add_date)
    public void onDateClick() {
        Calendar mCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year,monthOfYear,dayOfMonth);
                        mIsToday = DateUtils.isToday(calendar.getTimeInMillis());
                        mTvAddBsDate.setText(DateUtil.format(calendar.getTimeInMillis(),"yyyy.MM.dd"));
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
        Calendar mCalendar = Calendar.getInstance();
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
                        mTvAddBsTime.setText(DateUtil.format(calendar.getTimeInMillis(),"HH:mm"));
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
    public void onSaveBSSuccess(String message) {
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
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
}
