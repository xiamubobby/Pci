package com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.bean.MedicalRecordsBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsRVAdapter extends SimpleRVAdapter<MedicalRecordsBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_records_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder item = (ItemViewHolder) holder;
        MedicalRecordsBean bean = getBean(position);
        item.time.setText(bean.getTime());
        item.tag.setText(bean.getOfficeType());
        item.hospitalName.setText("就诊医院：" + bean.getHospitalName());
        item.departmentName.setText("就诊科室：" + bean.getDepartmentName());
        item.medicalResult.setText("就诊结果：" + bean.getMedicalResult());
        item.clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(item.itemView.getContext(), "点击查看更多", Toast.LENGTH_LONG).show();
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.medical_records_item_time)
        TextView time;
        @Bind(R.id.medical_records_item_tag)
        TextView tag;
        @Bind(R.id.medical_records_item_hospital_name)
        TextView hospitalName;
        @Bind(R.id.medical_records_item_department_name)
        TextView departmentName;
        @Bind(R.id.medical_records_item_medical_result)
        TextView medicalResult;
        @Bind(R.id.medical_records_item_click_more)
        TextView clickMore;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
