package com.wonders.xlab.patient.module.me.sex;

import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.me.sex.adapter.SexBean;
import com.wonders.xlab.patient.module.me.sex.adapter.SexRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class SexActivity extends AppbarActivity {

    public static final String EXTRA_SEX_ID = "sexId";
    public static final String EXTRA_SEX_NAME = "sexName";

    @Bind(R.id.recyclerView)
    CommonRecyclerView mRecyclerView;

    private SexRVAdapter mRVAdapter;
    private List<SexBean> sexBeanList;

    @Override
    public int getContentLayout() {
        return R.layout.sex_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        initBeanList();
        showSexList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initBeanList() {
        sexBeanList = new ArrayList<>();
        SexBean female = new SexBean();
        female.setId("Female");
        female.setName("女");
        SexBean male = new SexBean();
        male.setId("Male");
        male.setName("男");
        sexBeanList.add(female);
        sexBeanList.add(male);

    }

    private void showSexList() {
        if (mRVAdapter == null) {
            mRVAdapter = new SexRVAdapter();
            mRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    SexBean bean = mRVAdapter.getBean(position);
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_SEX_ID, bean.getId());
                    intent.putExtra(EXTRA_SEX_NAME, bean.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(sexBeanList);

    }

}