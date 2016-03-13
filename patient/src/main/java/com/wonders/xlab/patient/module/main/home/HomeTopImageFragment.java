package com.wonders.xlab.patient.module.main.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.patient.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

public class HomeTopImageFragment extends BaseFragment {
    private final static String EXTRA_IMAGE_URL = "imageUrl";

    private String mImageUrl;

    @Bind(R.id.iv_home_top_image)
    ImageView mIvHomeTopImage;


    public HomeTopImageFragment() {
        // Required empty public constructor
    }

    public static HomeTopImageFragment getInstance(String imageUrl) {
        Bundle data = new Bundle();
        data.putString(EXTRA_IMAGE_URL, imageUrl);

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static HomeTopImageFragment newInstance(String imageUrl) {
        Bundle data = new Bundle();
        data.putString(EXTRA_IMAGE_URL, imageUrl);

        HomeTopImageFragment fragment = new HomeTopImageFragment();
        fragment.setArguments(data);
        return fragment;
    }
}
