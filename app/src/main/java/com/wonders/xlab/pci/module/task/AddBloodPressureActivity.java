package com.wonders.xlab.pci.module.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBloodPressureActivity extends AppbarActivity {

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

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_blood_pressure;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mTvAddBpDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddBpTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
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
    }
}
