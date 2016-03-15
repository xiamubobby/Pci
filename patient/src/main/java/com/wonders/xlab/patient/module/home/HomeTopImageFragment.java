package com.wonders.xlab.patient.module.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.WebActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

public class HomeTopImageFragment extends BaseFragment {
    private final static String EXTRA_IMAGE_URL = "imageUrl";
    private final static String EXTRA_WEB_URL = "webUrl";

    private String mImageUrl;
    private String mWebUrl;

    @Bind(R.id.iv_home_top_image)
    ImageView mIvHomeTopImage;

    public HomeTopImageFragment() {
        // Required empty public constructor
    }

    public static HomeTopImageFragment newInstance(String imageUrl, String webUrl) {
        Bundle data = new Bundle();
        data.putString(EXTRA_IMAGE_URL, imageUrl);
        data.putString(EXTRA_WEB_URL, webUrl);

        HomeTopImageFragment fragment = new HomeTopImageFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (null != data) {
            mImageUrl = data.getString(EXTRA_IMAGE_URL);
            mWebUrl = data.getString(EXTRA_WEB_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_top_image_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageViewManager.setImageViewWithUrl(getActivity(), mIvHomeTopImage, mImageUrl, -1);
        mIvHomeTopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_WEB_URL, mWebUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
