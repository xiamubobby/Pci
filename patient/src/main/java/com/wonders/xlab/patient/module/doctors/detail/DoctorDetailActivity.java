package com.wonders.xlab.patient.module.doctors.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorDetailActivityBinding;
import com.wonders.xlab.patient.module.doctors.detail.adapter.DoctorDetailGroupOfDoctorRVAdapter;
import com.wonders.xlab.patient.module.doctors.detail.adapter.DoctorDetailMemberRVAdapter;
import com.wonders.xlab.patient.module.doctors.detail.adapter.DoctorDetailPackageRVAdapter;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.module.doctors.detail.bean.DoctorGroupBasicInfoBean;
import com.wonders.xlab.patient.mvp.presenter.IDoctorDetailPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorDetailPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;

public class DoctorDetailActivity extends BaseActivity implements DoctorDetailPresenter.DoctorDetailPresenterListener {
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_GROUP_ID = "group_id";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private String title;
    private String groupId;

    @Bind(R.id.iv_doctor_detail_portrait)
    ImageView mIvDoctorDetailPortrait;
    @Bind(R.id.recycler_view_doctor_detail_package)
    RecyclerView mRecyclerViewDoctorDetailPackage;
    @Bind(R.id.recycler_view_doctor_detail_member_or_group)
    RecyclerView mRecyclerViewDoctorDetailMemberOrGroup;

    private DoctorDetailPackageRVAdapter mPackageRVAdapter;
    private DoctorDetailMemberRVAdapter mMemberRVAdapter;
    private DoctorDetailGroupOfDoctorRVAdapter mGroupOfDoctorRVAdapter;

    private IDoctorDetailPresenter mDoctorDetailPresenter;

    private DoctorDetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.doctor_detail_activity);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        if (null == data) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        title = data.getString(EXTRA_TITLE);
        groupId = data.getString(EXTRA_GROUP_ID);

        mToolbar.setTitle(title);

        mRecyclerViewDoctorDetailPackage.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewDoctorDetailMemberOrGroup.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mDoctorDetailPresenter = new DoctorDetailPresenter(this);
        addPresenter(mDoctorDetailPresenter);

        mDoctorDetailPresenter.fetchDoctorDetailInfo(groupId);
    }

    @Override
    public void showBasicInfo(DoctorGroupBasicInfoBean basicInfoBean) {
        ImageViewManager.setImageViewWithUrl(this, mIvDoctorDetailPortrait, basicInfoBean.groupAvatar.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
        binding.setBean(basicInfoBean);

    }

    @Override
    public void showPackageList(ArrayList<DoctorDetailPackageBean> packageList) {
        if (null == mPackageRVAdapter) {
            mPackageRVAdapter = new DoctorDetailPackageRVAdapter();
            mPackageRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    BottomSheetDialog dialog = new BottomSheetDialog(DoctorDetailActivity.this);
                    View view = LayoutInflater.from(DoctorDetailActivity.this).inflate(R.layout.doctor_detail_bottom_sheet, null);
                    dialog.setContentView(view);
                    dialog.show();

                }
            });
        }
        mPackageRVAdapter.setDatas(packageList);
        mRecyclerViewDoctorDetailPackage.setAdapter(mPackageRVAdapter);
    }

    @Override
    public void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList) {
        if (null == mMemberRVAdapter) {
            mMemberRVAdapter = new DoctorDetailMemberRVAdapter();
        }
        mMemberRVAdapter.setDatas(groupMemberList);
        mRecyclerViewDoctorDetailMemberOrGroup.setAdapter(mMemberRVAdapter);
    }

    @Override
    public void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList) {
        if (null == mGroupOfDoctorRVAdapter) {
            mGroupOfDoctorRVAdapter = new DoctorDetailGroupOfDoctorRVAdapter();
        }
        mGroupOfDoctorRVAdapter.setDatas(groupOfDoctorList);
        mRecyclerViewDoctorDetailMemberOrGroup.setAdapter(mGroupOfDoctorRVAdapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }
}
