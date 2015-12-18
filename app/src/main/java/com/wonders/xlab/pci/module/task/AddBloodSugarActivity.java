package com.wonders.xlab.pci.module.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

public class AddBloodSugarActivity extends AppbarActivity {

    @Bind(R.id.tv_add_bs_date)
    TextView mTvAddBsDate;
    @Bind(R.id.tv_add_bs_time)
    TextView mTvAddBsTime;
    @Bind(R.id.tv_add_bs_period)
    TextView mTvAddBsPeriod;
    @Bind(R.id.et_add_bs_rate)
    EditText mEtAddBsRate;
    @Bind(R.id.fab_add_bs)
    FloatingActionButton mFabAddBs;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_blood_sugar;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_bs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        mTvAddBsDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddBsTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
    }

    @OnClick(R.id.tv_add_bs_date)
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
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_add_bs_time)
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

    @OnClick(R.id.tv_add_bs_period)
    public void onPeriodClick() {
        PopupMenu popupMenu = new PopupMenu(this, mTvAddBsPeriod, Gravity.RIGHT);
        popupMenu.inflate(R.menu.menu_meal_period);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mTvAddBsPeriod.setText(item.getTitle());
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
