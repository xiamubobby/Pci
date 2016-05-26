package com.wonders.xlab.patient.module.home.top;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.WebActivity;
import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

public class HomeTopImageFragment extends BaseFragment {
    private final static String EXTRA_IMAGE_URL = "imageUrl";
    private final static String EXTRA_WEB_URL = "webUrl";
    private final static String EXTRA_TITLE = "title";

    private String mImageUrl;
    private String mWebUrl;
    private String mTitle;

    @Bind(R.id.iv_home_top_image)
    ImageView mIvHomeTopImage;

    public HomeTopImageFragment() {
        // Required empty public constructor
    }

    public static HomeTopImageFragment newInstance(String imageUrl, String webUrl, String title) {
        Bundle data = new Bundle();
        data.putString(EXTRA_IMAGE_URL, imageUrl);
        data.putString(EXTRA_WEB_URL, webUrl);
        data.putString(EXTRA_TITLE, title);

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
            mTitle = data.getString(EXTRA_TITLE);
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
        ImageViewManager.setImageViewWithUrl(getActivity(), mIvHomeTopImage, mImageUrl, ImageViewManager.PLACE_HOLDER_EMPTY);
        if (URLUtil.isHttpUrl(mWebUrl) || URLUtil.isHttpsUrl(mWebUrl)) {
            mIvHomeTopImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("title",mTitle);
                    MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_TOP_IMAGE, map);

                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(WebActivity.EXTRA_WEB_URL, mWebUrl);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
