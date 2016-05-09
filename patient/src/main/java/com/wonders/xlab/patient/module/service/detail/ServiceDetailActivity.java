package com.wonders.xlab.patient.module.service.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.databinding.ServiceDetailActivityBinding;
import com.wonders.xlab.patient.databinding.ServiceDetailContentBinding;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.presenter.ServiceDetailPresenterContract;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailActivity extends AppbarActivity implements ServiceDetailPresenterContract.ViewListener {

    public final static String _key_SERVICE_ID_ = "serviceId";

    @Bind(R.id.view_pager_service_detail)
    ViewPager mViewPager;

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

        mServiceDetailPresenter = DaggerServiceDetailComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .serviceDetailModule(new ServiceDetailModule(this))
                .build().getServiceDetailPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceId = getIntent().getExtras().getLong(_key_SERVICE_ID_);
        mServiceDetailPresenter.getServiceDetail(serviceId);
    }

    @Override
    public void showServiceDetail(final ServiceDetailDataUnit dataUnit) {
        binding.setData(dataUnit);
        mViewPager.setAdapter(new PagerAdapter() {
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
            public void destroyItem(ViewGroup container, int position, Object object) { }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

        });
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
