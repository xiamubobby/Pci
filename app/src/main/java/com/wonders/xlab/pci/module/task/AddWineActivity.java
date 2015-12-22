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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

public class AddWineActivity extends AppbarActivity implements SimpleView {

    @Bind(R.id.et_add_wine)
    EditText mEtAddWine;
    @Bind(R.id.sp_add_wine)
    Spinner mSpAddWine;
    @Bind(R.id.fab_add_wine)
    FloatingActionButton mFab;
    @Bind(R.id.tv_add_date)
    TextView mTvAddDate;
    @Bind(R.id.tv_add_time)
    TextView mTvAddTime;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private Calendar mCalendar = Calendar.getInstance();

    private AddRecordModel mAddRecordModel;

    private List<HashMap<String, String>> mWineTypeList;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_wine;
    }

    @Override
    public String getToolbarTitle() {
        return "饮酒";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);

        initView();
        initWineTypeSpinner();
    }

    private void initView() {
        mTvAddDate.setText(String.format("%s-%s-%s", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH)));
        mTvAddTime.setText(String.format("%s:%s", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)));
    }

    private void initWineTypeSpinner() {
        if (mWineTypeList == null) {
            mWineTypeList = new ArrayList<>();
        } else {
            mWineTypeList.clear();
        }
        for (int i = 0; i < 4; i++) {
            HashMap<String, String> wine = new HashMap<>();
            switch (i) {
                case 0:
                    wine.put("wine", "白酒");
                    break;
                case 1:
                    wine.put("wine", "红酒");
                    break;
                case 2:
                    wine.put("wine", "啤酒");
                    break;
                case 3:
                    wine.put("wine", "黄酒");
                    break;
            }
            mWineTypeList.add(wine);
        }
        mSpAddWine.setAdapter(new SimpleAdapter(this, mWineTypeList, R.layout.item_spinner_text, new String[]{"wine"}, new int[]{R.id.tv_spinner}));
    }

    @OnClick(R.id.fab_add_wine)
    public void save() {
        mFab.setClickable(false);
        String wineCounts = mEtAddWine.getText().toString();
        if (TextUtils.isEmpty(wineCounts)) {
            showSnackbar(mCoordinator, "请输入饮酒量");
            return;
        }
        KeyboardUtil.hide(this, mContentView.getWindowToken());

        int wineType = mSpAddWine.getSelectedItemPosition();

        String dateStr = mTvAddDate.getText().toString();
        String timeStr = mTvAddTime.getText().toString();

        long date = DateUtil.parseToLong(String.format("%s %s", dateStr, timeStr), DateUtil.DEFAULT_FORMAT_FULL);

        mAddRecordModel.saveWine(AIManager.getInstance(this).getUserId(), date, Integer.parseInt(wineCounts), wineType);
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
        showSnackbar(mCoordinator, "数据保存成功");
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
