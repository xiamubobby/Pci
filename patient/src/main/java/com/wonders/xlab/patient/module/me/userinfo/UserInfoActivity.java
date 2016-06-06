package com.wonders.xlab.patient.module.me.userinfo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.module.me.address.AddressActivity;
import com.wonders.xlab.patient.module.me.hospital.HospitalActivity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenter;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenterContract;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;

public class UserInfoActivity extends AppbarActivity implements UserInfoPresenterContract.ViewListener {

    private final int REQUEST_CODE = 1;
    private final int REQUEST_CODE_DOCTOR = REQUEST_CODE << 1;
    private final int REQUEST_CODE_NUMBER = REQUEST_CODE << 2;
    private final int REQUEST_CODE_HISTORY = REQUEST_CODE << 3;
    private final int REQUEST_CODE_ADDRESS = REQUEST_CODE << 4;
    private final int REQUEST_CODE_HOSPITAL = REQUEST_CODE << 5;

    @Bind(R.id.textViewHospital)
    TextView mTextViewHospital;
    @Bind(R.id.tv_user_info_doctor)
    TextView mTvUserInfoDoctor;
    @Bind(R.id.tv_user_info_surgery_time)
    TextView mTvUserInfoSurgeryTime;
    @Bind(R.id.tv_user_info_number)
    TextView mTvUserInfoNumber;
    @Bind(R.id.tv_user_info_history)
    TextView mTvUserInfoHistory;
    @Bind(R.id.tv_user_info_address)
    TextView mTvUserInfoAddress;

    private UserInfoPresenter mPresenter;

    private int mHospitalSpSelectedPosition = 0;

    private String addressId;
    private String address;

    @Override
    public int getContentLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter = DaggerUserInfoComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .userInfoModule(new UserInfoModule(this))
                .build()
                .getUserInfoPresenter();
        addPresenter(mPresenter);

        mPresenter.getUserInfo();

    }

    @Override
    public void showUserInfo(UserInfoEntity.RetValuesEntity entity) {
        mTvUserInfoDoctor.setText(entity.getSurgeon());
        String timeStr = "";
        if (0 != entity.getLastOperationDate()) {
            timeStr = DateUtil.format(entity.getLastOperationDate(), "yyyy-MM-dd");
        }
        address = entity.getAddress();
        addressId = entity.getAddressCode();
        mTvUserInfoSurgeryTime.setText(timeStr);
        mTvUserInfoNumber.setText(entity.getBracketNum());
        mTvUserInfoHistory.setText(entity.getCaseHistory());
        mTvUserInfoAddress.setText(address);
        mTextViewHospital.setText(entity.getHospital());
    }

    private Calendar mCalendarSurgeryDate = Calendar.getInstance();

    @OnClick(R.id.textViewHospital)
    public void goToChooseHospital() {
        startActivityForResult(new Intent(this, HospitalActivity.class), REQUEST_CODE_HOSPITAL);
    }

    @OnClick(R.id.tv_user_info_surgery_time)
    public void pickSurgeryTime() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mCalendarSurgeryDate.set(year, monthOfYear, dayOfMonth);
                        mTvUserInfoSurgeryTime.setText(DateUtil.format(mCalendarSurgeryDate.getTimeInMillis(), "yyyy-MM-dd"));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        mDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        mDatePickerDialog.show();
    }

    @OnClick(R.id.tv_user_info_doctor)
    public void editDoctor() {
        goToTextInputActivity(mTvUserInfoDoctor.getText().toString(), "请输入手术医生姓名", "手术医生", REQUEST_CODE_DOCTOR, false);
    }

    @OnClick(R.id.tv_user_info_number)
    public void editNumber() {
        goToTextInputActivity(mTvUserInfoNumber.getText().toString(), "请输入支架个数", "支架个数", REQUEST_CODE_NUMBER, true);
    }

    @OnClick(R.id.tv_user_info_history)
    public void editHistory() {
        goToTextInputActivity(mTvUserInfoHistory.getText().toString(), "请输入病史", "病史", REQUEST_CODE_HISTORY, false);
    }

    @OnClick(R.id.tv_user_info_address)
    public void editAddress() {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra(AddressActivity.ADDRESS_ID, addressId);
        intent.putExtra(AddressActivity.ADDRESS, address);
        Log.i("sss", "......................." + address + "......................." + addressId);
        startActivityForResult(intent, REQUEST_CODE_ADDRESS);
//        goToTextInputActivity(mTvUserInfoAddress.getText().toString(), "请输入家庭住址", "住址", REQUEST_CODE_ADDRESS, false);
    }

    private void goToTextInputActivity(String defaultValue, String hint, String title, int requestCode, boolean isNumber) {
        Intent intent = new Intent(this, TextInputActivity.class);
        intent.putExtra(TextInputActivity.EXTRA_DEFAULT_VALUE, defaultValue);
        intent.putExtra(TextInputActivity.EXTRA_HINT, hint);
        intent.putExtra(TextInputActivity.EXTRA_TITLE, title);
        intent.putExtra(TextInputActivity.EXTRA_INPUT_TYPE_NUMBER, isNumber);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void modifyUserInfoSuccess(String message) {
        showShortToast(message);
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
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
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

                UserInfoBody body = new UserInfoBody();
                body.setAddress(mTvUserInfoAddress.getText().toString());
                body.setLastOperationDate(mCalendarSurgeryDate.getTimeInMillis());
                body.setBracketNum(Integer.parseInt(mTvUserInfoNumber.getText().toString()));
                body.setCaseHistory(mTvUserInfoHistory.getText().toString());
                body.setSurgeon(mTvUserInfoDoctor.getText().toString());
                Object hospitalId = mTextViewHospital.getTag();
                if (hospitalId != null) {
                    body.setHospitalId(String.valueOf(hospitalId));
                }

                mPresenter.modifyUserInfo(body);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        if (requestCode == REQUEST_CODE_HOSPITAL) {
            String hospitalId = data.getStringExtra(HospitalActivity.EXTRA_RESULT_ID);
            String hospitalName = data.getStringExtra(HospitalActivity.EXTRA_RESULT_NAME);
            mTextViewHospital.setText(hospitalName);
            mTextViewHospital.setTag(hospitalId);
        } else {
            String result = data.getStringExtra(TextInputActivity.EXTRA_RESULT);
            switch (requestCode) {
                case REQUEST_CODE_DOCTOR:
                    mTvUserInfoDoctor.setText(result);
                    break;
                case REQUEST_CODE_ADDRESS:
                    mTvUserInfoAddress.setText(result);
                    break;
                case REQUEST_CODE_HISTORY:
                    mTvUserInfoHistory.setText(result);
                    break;
                case REQUEST_CODE_NUMBER:
                    mTvUserInfoNumber.setText(result);
                    break;

            }
        }
    }

}
