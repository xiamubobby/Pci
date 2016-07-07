package com.wonders.xlab.patient.module.me.doctor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.doctordetail.DoctorDetailActivity;
import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiamubobby on 16/7/7.
 */

public class DoctorListAdapter extends SimpleRVAdapter<DoctorListEntity.DoctorPatientRelationDoctorList.DoctorPatientRelationDoctor> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ret = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, null);
        return new ViewHolder(ret);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final DoctorListEntity.DoctorPatientRelationDoctorList.DoctorPatientRelationDoctor bean = getBean(position);
        final ViewHolder item = (ViewHolder) holder;
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), item.avatar, bean.getAvatarUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
        item.department.setText(bean.getDepartment());
        item.doctorName.setText(bean.getName());
        item.doctorTitle.setText(bean.getJobTitle());
        item.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof Activity) {
                    Intent i = new Intent((Activity) view.getContext(), DoctorDetailActivity.class);
                    i.putExtra(DoctorDetailActivity.EXTRA_TITLE, bean.getJobTitle());
                    i.putExtra(DoctorDetailActivity.EXTRA_ID, bean.getDoctorId());
                    i.putExtra(DoctorDetailActivity.EXTRA_TYPE, DoctorDetailActivity.TYPE_DOCTOR);
                    ((Activity) view.getContext()).startActivity(i);
                } else {
                    Log.e("System.out.println", "clicked on a view whose context is not an activity");
                }
            }
        });
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar) ImageView avatar;
        @Bind(R.id.department) TextView department;
        @Bind(R.id.doctorName) TextView doctorName;
        @Bind(R.id.doctorTitle) TextView doctorTitle;
        @Bind(R.id.hospital) TextView hospital;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
