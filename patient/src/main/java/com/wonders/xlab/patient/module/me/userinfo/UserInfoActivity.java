package com.wonders.xlab.patient.module.me.userinfo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.module.auth.authorize.crop.CropActivity2;
import com.wonders.xlab.patient.module.me.address.AddressActivity;
import com.wonders.xlab.patient.module.me.hospital.HospitalActivity;
import com.wonders.xlab.patient.module.me.otto.UserIconUpdateOtto;
import com.wonders.xlab.patient.module.me.otto.UserInfoUpdateOtto;
import com.wonders.xlab.patient.module.me.sex.SexActivity;
import com.wonders.xlab.patient.module.me.userinfo.di.DaggerUserInfoComponent;
import com.wonders.xlab.patient.module.me.userinfo.di.UserInfoModule;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;
import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.patient.util.MyAddressUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DateUtil;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class UserInfoActivity extends AppbarActivity implements UserInfoContract.ViewListener {

    private final int REQUEST_CODE = 1;

    private final int REQUEST_CODE_DOCTOR = REQUEST_CODE << 1;
    private final int REQUEST_CODE_NUMBER = REQUEST_CODE << 2;
    private final int REQUEST_CODE_HISTORY = REQUEST_CODE << 3;
    private final int REQUEST_CODE_ADDRESS = REQUEST_CODE << 4;
    private final int REQUEST_CODE_HOSPITAL = REQUEST_CODE << 5;
    private final int REQUEST_CODE_PIC = REQUEST_CODE << 6;
    private final int REQUEST_CROP_IMAGE = REQUEST_CODE << 7;
    private final int REQUEST_CODE_SEX = REQUEST_CODE << 8;
    private final int REQUEST_CODE_AGE = REQUEST_CODE << 9;
    private final int REQUEST_CODE_NAME = REQUEST_CODE << 10;

    @Bind(R.id.iv_user_avatar)
    ImageView mIvPortrait;
    @Bind(R.id.tv_user_info_name)
    TextView mTextViewName;
    @Bind(R.id.tv_user_info_sex)
    TextView mTextViewSex;
    @Bind(R.id.tv_user_info_age)
    TextView mTextViewAge;
    @Bind(R.id.tv_user_info_hospital)
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

    private String imageFilePath;
    private String sexId;
    private String sex;
    private String age;
    private String hospitalId;
    private String addressId;
    private String address;
    private MyAddressUtil addressUtil;
    /**
     * 保存选择的图片
     */
    private File mPickedIdPicFile;
    private Uri handlingImageUri;
    private String imageFileUrl;

    @Override
    public int getContentLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OttoManager.register(this);
        ButterKnife.bind(this);
        mPresenter = DaggerUserInfoComponent.builder()
                .applicationComponent(XApplication.getComponent())
                .userInfoModule(new UserInfoModule(this))
                .build()
                .getUserInfoPresenter();
        addPresenter(mPresenter);
        addressUtil = new MyAddressUtil(this);
        mPresenter.getUserInfo();

    }

    @Override
    public void showUserInfo(UserInfoEntity.RetValuesEntity entity) {

        mTvUserInfoDoctor.setText(entity.getSurgeon());
        String timeStr = "";
        if (0 != entity.getLastOperationDate()) {
            timeStr = DateUtil.format(entity.getLastOperationDate(), "yyyy-MM-dd");
        }
        ImageViewManager.setImageViewWithUrl(this, mIvPortrait, AIManager.getInstance().getPatientPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
        if (!TextUtils.isEmpty(entity.getName())) {
            mTextViewName.setText(entity.getName());
        } else {
            mTextViewName.setText(AIManager.getInstance().getPatientName());
        }

        sex = entity.getSex();
        if (sex.equals("男")) {
            sexId = "Male";
        } else {
            sexId = "Female";
        }

        hospitalId = entity.getHospitalId();
        mTextViewSex.setText(sex);
        age = entity.getAge();
        mTextViewAge.setText(age);
        address = entity.getAddress();
        addressId = entity.getAddressCode();
        mTvUserInfoSurgeryTime.setText(timeStr);
        mTvUserInfoNumber.setText(entity.getBracketNum());
        mTvUserInfoHistory.setText(entity.getCaseHistory());
        mTvUserInfoAddress.setText(addressUtil.getFrontAddress(addressId) + address);
        mTextViewHospital.setText(entity.getHospital());
        AIManager.getInstance().modifyPatientInfo(null, null, null, null, sex, age);
    }

    private Calendar mCalendarSurgeryDate = Calendar.getInstance();

    @OnClick(R.id.iv_user_avatar)
    public void choosePic() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        startActivityForResult(intent, REQUEST_CODE_PIC);
    }

    @OnClick(R.id.tv_user_info_name)
    public void editName() {
        goToTextInputActivity(mTvUserInfoDoctor.getText().toString(), "请输入手术医生姓名", "手术医生", REQUEST_CODE_NAME, false);
    }

    @OnClick(R.id.tv_user_info_sex)
    public void goToChooseSex() {
        startActivityForResult(new Intent(this, SexActivity.class), REQUEST_CODE_SEX);
    }

    @OnClick(R.id.tv_user_info_age)
    public void editAge() {
        goToTextInputActivity(mTextViewAge.getText().toString(), "请输入年龄", "年龄", REQUEST_CODE_AGE, true);
    }

    @OnClick(R.id.tv_user_info_hospital)
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
        OttoManager.post(new UserInfoUpdateOtto(sex, age));
        showShortToast(message);
    }

    @Override
    public void uploadUserAvaterSuccess(String url) {
        imageFileUrl = url;
        mPresenter.modifyUserAvater(url);
    }

    @Override
    public void modifyUserAvaterSuccess(String message) {
        OttoManager.post(new UserIconUpdateOtto(imageFilePath));
        AIManager.getInstance().modifyPatientAvatar(imageFileUrl);
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
                body.setName(mTextViewName.getText().toString());
                body.setSex(sexId);
                body.setAge(Integer.parseInt(mTextViewAge.getText().toString()));
                body.setAddress(mTvUserInfoAddress.getText().toString());
                body.setLastOperationDate(mCalendarSurgeryDate.getTimeInMillis());
                body.setBracketNum(Integer.parseInt(mTvUserInfoNumber.getText().toString()));
                body.setCaseHistory(mTvUserInfoHistory.getText().toString());
                body.setSurgeon(mTvUserInfoDoctor.getText().toString());
                Object tempId = mTextViewHospital.getTag();
                if (tempId != null) {
                    hospitalId = String.valueOf(tempId);
                }
                body.setHospitalId(hospitalId);
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
        } else if (requestCode == REQUEST_CODE_ADDRESS) {
            String addressResult = data.getStringExtra(AddressActivity.ADDRESS_RESULT);
            mTvUserInfoAddress.setText(addressResult);
            address = data.getStringExtra(AddressActivity.ADDRESS);
            addressId = data.getStringExtra(AddressActivity.ADDRESS_ID);
        } else if (requestCode == REQUEST_CODE_PIC) {
            ArrayList<String> photos =
                    data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            if (null == photos || photos.size() <= 0) {
                return;
            }

            String path = photos.get(0);

//            Intent intent = new Intent(this, CropActivity.class);
//            intent.putExtra(CropActivity.EXTRA_IMAGE_PATH, path);
//            startActivityForResult(intent, REQUEST_CROP_IMAGE);
            final Intent editIntent = new Intent(Intent.ACTION_EDIT);
            final File photo = new File(photos.get(0));
            handlingImageUri = Uri.fromFile(photo);
            editIntent.setDataAndType(handlingImageUri, "image/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(editIntent, "请选择裁剪软件"), REQUEST_CROP_IMAGE);
        } else if (requestCode == REQUEST_CROP_IMAGE) {
//            imageFilePath = data.getStringExtra(CropActivity.RESULT_IMAGE_PATH);
//            mPickedIdPicFile = new File(imageFilePath);
//            Uri uri = Uri.fromFile(mPickedIdPicFile);
            Uri dataUri = data.getData();
            ImageViewManager.setImageViewWithUri(this, mIvPortrait, dataUri, ImageViewManager.PLACE_HOLDER_EMPTY);
            imageFilePath = dataUri.getPath();
            mPickedIdPicFile = new File(dataUri.getPath());
            mPresenter.uploadAvater(mPickedIdPicFile);
        } else if (requestCode == REQUEST_CODE_SEX) {
            sexId = data.getStringExtra(SexActivity.EXTRA_SEX_ID);
            sex = data.getStringExtra(SexActivity.EXTRA_SEX_NAME);
            mTextViewSex.setText(sex);

        } else {
            String result = data.getStringExtra(TextInputActivity.EXTRA_RESULT);
            switch (requestCode) {
                case REQUEST_CODE_AGE:
                    age = result;
                    mTextViewAge.setText(result);
                    break;
                case REQUEST_CODE_DOCTOR:
                    mTvUserInfoDoctor.setText(result);
                    break;
                case REQUEST_CODE_HISTORY:
                    mTvUserInfoHistory.setText(result);
                    break;
                case REQUEST_CODE_NUMBER:
                    mTvUserInfoNumber.setText(result);
                    break;
                case REQUEST_CODE_NAME:
                    mTextViewName.setText(result);
                    break;

            }
        }
    }
}

