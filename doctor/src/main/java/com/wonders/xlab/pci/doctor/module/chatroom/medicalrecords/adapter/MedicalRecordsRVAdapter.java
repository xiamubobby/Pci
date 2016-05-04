package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter;

import android.content.Context;
import android.database.Observable;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.MedicalRecordsItemBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.bean.MedicalRecordsBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsRVAdapter extends SimpleRVAdapter<MedicalRecordsBean> {

    Context context;

    public MedicalRecordsRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_records_item, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder item = (ItemViewHolder) holder;
        MedicalRecordsBean bean = getBean(position);
        item.time.setText(DateUtil.format(bean.getTime().get(), "yyyy-MM-dd"));
        item.tag.setText(bean.getTag().get());
        item.hospitalName.setText(bean.getHospitalName().get());
        item.departmentName.setText(bean.getDepartmentName().get());
        item.medicalResult.setText(bean.getMedicalResult().get());
        item.clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击查看更多", Toast.LENGTH_LONG).show();
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
            ButterKnife.bind(this,itemView);
        }
    }
}
