package com.wonders.xlab.pci.module.task;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.drakeet.labelview.LabelView;

public class MedicineRecordFragment extends BaseFragment {
    private static final String ARG_MEDICINE = "medicine";
    private LinearLayout mLinearLayout;
    private List<MedicineRecordBean> mBeanList;

    public MedicineRecordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MedicineRecordFragment newInstance(List<MedicineRecordBean> beanList) {
        MedicineRecordFragment fragment = new MedicineRecordFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_MEDICINE, (ArrayList<? extends Parcelable>) beanList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBeanList = getArguments().getParcelableArrayList(ARG_MEDICINE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_medicine_record, container, false);
        mLinearLayout = (LinearLayout) itemView;
        ButterKnife.bind(this, itemView);
        return itemView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        mLinearLayout.removeAllViews();
        for (final MedicineRecordBean bean : mBeanList) {
            final View itemView = layoutInflater.inflate(R.layout.item_task_medicine_record, null, false);
            CheckBox mCbName = (CheckBox) itemView.findViewById(R.id.cb_item_task_medicine_record_name);
            LabelView mTvDosage = (LabelView) itemView.findViewById(R.id.tv_item_task_medicine_record_dosage);
            mCbName.setText(bean.getMedicineName());
            mTvDosage.setText(bean.getMedicineDosage());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxBus.getInstance().send(bean);
                }
            });
            mLinearLayout.addView(itemView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
