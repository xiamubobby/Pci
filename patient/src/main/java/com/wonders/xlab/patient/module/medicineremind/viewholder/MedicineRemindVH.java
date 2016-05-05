package com.wonders.xlab.patient.module.medicineremind.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindDataBean;

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

    private OnSwitchChangeListener mOnSwitchChangeListener;

    private int curColor;

    public MedicineRemindVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(MedicineRemindDataBean data) {
        timePeriod.setText(data.getTimePeriod());
        time.setText(data.getTimeStr());
        switsh.setChecked(data.isRemind());
        if (data.isRemind()) {
            curColor = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark);
        } else {
            curColor = ContextCompat.getColor(itemView.getContext(), R.color.text_color_primary_gray);
        }
        timePeriod.setTextColor(curColor);
        time.setTextColor(curColor);
        String medicineStr = "药品名称：";
        for (int i = 0; i < data.getMedicines().size(); i++) {
            if (i == 0) {
                medicineStr += data.getMedicines().get(i).getMedicineName();
            } else {
                medicineStr += "，" + data.getMedicines().get(i).getMedicineName();
            }

        }
        medicines.setText(medicineStr);
        if (data.getEndTime() != null && !data.getEndTime().equals("")) {
            endTime.setText("结束时间：" + data.getEndTime());
        } else {
            endTime.setText("结束时间：长期");
        }
        switsh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (null != mOnSwitchChangeListener) {
                    mOnSwitchChangeListener.onChange(isChecked);
                    if (isChecked) {
                        curColor = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark);
                    } else {
                        curColor = ContextCompat.getColor(itemView.getContext(), R.color.text_color_primary_gray);
                    }
                    timePeriod.setTextColor(curColor);
                    time.setTextColor(curColor);
                }

            }
        });

    }

    public void setmOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        mOnSwitchChangeListener = onSwitchChangeListener;
    }

    public interface OnSwitchChangeListener {
        void onChange(boolean isChecked);
    }
}
