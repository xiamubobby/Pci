package com.wonders.xlab.patient.module.main.doctors.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.databinding.DoctorDetailActivityBinding;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.DoctorDetailGroupOfDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.DoctorDetailMemberRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.DoctorDetailPackageRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.module.main.doctors.detail.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.mvp.presenter.IDoctorDetailPresenter;
import com.wonders.xlab.patient.mvp.presenter.IDoctorGroupDetailPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorDetailPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorGroupDetailPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;

/**
 *
 */
public class DoctorDetailActivity extends BaseActivity implements DoctorGroupDetailPresenter.DoctorGroupDetailPresenterListener, DoctorDetailPresenter.DoctorDetailPresenterListener {
    public final static int TYPE_DOCTOR = 0;
    public final static int TYPE_DOCTOR_GROUP = 1;

    public final static String EXTRA_TITLE = "title";

    /**
     * DoctorDetailActivity.TYPE_DOCTOR
     * DoctorDetailActivity.TYPE_DOCTOR_GROUP
     */
    public final static String EXTRA_TYPE = "type";

    /**
     * 可能是小组id或者医生个人的id
     */
    public final static String EXTRA_ID = "extraId";

    private String title;
    private String doctorId;
    private int type;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.refresh_doctor_detail)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.iv_doctor_detail_portrait)
    ImageView mIvDoctorDetailPortrait;
    @Bind(R.id.recycler_view_doctor_detail_package)
    RecyclerView mRecyclerViewDoctorDetailPackage;
    @Bind(R.id.recycler_view_doctor_detail_member_or_group)
    RecyclerView mRecyclerViewDoctorDetailMemberOrGroup;

    private DoctorDetailPackageRVAdapter mPackageRVAdapter;
    private DoctorDetailMemberRVAdapter mMemberRVAdapter;
    private DoctorDetailGroupOfDoctorRVAdapter mGroupOfDoctorRVAdapter;

    private IDoctorGroupDetailPresenter mDoctorGroupDetailPresenter;
    private IDoctorDetailPresenter mDoctorDetailPresenter;

    private DoctorDetailActivityBinding binding;

    private BottomSheetDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.doctor_detail_activity);
        //we'd better initial the bean here
//        binding.setBean(new DoctorBasicInfoBean());
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        if (null == data) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        title = data.getString(EXTRA_TITLE);
        type = data.getInt(EXTRA_TYPE, TYPE_DOCTOR_GROUP);
        doctorId = data.getString(EXTRA_ID);

        if (TextUtils.isEmpty(doctorId)) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setTitle(title);

        mRecyclerViewDoctorDetailPackage.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewDoctorDetailPackage.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewDoctorDetailMemberOrGroup.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewDoctorDetailMemberOrGroup.setItemAnimator(new DefaultItemAnimator());

        switch (type) {
            case TYPE_DOCTOR:
                mDoctorDetailPresenter = new DoctorDetailPresenter(this);
                addPresenter(mDoctorDetailPresenter);
                break;
            case TYPE_DOCTOR_GROUP:
                mDoctorGroupDetailPresenter = new DoctorGroupDetailPresenter(this);
                addPresenter(mDoctorGroupDetailPresenter);
                break;
        }

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
            }
        });
        requestData();
    }

    private void requestData() {
        switch (type) {
            case TYPE_DOCTOR:
                mDoctorDetailPresenter.fetchDoctorDetailInfo(doctorId);
                break;
            case TYPE_DOCTOR_GROUP:
                mDoctorGroupDetailPresenter.fetchDoctorGroupDetailInfo(doctorId);
                break;
        }
    }

    @Override
    public void showBasicInfo(DoctorBasicInfoBean basicInfoBean) {
        ImageViewManager.setImageViewWithUrl(this, mIvDoctorDetailPortrait, basicInfoBean.groupAvatar.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
        binding.setBean(basicInfoBean);

    }

    @Override
    public void showPackageList(final ArrayList<DoctorDetailPackageBean> packageList) {
        if (null == mPackageRVAdapter) {
            mPackageRVAdapter = new DoctorDetailPackageRVAdapter();
            mPackageRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                    if (null == dialog) {
                        dialog = new BottomSheetDialog(DoctorDetailActivity.this);
                    }
                    View view = LayoutInflater.from(DoctorDetailActivity.this).inflate(R.layout.doctor_detail_bottom_sheet, null, false);
                    TextView name = (TextView) view.findViewById(R.id.tv_doctor_detail_bottom_sheet_package_name);
                    TextView price = (TextView) view.findViewById(R.id.tv_doctor_detail_bottom_sheet_price);
                    TextView desc = (TextView) view.findViewById(R.id.tv_doctor_detail_bottom_sheet_desc);
                    Button btnBuy = (Button) view.findViewById(R.id.btn_doctor_detail_bottom_sheet);
                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (type) {
                                case TYPE_DOCTOR:
                                    mDoctorDetailPresenter.orderPackage(AIManager.getInstance(DoctorDetailActivity.this).getPatientId(),packageList.get(position).packageId.get());
                                    break;
                                case TYPE_DOCTOR_GROUP:
                                    mDoctorGroupDetailPresenter.orderPackage(AIManager.getInstance(DoctorDetailActivity.this).getPatientId(),packageList.get(position).packageId.get());
                                    break;
                            }
                        }
                    });

                    DoctorDetailPackageBean bean = mPackageRVAdapter.getBean(position);
                    name.setText(bean.name.get());
                    price.setText(bean.priceStr.get());
                    desc.setText(bean.description.get());

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
            mMemberRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(DoctorDetailActivity.this, DoctorDetailActivity.class);
                    intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, mMemberRVAdapter.getBean(position).name.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_ID, mMemberRVAdapter.getBean(position).doctorId.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_TYPE, DoctorDetailActivity.TYPE_DOCTOR);
                    startActivity(intent);
                }
            });
        }
        mMemberRVAdapter.setDatas(groupMemberList);
        mRecyclerViewDoctorDetailMemberOrGroup.setAdapter(mMemberRVAdapter);
    }

    @Override
    public void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList) {
        if (null == mGroupOfDoctorRVAdapter) {
            mGroupOfDoctorRVAdapter = new DoctorDetailGroupOfDoctorRVAdapter();
            mGroupOfDoctorRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(DoctorDetailActivity.this, DoctorDetailActivity.class);
                    intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, mGroupOfDoctorRVAdapter.getBean(position).name.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_ID, mGroupOfDoctorRVAdapter.getBean(position).groupId.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_TYPE, DoctorDetailActivity.TYPE_DOCTOR_GROUP);
                    startActivity(intent);
                }
            });
        }
        mGroupOfDoctorRVAdapter.setDatas(groupOfDoctorList);
        mRecyclerViewDoctorDetailMemberOrGroup.setAdapter(mGroupOfDoctorRVAdapter);
    }

    @Override
    public void orderPackageSuccess(String message) {
        showShortToast(message);
        dialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(false);
            }
        });
    }
}
