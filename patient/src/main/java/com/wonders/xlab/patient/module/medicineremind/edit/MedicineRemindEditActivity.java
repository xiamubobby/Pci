package com.wonders.xlab.patient.module.medicineremind.edit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditBean;
import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.MedicineSearchActivity;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindEditPresenterContract;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;

public class MedicineRemindEditActivity extends AppbarActivity implements MedicineRemindEditPresenterContract.ViewListener {

    private final int REQUEST_CODE_MESSAGE = 1234;

    @Bind(R.id.timePicker)
    TimePicker mTimePicker;
    @Bind(R.id.tv_medicine_remind_edit_start_date)
    TextView mTvStartDate;
    @Bind(R.id.tv_medicine_remind_edit_end_date)
    TextView mTvEndDate;
    @Bind(R.id.tv_medicine_remind_edit_message)
    TextView mTvMessage;
    @Bind(R.id.recycler_view_medicine_remind_edit)
    RecyclerView mRecyclerView;

    private Calendar mStartCalendar = Calendar.getInstance();
    private Calendar mEndCalendar = Calendar.getInstance();

    private MedicineRemindEditRVAdapter mRVAdapter;

    private MedicineRemindEditPresenterContract.Actions mPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_remind_edit_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTvStartDate.setText(DateUtil.format(mStartCalendar.getTimeInMillis(), "yyyy-MM-dd"));

        mPresenter = DaggerMedicineRemindEditComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicineRemindEditModule(new MedicineRemindEditModule(this))
                .build()
                .getMedicineRemindEditPresenter();
        addPresenter(mPresenter);

        mPresenter.getMedicineRemindInfoById("1");
    }

    @OnClick(R.id.ll_medicine_remind_edit_start_date)
    public void showStartDate() {

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mStartCalendar.set(year, monthOfYear, dayOfMonth);
                        mTvStartDate.setText(DateUtil.format(mStartCalendar.getTimeInMillis(), "yyyy-MM-dd"));
                    }
                },
                mStartCalendar.get(Calendar.YEAR),
                mStartCalendar.get(Calendar.MONTH),
                mStartCalendar.get(Calendar.DAY_OF_MONTH)
        );
        mDatePickerDialog.getDatePicker().setMaxDate(Math.min(mEndCalendar.getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
        mDatePickerDialog.show();
    }

    @OnClick(R.id.ll_medicine_remind_edit_end_date)
    public void showEndDate() {
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEndCalendar.set(year, monthOfYear, dayOfMonth);
                        mTvEndDate.setText(DateUtil.format(mEndCalendar.getTimeInMillis(), "yyyy-MM-dd"));
                    }
                },
                mEndCalendar.get(Calendar.YEAR),
                mEndCalendar.get(Calendar.MONTH),
                mEndCalendar.get(Calendar.DAY_OF_MONTH)
        );
        mDatePickerDialog.getDatePicker().setMinDate(mStartCalendar.getTimeInMillis());
        mDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        mDatePickerDialog.show();
    }

    @OnClick(R.id.tv_medicine_remind_edit_add_medicine)
    public void addMedicine() {
        startActivity(new Intent(this, MedicineSearchActivity.class));
    }

    @OnClick(R.id.ll_medicine_remind_edit_message)
    public void setMessage() {
        Intent intent = new Intent(this, TextInputActivity.class);
        intent.putExtra(TextInputActivity.EXTRA_TITLE, "提醒语");
        intent.putExtra(TextInputActivity.EXTRA_HINT, "请输入提醒语");
        intent.putExtra(TextInputActivity.EXTRA_DEFAULT_VALUE, mTvMessage.getText().toString());
        startActivityForResult(intent,REQUEST_CODE_MESSAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data || resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_MESSAGE:
                mTvMessage.setText(data.getStringExtra(TextInputActivity.EXTRA_RESULT));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMedicineRemindInfo(int hour, int minutes, long startDate, Long endDate, String message, List<MedicineRemindEditBean> beanList) {

        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minutes);

        mStartCalendar.setTimeInMillis(startDate);
        mTvStartDate.setText(DateUtil.format(mStartCalendar.getTimeInMillis(), "yyyy-MM-dd"));
        if (null != endDate) {
            mEndCalendar.setTimeInMillis(endDate);
            mTvEndDate.setText(DateUtil.format(mEndCalendar.getTimeInMillis(), "yyyy-MM-dd"));
        }

        mTvMessage.setText(message);
        if (null == mRVAdapter) {
            mRVAdapter = new MedicineRemindEditRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(beanList);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView(String message) {
        showShortToast(message);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }
}
