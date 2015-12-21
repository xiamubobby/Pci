package com.wonders.xlab.pci.module.task;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MedicineRecordFragment extends BaseFragment {
    private static final String ARG_MEDICINE = "medicine";
    private static final String ARG_MODIFY = "canModify";
    private LinearLayout mLinearLayout;
    private List<MedicineRecordBean> mBeanList;
    private boolean mCanModify;

    public MedicineRecordFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static MedicineRecordFragment newInstance(List<MedicineRecordBean> beanList, boolean canModify) {
        MedicineRecordFragment fragment = new MedicineRecordFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_MEDICINE, (ArrayList<? extends Parcelable>) beanList);
        args.putBoolean(ARG_MODIFY, canModify);
        fragment.setArguments(args);
        return fragment;
    }

    public void setDatas(List<MedicineRecordBean> beanList) {
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        } else {
            mBeanList.clear();
        }
        if (beanList != null) {
            mBeanList.addAll(beanList);
        }
        refreshViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBeanList = getArguments().getParcelableArrayList(ARG_MEDICINE);
            mCanModify = getArguments().getBoolean(ARG_MODIFY, false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_medicine_record, container, false);
        ButterKnife.bind(this, mLinearLayout);
        return mLinearLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshViews();
    }

    private void refreshViews() {
        if (mLinearLayout != null) {
            mLinearLayout.removeAllViews();
        }
        if (mBeanList != null && mBeanList.size() > 0) {
            int interval = (int) getResources().getDimension(R.dimen.item_interval);
            int intervalLarge = (int) getResources().getDimension(R.dimen.item_interval_large);

            for (final MedicineRecordBean bean : mBeanList) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(intervalLarge, interval, intervalLarge, interval);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);

                final CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setPadding(0, 0, intervalLarge, 0);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.weight = 1;
                checkBox.setLayoutParams(lp1);
                checkBox.setChecked(bean.isChecked());

                TextView labelView = new TextView(getActivity());
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                labelView.setLayoutParams(lp2);

                linearLayout.addView(checkBox);
                linearLayout.addView(labelView);

                checkBox.setText(bean.getMedicineName());
                labelView.setText(getResources().getString(R.string.daily_task_mg, bean.getMedicineDosage()));
                if (mCanModify) {
                    checkBox.setClickable(true);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bean.setChecked(checkBox.isChecked());
                            RxBus.getInstance().send(bean);
                        }
                    });
                } else {
                    checkBox.setClickable(false);
                }
                mLinearLayout.addView(linearLayout);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}