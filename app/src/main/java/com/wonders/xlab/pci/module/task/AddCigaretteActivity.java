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
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.mvn.view.SimpleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCigaretteActivity extends AppbarActivity implements SimpleView {

    @Bind(R.id.fab_add_cigarette)
    FloatingActionButton mFab;
    @Bind(R.id.et_add_cigarette)
    EditText mEtAddCigarette;
    @Bind(R.id.tv_add_date)
    TextView mTvAddDate;
    @Bind(R.id.tv_add_time)
    TextView mTvAddTime;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private Calendar mCalendar = Calendar.getInstance();

    private List<HashMap<String, Integer>> counts = new ArrayList<>();

    private AddRecordModel mAddRecordModel;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_cigarette;
    }

    @Override
    public String getToolbarTitle() {
        return "吸烟";
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
        mTvAddDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
    }

    @OnClick(R.id.fab_add_cigarette)
    public void save() {
        mFab.setClickable(false);
        String cigaretteCounts = mEtAddCigarette.getText().toString();
        if (TextUtils.isEmpty(cigaretteCounts)) {
            showSnackbar(mCoordinator, "请输入抽烟数!");
            mFab.setClickable(true);
            return;
        }else if (Integer.parseInt(cigaretteCounts) <= 0) {
            showSnackbar(mCoordinator, "吸烟根数必须大于0");
            mFab.setClickable(true);
            return;
        }

        KeyboardUtil.hide(this, mContentView.getWindowToken());

        String dateStr = mTvAddDate.getText().toString();
        String timeStr = mTvAddTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), DateUtil.DEFAULT_FORMAT_FULL);
        mAddRecordModel.saveCigarette(AIManager.getInstance(this).getUserId(), date, Integer.valueOf(cigaretteCounts));
    }

    @OnClick(R.id.tv_add_date)
    public void onDateClick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvAddDate.setText(String.format("%s-%s-%s", year, monthOfYear + 1, dayOfMonth));
                    }
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_add_time)
    public void onTimeClick() {
        TimePickerDialog dialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTvAddTime.setText(String.format("%s:%s", hourOfDay, minute));
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

    @Override
    public void svSuccess() {
        mFab.setClickable(false);
        showSnackbar(mCoordinator,"数据保存成功");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    @Override
    public void svFailed(String message) {
        mFab.setClickable(true);
        showSnackbar(mCoordinator,message);
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
