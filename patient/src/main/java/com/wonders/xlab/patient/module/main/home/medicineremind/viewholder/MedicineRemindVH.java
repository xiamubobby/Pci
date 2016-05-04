package com.wonders.xlab.patient.module.main.home.medicineremind.viewholder;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.home.medicineremind.bean.MedicineRemindDataBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindVH extends MultiViewHolder<MedicineRemindDataBean> {

    @Bind(R.id.tv_medicine_remind_item_time_period)
    TextView timePeriod;
    @Bind(R.id.tv_medicine_remind_item_remind_time)
    TextView time;
    @Bind(R.id.sh_medicine_remind_item_switch)
    Switch switsh;
    @Bind(R.id.tv_medicine_remind_item_medicine_names)
    TextView medicines;
    @Bind(R.id.tv_medicine_remind_item_end_time)
    TextView endTime;


    public MedicineRemindVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(MedicineRemindDataBean data) {

    }
}
