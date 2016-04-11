package com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServiceModifyPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupServiceModifyPresenter;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupServiceModifyActivity extends AppbarActivity implements GroupServiceModifyPresenter.GroupServiceModifyPresenterListener {

    public final static int RESULT_CODE_SUCCESS = 0;

    public final static String EXTRA_PACKAGE_ID = "packageId";
    private String mPackageId;

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
        mPackageId = intent.getStringExtra(EXTRA_PACKAGE_ID);
        if (TextUtils.isEmpty(mPackageId)) {
            Log.e("GroupServiceModifyActiv", "请传入packageId");
            showShortToast("获取套餐信息失败，请重试！");
            finish();
            return;
        }

        initCustomValueAlertDialog();

        mPresenter = new GroupServiceModifyPresenter(this);
        addPresenter(mPresenter);
        mPresenter.getServicePackageInfo(mPackageId);
    }

    private void initCustomValueAlertDialog() {
        mDialogEditText = new EditText(this);
        mDialogEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        mDialogEditText.setSingleLine(true);
        mDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mDialogEditText.setMaxLines(1);
        mDialogEditText.setHint("请输入自定义值");
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialogEditText.setLayoutParams(layoutParams);

        mBuilder = new AlertDialog.Builder(this)
                .setView(mDialogEditText)
                .setTitle("自定义")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        HashMap<String, String> tmp = new HashMap<>();
                        tmp.put("value", mDialogEditText.getText().toString() + mPackageInfoBean.getUnit());
                        tmp.put("tag", mDialogEditText.getText().toString());
                        mPackageInfoBean.getDefaultValues().add(0,tmp);

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

    @Override
    public void showServicePackageInfo(PackageInfoBean packageInfoBean) {
        mPackageInfoBean = packageInfoBean;

        mTvUnitTitle.setText(packageInfoBean.getUnitTitle());
        mTvDescTitle.setText(packageInfoBean.getDescTitle());
        mTvDesc.setText(Html.fromHtml(packageInfoBean.getDesc()));

        mSp.setAdapter(new SimpleAdapter(this, packageInfoBean.getDefaultValues(), R.layout.support_simple_spinner_dropdown_item, new String[]{"value"}, new int[]{android.R.id.text1}));
        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) parent.getSelectedItem();
                showShortToast(map.get("tag"));
                if ("-1".equals(map.get("tag"))) {
                    alertDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }
}
