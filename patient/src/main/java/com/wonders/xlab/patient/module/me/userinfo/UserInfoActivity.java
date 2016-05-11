package com.wonders.xlab.patient.module.me.userinfo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenter;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenterContract;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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

    @Bind(R.id.sp_user_info_hospital)
    Spinner mSpUserInfoHospital;
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

    @Override
    public int getContentLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mSpUserInfoHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHospitalSpSelectedPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPresenter = DaggerUserInfoComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .userInfoModule(new UserInfoModule(this))
                .build()
                .getUserInfoPresenter();

        mPresenter.getAllHospitals();

        addPresenter(mPresenter);
    }

    @Override
    public void showUserInfo(UserInfoEntity.RetValuesEntity entity) {
        mTvUserInfoDoctor.setText(entity.getSurgeon());
        String timeStr = "";
        if (0 != entity.getLastOperationDate()) {
            timeStr = DateUtil.format(entity.getLastOperationDate(), "yyyy-MM-dd");
        }
        mTvUserInfoSurgeryTime.setText(timeStr);
        mTvUserInfoNumber.setText(entity.getBracketNum());
        mTvUserInfoHistory.setText(entity.getCaseHistory());
        mTvUserInfoAddress.setText(entity.getAddress());

        if (null != mDicEntityList) {
            for (int i = 0; i < mDicEntityList.size(); i++) {
                if (entity.getHospital().equals(mDicEntityList.get(i).get("name"))) {
                    mSpUserInfoHospital.setSelection(i);
                    break;
                }
            }
        }
    }

    private Calendar mCalendarSurgeryDate = Calendar.getInstance();

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
        goToTextInputActivity(mTvUserInfoAddress.getText().toString(), "请输入家庭住址", "住址", REQUEST_CODE_ADDRESS, false);
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

    /**
     * 医院字典表数据
     */
    private List<HashMap<String, String>> mDicEntityList;

    @Override
    public void showHospitalList(List<HashMap<String, String>> dicEntityList) {
        mDicEntityList = dicEntityList;
        mSpUserInfoHospital.setAdapter(new SimpleAdapter(this, dicEntityList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));

        mPresenter.getUserInfo();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
    }

    @Override
    public void showNetworkError(String message) {

    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (null == mDicEntityList || mDicEntityList.size() <= mHospitalSpSelectedPosition) {
                    showShortToast("请等待获取医院列表");
                    break;
                }
                UserInfoBody body = new UserInfoBody();
                body.setAddress(mTvUserInfoAddress.getText().toString());
                body.setLastOperationDate(mCalendarSurgeryDate.getTimeInMillis());
                body.setBracketNum(Integer.parseInt(mTvUserInfoNumber.getText().toString()));
                body.setCaseHistory(mTvUserInfoHistory.getText().toString());
                body.setSurgeon(mTvUserInfoDoctor.getText().toString());
                body.setHospitalId(mDicEntityList.get(mHospitalSpSelectedPosition).get("id"));

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
