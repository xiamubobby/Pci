package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.bean.SurgicalHistoryBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jimmy on 16/5/3.
 */
public class SurgicalHistoryRVAdapter extends SimpleRVAdapter<SurgicalHistoryBean> {

    public SurgicalHistoryRVAdapter() {

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

        int length = "医院名称：".length();

        Spannable hospitalSp = new SpannableStringBuilder(String.format("医院名称：%s", bean.getHospitalName()));
        hospitalSp.setSpan(new ForegroundColorSpan(viewHolder.itemView.getContext().getResources().getColor(R.color.text_color_primary_black)),0,length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable departmentSp = new SpannableStringBuilder(String.format("科室名称：%s", bean.getDepartmentName()));
        departmentSp.setSpan(new ForegroundColorSpan(viewHolder.itemView.getContext().getResources().getColor(R.color.text_color_primary_black)),0,length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable suggestionSp = new SpannableStringBuilder(String.format("医生建议：%s", bean.getDoctorSuggestion()));
        suggestionSp.setSpan(new ForegroundColorSpan(viewHolder.itemView.getContext().getResources().getColor(R.color.text_color_primary_black)),0,length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable diagnosticsSp = new SpannableStringBuilder(String.format("出院诊断：%s", bean.getLeaveHospitalDiagnostics()));
        diagnosticsSp.setSpan(new ForegroundColorSpan(viewHolder.itemView.getContext().getResources().getColor(R.color.text_color_primary_black)),0,length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.hospitalName.setText(hospitalSp);
        viewHolder.departmentName.setText(departmentSp);
        viewHolder.doctorSuggestion.setText(suggestionSp);
        viewHolder.leaveHospital.setText(diagnosticsSp);
        viewHolder.time.setText(bean.getSurgicalHistoryTime());
        viewHolder.clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
