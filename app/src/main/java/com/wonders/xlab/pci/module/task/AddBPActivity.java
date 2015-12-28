package com.wonders.xlab.pci.module.task;

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

public class AddBPActivity extends AppbarActivity implements SimpleView {

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

    private AddRecordModel mAddRecordModel;
    private ProgressDialog dialog;

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

        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);

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

        String systolicPressure = mEtAddBpSsy.getText().toString();
        if (TextUtils.isEmpty(systolicPressure)) {
            showSnackbar(mCoordinator, "请输入收缩压");
            mFabAddBp.setClickable(true);
            return;
        }
        String diastolicPressure = mEtAddBpSzy.getText().toString();
        if (TextUtils.isEmpty(diastolicPressure)) {
            showSnackbar(mCoordinator, "请输入舒张压");
            mFabAddBp.setClickable(true);
            return;
        }
        String heartRate = mEtAddBpRate.getText().toString();

        if (TextUtils.isEmpty(heartRate)) {
            showSnackbar(mCoordinator, "请输入心率");
            mFabAddBp.setClickable(true);
            return;
        }

        String dateStr = mTvAddBpDate.getText().toString();
        String timeStr = mTvAddBpTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), DateUtil.DEFAULT_FORMAT_FULL);

        mAddRecordModel.saveBP(AIManager.getInstance(this).getUserId(), date, Integer.valueOf(heartRate), Integer.valueOf(systolicPressure), Integer.valueOf(diastolicPressure));
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
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void svSuccess() {
        mFabAddBp.setClickable(false);
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
        mFabAddBp.setClickable(true);
    }

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
