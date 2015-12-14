package com.wonders.xlab.pci.module.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.LoadMoreRecyclerView;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.home.adapter.HomeRVAdapter;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.mvn.model.HomeModel;
import com.wonders.xlab.pci.mvn.view.HomeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView{

    @Bind(R.id.rv_home)
    LoadMoreRecyclerView mRvHome;

    private HomeRVAdapter mHomeRVAdapter;

    private HomeModel mHomeModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeModel = new HomeModel(this);
        addModel(mHomeModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true));
        mHomeModel.getHomeList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showHomeList(List<HomeTaskBean> beanList) {
        if (mHomeRVAdapter == null) {
            mHomeRVAdapter = new HomeRVAdapter(getActivity());
            mRvHome.setAdapter(mHomeRVAdapter);
        }
        mHomeRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendHomeList(List<HomeTaskBean> beanList) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
