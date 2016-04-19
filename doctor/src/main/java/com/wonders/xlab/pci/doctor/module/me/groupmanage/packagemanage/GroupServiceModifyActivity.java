package com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.presenter.IGroupServiceModifyPresenter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.presenter.impl.GroupServiceModifyPresenter;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.DensityUtil;

public class GroupServiceModifyActivity extends AppbarActivity implements GroupServiceModifyPresenter.GroupServiceModifyPresenterListener {

    public final static int RESULT_CODE_SUCCESS = 12345;

    public final static String EXTRA_SERVICE_PACKAGE_ID = "servicePackageId";
    public final static String EXTRA_GROUP_ID = "groupId";
    public final static String EXTRA_PUBLISHED = "published";
    public final static String EXTRA_IS_ADMIN = "isAdmin";

    @Bind(R.id.tv_group_service_modify_unpublish)
    TextView mTvUnpublish;
    @Bind(R.id.tv_group_service_modify_publish)
    TextView mTvPublish;
    @Bind(R.id.ll_group_service_modify_bottom)
    CardView mCardViewBottom;

    private String mServicePackageId;//模板id
    private String mGroupId;
    private boolean mPublished;
    private boolean mIsAdmin;

    private String mDoctorPackageId;

    @Bind(R.id.tv_group_service_modify_unit_title)
    TextView mTvUnitTitle;
    @Bind(R.id.sp_group_service_modify)
    Spinner mSp;
    @Bind(R.id.tv_group_service_modify_desc_title)
    TextView mTvDescTitle;
    @Bind(R.id.tv_group_service_modify_desc)
    TextView mTvDesc;

    private IGroupServiceModifyPresenter mPresenter;

    private AlertDialog.Builder mBuilder;
    private AlertDialog alertDialog;

    private EditText mDialogEditText;
    private PackageInfoBean mPackageInfoBean;

    @Override
    public int getContentLayout() {
        return R.layout.group_service_modify_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null == intent) {
            Log.e("GroupServiceModifyActiv", "请传入packageId");
            showShortToast("获取套餐信息失败，请重试！");
            finish();
            return;
        }
        mServicePackageId = intent.getStringExtra(EXTRA_SERVICE_PACKAGE_ID);
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        mPublished = intent.getBooleanExtra(EXTRA_PUBLISHED, false);
        mIsAdmin = intent.getBooleanExtra(EXTRA_IS_ADMIN, false);

        if (TextUtils.isEmpty(mServicePackageId)) {
            Log.e("GroupServiceModifyActiv", "请传入packageId");
            showShortToast("获取套餐信息失败，请重试！");
            finish();
            return;
        }

        /**
         * 只有管理员才可以进行发布和保存操作
         */
        if (mIsAdmin) {
            if (mPublished) {
                mTvUnpublish.setVisibility(View.VISIBLE);
            } else {
                mTvUnpublish.setVisibility(View.GONE);
            }
            mCardViewBottom.setVisibility(View.VISIBLE);
            mSp.setEnabled(true);
            mSp.setClickable(true);
        } else {
            mSp.setEnabled(false);
            mSp.setClickable(false);
        }


        mPresenter = new GroupServiceModifyPresenter(this);
        addPresenter(mPresenter);
        mPresenter.getServicePackageInfo(mGroupId, mServicePackageId);
    }

    private void initCustomValueAlertDialog() {
        String title = mTvUnitTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            title = "自定义值";
        }

        if (null == alertDialog) {
            mDialogEditText = new EditText(this);
            mDialogEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            mDialogEditText.setSingleLine(true);
            mDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            mDialogEditText.setMaxLines(1);
            mDialogEditText.setHint("请输入" + title);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = DensityUtil.dp2px(this, 10);
            layoutParams.rightMargin = DensityUtil.dp2px(this, 10);
            mDialogEditText.setLayoutParams(layoutParams);

            mBuilder = new AlertDialog.Builder(this)
                    .setView(mDialogEditText)
                    .setTitle(title)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            HashMap<String, String> tmp = new HashMap<>();
                            tmp.put("valueStr", mDialogEditText.getText().toString() + mPackageInfoBean.getUnit());
                            tmp.put("value", mDialogEditText.getText().toString());
                            mPackageInfoBean.getDefaultValues().add(0, tmp);

                            SimpleAdapter adapter = (SimpleAdapter) mSp.getAdapter();
                            adapter.notifyDataSetChanged();

                            mDialogEditText.setText("");
                            mSp.setSelection(0);
                        }
                    }).setNegativeButton("取消", null);
            alertDialog = mBuilder.create();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDialogEditText.setText("");
                    mSp.setSelection(0);
                }
            });
        }

        mDialogEditText.setHint("请输入" + title);
        alertDialog.setTitle(title);

    }

    private List<HashMap<String, String>> defaultSpValues;

    @Override
    public void showServicePackageInfo(PackageInfoBean packageInfoBean) {
        mPackageInfoBean = packageInfoBean;

        mTvUnitTitle.setText(packageInfoBean.getUnitTitle());
        mTvDescTitle.setText(packageInfoBean.getDescTitle());
        mTvDesc.setText(Html.fromHtml(packageInfoBean.getDesc()));
        mDoctorPackageId = packageInfoBean.getDoctorPackageId();

        defaultSpValues = packageInfoBean.getDefaultValues();
        mSp.setAdapter(new SimpleAdapter(this, defaultSpValues, R.layout.support_simple_spinner_dropdown_item, new String[]{"valueStr"}, new int[]{android.R.id.text1}));
        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = defaultSpValues.get(position);
                if ("-1".equals(map.get("value"))) {
                    //自定义
                    alertDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp.setSelection(packageInfoBean.getDefaultSpPosition());

        initCustomValueAlertDialog();
    }

    @OnClick(R.id.tv_group_service_modify_publish)
    public void publish() {
        update(true);
    }

    @OnClick(R.id.tv_group_service_modify_unpublish)
    public void unPublish() {
        update(false);
    }

    private void update(boolean isPublish) {
        String value = defaultSpValues.get(mSp.getSelectedItemPosition()).get("value");
        if (!TextUtils.isDigitsOnly(value)) {
            showShortToast("请选择一个值");
            return;
        }
        GroupPackagePublishBody body = new GroupPackagePublishBody();
        body.setDoctorId(AIManager.getInstance().getDoctorId());
        body.setDoctorGroupId(mGroupId);
        body.setDoctorPackageId(mDoctorPackageId);
        body.setPublishType(isPublish ? "Publish" : "Unpublish");
        body.setServicePackageId(mServicePackageId);
        if (isPublish) {
            body.setNumberValue(Integer.parseInt(value));
        }
        mPresenter.publishPackage(body);
    }

    @Override
    public void publishSuccess(String message) {
        showShortToast(message);
        setResult(RESULT_CODE_SUCCESS);
        finish();
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
