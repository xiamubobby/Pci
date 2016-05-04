package com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.SurgicalHistoryItemBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.adapter.bean.SurgicalHistoryBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by jimmy on 16/5/3.
 */
public class SurgicalHistoryRVAdapter extends SimpleRVAdapter<SurgicalHistoryBean> {


    private Context context;

    public SurgicalHistoryRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.surgical_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        SurgicalHistoryBean bean = getBean(position);
        viewHolder.hospitalName.setText(bean.getHospitalName().get());
        viewHolder.departmentName.setText(bean.getDepartmentName().get());
        viewHolder.doctorSuggestion.setText(bean.getDoctorSuggestion().get());
        viewHolder.leaveHospital.setText(bean.getLeaveHospitalDiagnostics().get());
        viewHolder.time.setText(DateUtil.format(bean.getSurgicalHistoryBeginTime().get(), "yyyy-MM-dd") + " — " + DateUtil.format(bean.getSurgicalHistoryBeginTime().get(), "yyyy-MM-dd"))
        ;
        viewHolder.clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "查看更多", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.surgical_history_item_time)
        TextView time;
        @Bind(R.id.surgical_history_item_hospital_name)
        TextView hospitalName;
        @Bind(R.id.surgical_history_item_department_name)
        TextView departmentName;
        @Bind(R.id.surgical_history_item_leave_hospital)
        TextView leaveHospital;
        @Bind(R.id.surgical_history_item_doctor_suggestion)
        TextView doctorSuggestion;
        @Bind(R.id.surgical_history_item_click_more)
        TextView clickMore;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
