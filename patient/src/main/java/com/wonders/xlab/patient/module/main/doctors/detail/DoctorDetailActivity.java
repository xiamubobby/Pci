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

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.common.manager.OttoManager;
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
import com.wonders.xlab.patient.otto.BuyPackageSuccessOtto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;

/**
 *医生详情
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
    private String doctorOrOwnerId;
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
    @Bind(R.id.tv_doctor_detail_title_members_or_group_of_doctor)
    TextView mTvTitleMembersOrGroupOfDoctor;

    private DoctorDetailPackageRVAdapter mPackageRVAdapter;
    private DoctorDetailMemberRVAdapter mMemberRVAdapter;
    private DoctorDetailGroupOfDoctorRVAdapter mGroupOfDoctorRVAdapter;

    private IDoctorGroupDetailPresenter mDoctorGroupDetailPresenter;
    private IDoctorDetailPresenter mDoctorDetailPresenter;

    private DoctorDetailActivityBinding binding;

    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        if (null == data) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        title = data.getString(EXTRA_TITLE);
        type = data.getInt(EXTRA_TYPE, TYPE_DOCTOR_GROUP);
        doctorOrOwnerId = data.getString(EXTRA_ID);

        if (TextUtils.isEmpty(doctorOrOwnerId)) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.doctor_detail_activity);
        //we'd better initial the bean here
//        binding.setBean(new DoctorBasicInfoBean());
        ButterKnife.bind(this);

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
                mDoctorDetailPresenter.fetchDoctorDetailInfo(AIManager.getInstance().getPatientId(), doctorOrOwnerId);
                break;
            case TYPE_DOCTOR_GROUP:
                mDoctorGroupDetailPresenter.fetchDoctorGroupDetailInfo(AIManager.getInstance().getPatientId(), doctorOrOwnerId);
                break;
        }
    }

    @Override
    public void showBasicInfo(DoctorBasicInfoBean basicInfoBean) {
        ImageViewManager.setImageViewWithUrl(this, mIvDoctorDetailPortrait, basicInfoBean.groupAvatar.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
        binding.setBean(basicInfoBean);

    }

    /**
     * 点击套餐显示BottomSheet
     * @param packageList
     */
    @Override
    public void showPackageList(final ArrayList<DoctorDetailPackageBean> packageList) {
        if (null == mPackageRVAdapter) {
            mPackageRVAdapter = new DoctorDetailPackageRVAdapter();
            mPackageRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
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

                    final int status = mPackageRVAdapter.getBean(position).orderStatus.get();
                    switch (status) {
                        case DoctorDetailPackageBean.STATUS_IN_SERVICE:
                            btnBuy.setText("已购买");
                            btnBuy.setBackgroundColor(getResources().getColor(R.color.text_color_secondary_gray));
                            break;
                        case DoctorDetailPackageBean.STATUS_OUT_SERVICE:
                            btnBuy.setText("再次购买");
                            break;
                        case DoctorDetailPackageBean.STATUS_NEVER_BUY:
                            btnBuy.setText("立即购买");
                            break;
                    }
                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (status == DoctorDetailPackageBean.STATUS_IN_SERVICE) {
                                showShortToast("您已购买本套餐");
                            } else {
                                showProgressDialog("","正在购买，请稍候...",null);
                                switch (type) {
                                    case TYPE_DOCTOR:
                                        mDoctorDetailPresenter.orderPackage(AIManager.getInstance().getPatientId(), packageList.get(position).packageId.get());
                                        break;
                                    case TYPE_DOCTOR_GROUP:
                                        mDoctorGroupDetailPresenter.orderPackage(AIManager.getInstance().getPatientId(), packageList.get(position).packageId.get());
                                        break;
                                }
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
        if (groupMemberList.size() > 0) {
            mRecyclerViewDoctorDetailMemberOrGroup.setVisibility(View.VISIBLE);
        } else {
            mRecyclerViewDoctorDetailMemberOrGroup.setVisibility(View.GONE);
            return;
        }
        if (null == mMemberRVAdapter) {
            mMemberRVAdapter = new DoctorDetailMemberRVAdapter();
            mMemberRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
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
            mGroupOfDoctorRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(DoctorDetailActivity.this, DoctorDetailActivity.class);
                    intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, mGroupOfDoctorRVAdapter.getBean(position).name.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_ID, mGroupOfDoctorRVAdapter.getBean(position).ownerId.get());
                    intent.putExtra(DoctorDetailActivity.EXTRA_TYPE, DoctorDetailActivity.TYPE_DOCTOR_GROUP);
                    startActivity(intent);
                }
            });
        }
        mGroupOfDoctorRVAdapter.setDatas(groupOfDoctorList);
        mRecyclerViewDoctorDetailMemberOrGroup.setAdapter(mGroupOfDoctorRVAdapter);
    }

    @Override
    public void showMemberOrGroupOfDoctorRV() {
        if (null != mTvTitleMembersOrGroupOfDoctor) {
            mTvTitleMembersOrGroupOfDoctor.setVisibility(View.VISIBLE);
        }
        if (null != mRecyclerViewDoctorDetailMemberOrGroup) {
            mRecyclerViewDoctorDetailMemberOrGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideMemberOrGroupOfDoctorRV() {
        if (null != mTvTitleMembersOrGroupOfDoctor) {
            mTvTitleMembersOrGroupOfDoctor.setVisibility(View.GONE);
        }
        if (null != mRecyclerViewDoctorDetailMemberOrGroup) {
            mRecyclerViewDoctorDetailMemberOrGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void orderPackageSuccess(String message) {
        showShortToast(message);
        dismissProgressDialog();
        requestData();
        dialog.dismiss();
        OttoManager.post(new BuyPackageSuccessOtto());
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_doctor_detail));
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_doctor_detail));
        MobclickAgent.onPause(this);
    }
}
