package com.wonders.xlab.patient.module.medicineremind.edit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.MedicineSearchActivity;
import com.wonders.xlab.patient.mvp.entity.MedicationUsagesEntity;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindEditPresenterContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MedicineRemindEditActivity extends AppbarActivity implements MedicineRemindEditPresenterContract.ViewListener {

    public static final String EXTRA_MEDICINE_REMIND_ID = "medicineRemindId";
    private String mMedicineRemindId;

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
        OttoManager.register(this);

        Intent intent = getIntent();
        if (null != intent) {
            mMedicineRemindId = intent.getStringExtra(EXTRA_MEDICINE_REMIND_ID);
            if (TextUtils.isEmpty(mMedicineRemindId)) {
                setToolbarTitle("添加提醒");
            } else {
                setToolbarTitle("编辑提醒");
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTvStartDate.setText(DateUtil.format(mStartCalendar.getTimeInMillis(), "yyyy-MM-dd"));

        mPresenter = DaggerMedicineRemindEditComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicineRemindEditModule(new MedicineRemindEditModule(this))
                .build()
                .getMedicineRemindEditPresenter();
        addPresenter(mPresenter);

        /**
         * 编辑的时候才获取详细信息
         */
        if (!TextUtils.isEmpty(mMedicineRemindId)) {
            mPresenter.getMedicineRemindInfoById(mMedicineRemindId);
        }
    }

    @Subscribe
    public void receiveMedicineBean(MedicineRealmBean bean) {
        initMedicineListAdapter();
        mRVAdapter.appendToLast(bean);
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
        startActivityForResult(intent, REQUEST_CODE_MESSAGE);
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
                if (null == mRVAdapter || mRVAdapter.getBeanList().size() == 0) {
                    showShortToast("请添加药品");
                    break;
                }
                final MedicineRemindEditBody body = new MedicineRemindEditBody();
                body.setId(mMedicineRemindId);
                body.setStartDate(mStartCalendar.getTimeInMillis());
                /**
                 * 是否长期
                 */
                if (mTvEndDate.getText().toString().equals(getResources().getString(R.string.medicine_remind_edit_end_date_default))) {
                    body.setEndDate(0L);
                } else {
                    body.setEndDate(mEndCalendar.getTimeInMillis());
                }
                //TODO 到底是什么格式了？
                String timeStr = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                body.setRemindersTime(timeStr);
                body.setRemindersDesc(mTvMessage.getText().toString());
                Observable.from(mRVAdapter.getBeanList())
                        .flatMap(new Func1<MedicineRealmBean, Observable<MedicationUsagesEntity>>() {
                            @Override
                            public Observable<MedicationUsagesEntity> call(MedicineRealmBean medicineRealmBean) {
                                MedicationUsagesEntity entity = new MedicationUsagesEntity();
                                entity.setMedicationName(medicineRealmBean.getMedicineName());
                                entity.setMedicationNum(medicineRealmBean.getDose());
                                entity.setPharmaceuticalUnit(medicineRealmBean.getFormOfDrug());
                                return Observable.just(entity);
                            }
                        })
                        .subscribe(new Subscriber<MedicationUsagesEntity>() {
                            List<MedicationUsagesEntity> mUsagesEntityList = new ArrayList<>();

                            @Override
                            public void onCompleted() {
                                body.setMedicationUsages(mUsagesEntityList);
                                mPresenter.addOrModify(body);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(MedicationUsagesEntity medicationUsagesEntity) {
                                mUsagesEntityList.add(medicationUsagesEntity);
                            }
                        });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMedicineRemindInfo(int hour, int minutes, long startDate, long endDate, String message, List<MedicineRealmBean> beanList) {

        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minutes);

        mStartCalendar.setTimeInMillis(startDate);
        mTvStartDate.setText(DateUtil.format(mStartCalendar.getTimeInMillis(), "yyyy-MM-dd"));
        if (0 != endDate) {
            mEndCalendar.setTimeInMillis(endDate);
            mTvEndDate.setText(DateUtil.format(mEndCalendar.getTimeInMillis(), "yyyy-MM-dd"));
        }

        mTvMessage.setText(message);
        initMedicineListAdapter();
        mRVAdapter.setDatas(beanList);
    }

    private void initMedicineListAdapter() {
        if (null == mRVAdapter) {
            mRVAdapter = new MedicineRemindEditRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void saveSuccess(String message) {
        OttoManager.post(new SaveRemindSuccessOtto());
        showShortToast(message);
        finish();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
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
        dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
