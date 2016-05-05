package com.wonders.xlab.patient.module.medicineremind.edit.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.xlab.patient.R;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditVH extends RecyclerView.ViewHolder {

    //    @Bind(R.id.tv_medicine_remind_edit_top_item_remind_time)
    public TextView remindTime;
    //    @Bind(R.id.tv_medicine_remind_edit_top_item_start_time)
    public TextView startTime;
    //    @Bind(R.id.tv_medicine_remind_edit_top_item_end_time)
    public TextView endTime;
    //    @Bind(R.id.rl_medicine_remind_edit_medicine_item)
    public RelativeLayout medicineView;
    //    @Bind(R.id.tv_medicine_remind_edit_medicine_item_name)
    public TextView medicineName;
    //    @Bind(R.id.tv_medicine_remind_edit_medicine_item_dose)
    public TextView medicineDose;
    //    @Bind(R.id.tv_medicine_remind_edit_medicine_item_delete)
    public ImageView delete;
    //    @Bind(R.id.tv_medicine_remind_edit_bottom_item_add)
    public RelativeLayout medicineAdd;
    //    @Bind(R.id.tv_medicine_remind_edit_bottom_remind_desc)
    public TextView remindDesc;


    public MedicineRemindEditVH(View itemView) {
        super(itemView);

        remindTime = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_top_item_remind_time);
        startTime = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_top_item_start_time);
        endTime = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_top_item_end_time);
        medicineView = (RelativeLayout) itemView.findViewById(R.id.rl_medicine_remind_edit_medicine_item);
        medicineName = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_medicine_item_name);
        medicineDose = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_medicine_item_dose);
        delete = (ImageView) itemView.findViewById(R.id.tv_medicine_remind_edit_medicine_item_delete);
        medicineAdd = (RelativeLayout) itemView.findViewById(R.id.tv_medicine_remind_edit_bottom_item_add);
        remindDesc = (TextView) itemView.findViewById(R.id.tv_medicine_remind_edit_bottom_remind_desc);

    }
}
