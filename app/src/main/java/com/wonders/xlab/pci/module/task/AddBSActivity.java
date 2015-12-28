package com.wonders.xlab.pci.module.task;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.KeyboardUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.rxbus.TaskRefreshBus;
import com.wonders.xlab.pci.mvn.view.SimpleView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBSActivity extends AppbarActivity implements SimpleView {

    @Bind(R.id.tv_add_date)
    TextView mTvAddBsDate;
    @Bind(R.id.tv_add_time)
    TextView mTvAddBsTime;
    @Bind(R.id.tv_add_bs_period)
    TextView mTvAddBsPeriod;
    @Bind(R.id.et_add_bs)
    EditText mEtAddBs;
    @Bind(R.id.fab_add_bs)
    FloatingActionButton mFabAddBs;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private Calendar mCalendar = Calendar.getInstance();

    private AddRecordModel mAddRecordModel;

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

        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);

        initView();
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
            showSnackbar(mCoordinator, "不能选择今天之后的日期");
            mFabAddBs.setClickable(true);
            return;
        }

        String bloodSugar = mEtAddBs.getText().toString();
        if (TextUtils.isEmpty(bloodSugar)) {
            showSnackbar(mCoordinator, "请输入血糖");
            mFabAddBs.setClickable(true);
            return;
        }

         int periodIndex;
        String period = mTvAddBsPeriod.getText().toString();
        switch (period) {
            case "早餐前":
                periodIndex = 0;
                break;
            case "早餐后":
                periodIndex = 1;
                break;
            case "午餐前":
                periodIndex = 2;
                break;
            case "午餐后":
                periodIndex = 3;
                break;
            case "晚餐前":
                periodIndex = 4;
                break;
            case "晚餐后":
                periodIndex = 5;
                break;
            case "睡前":
                periodIndex = 6;
                break;
            default:
                periodIndex = 0;
        }

        mAddRecordModel.saveBS(AIManager.getInstance(this).getUserId(), date, periodIndex, Float.parseFloat(bloodSugar));
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

    @OnClick(R.id.tv_add_bs_period)
    public void onPeriodClick() {
        KeyboardUtil.hide(this, mTvAddBsPeriod.getWindowToken());
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
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void svSuccess() {
        mFabAddBs.setClickable(false);
        showSnackbar(mCoordinator, "数据保存成功");
        RxBus.getInstance().send(new TaskRefreshBus());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    @Override
    public void svFailed(String message) {
        mFabAddBs.setClickable(true);
        showSnackbar(mCoordinator, message);
    }

    private ProgressDialog dialog;

    @Override
    public void svShowLoading() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在保存，请稍候...");
        dialog.show();
    }

    @Override
    public void svHideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
