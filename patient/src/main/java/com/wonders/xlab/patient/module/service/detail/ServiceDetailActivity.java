package com.wonders.xlab.patient.module.service.detail;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.databinding.ServiceDetailActivityBinding;
import com.wonders.xlab.patient.mvp.presenter.ServiceDetailPresenterContract;
import com.wonders.xlab.patient.util.ImageViewManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailActivity extends AppbarActivity implements ServiceDetailPresenterContract.ViewListener {

    public final static String _key_SERVICE_ID_ = "serviceId";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager_service_detail)
    ViewPager mBannerPager;
    @Bind(R.id.tab)
    CommonTabLayout mTab;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tv_service_detail_price)
    TextView price;
    @Bind(R.id.specificans)
    LinearLayout specifican;
    @Bind(R.id.tv_service_detail_selected)
    TextView selectedService;

    private BottomSheetDialog dialog;
    private TextView dgPrice;
    private SpecificanAdapter specificanAdapter;

    private ServiceDetailDataUnit.Specifican selectedSpecifican;
    private ServiceDetailDataUnit.Specifican tempSpecifican;

    private ServiceDetailPresenterContract.Actions mServiceDetailPresenter;
    private long serviceId;
    private ServiceDetailActivityBinding binding;

    @Override
    public int getContentLayout() {
        return R.layout.its_blank;
    }

    @Override
    public String getToolbarTitle() {
        return "服务详情／购买";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.service_detail_activity);
        ButterKnife.bind(this);
        mToolbar.setTitle("服务详情／购买");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mServiceDetailPresenter = DaggerServiceDetailComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .serviceDetailModule(new ServiceDetailModule(this))
                .build().getServiceDetailPresenter();

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return pager.getChildAt(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        setupTopTab();
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceId = getIntent().getExtras().getLong(_key_SERVICE_ID_);
        mServiceDetailPresenter.getServiceContentDetail(serviceId);
        mServiceDetailPresenter.getServiceDetail(serviceId);
    }

    private void setupTopTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("购买", R.drawable.tab_home_selected, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("详情", R.drawable.tab_home_selected, R.drawable.tab_home_unselect));
                    break;
            }
        }
        mTab.setTabData(tabEntities);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                pager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showServiceDetail(final ServiceDetailDataUnit dataUnit) {
        binding.setData(dataUnit);
        selectedSpecifican = dataUnit.getSpecificanList().get(0);
        specifican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == dialog) {
                    dialog = new BottomSheetDialog(ServiceDetailActivity.this);
                }
                View view = LayoutInflater.from(ServiceDetailActivity.this).inflate(R.layout.bs_service_specifican, null, false);//layout pch

                dgPrice = (TextView) view.findViewById(R.id.price);
                Button dgConfirm = (Button) view.findViewById(R.id.confirm);
                dgConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                dgPrice.setText("¥" + selectedSpecifican.getPrice());
                specificanAdapter = new SpecificanAdapter();
                specificanAdapter.setSelectedId(selectedSpecifican.getId());
                specificanAdapter.setDatas(dataUnit.getSpecificanList());
                ((CommonRecyclerView) view.findViewById(R.id.recycler)).setAdapter(specificanAdapter);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (tempSpecifican != null) {
                            selectedSpecifican = tempSpecifican;
                            price.setText("¥" + selectedSpecifican.getPrice());
                            selectedService.setText(selectedSpecifican.getName());
                        }
                    }
                });
                dialog.show();
            }
        });


        mBannerPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return dataUnit.getBannerList().size();//model对接时数量可能有变动
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView ret = new ImageView(ServiceDetailActivity.this);
                ret.setScaleType(ImageView.ScaleType.FIT_CENTER);
                container.addView(ret);
                ret.setLayoutParams(new ViewPager.LayoutParams());
                ImageViewManager.setImageViewWithUrl(ServiceDetailActivity.this, ret, dataUnit.getBannerList().get(position).getImageUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
                return ret;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

        });
    }

    public class SpecificanAdapter extends SimpleRVAdapter<ServiceDetailDataUnit.Specifican> {

        private int selectedId;

        public void setSelectedId(int selectedId) {
            this.selectedId = selectedId;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = getLayoutInflater().inflate(R.layout.item_service_specifican, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final VH vh = (VH) holder;
            vh.str = getBean(position).getName();
            if (selectedId == getBean(position).getId()) {
                vh.textView.setTextColor(ContextCompat.getColor(ServiceDetailActivity.this, R.color.red));
            } else {
                vh.textView.setTextColor(ContextCompat.getColor(ServiceDetailActivity.this, R.color.text_color_primary_black));
            }
            vh.textView.setText(vh.str);
            vh.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tempSpecifican = getBean(position);
                    selectedId = tempSpecifican.getId();
                    dgPrice.setText("¥" + tempSpecifican.getPrice());
                    notifyDataSetChanged();
                }
            });
        }

        class VH extends RecyclerView.ViewHolder {
            private String str;
            private TextView textView;

            public VH(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_service_name);
            }


        }
    }

    @Override
    public void showServiceContentDetail(String desc) {
        ((TextView) findViewById(R.id.desc)).setText(Html.fromHtml(desc));
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

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }


}
