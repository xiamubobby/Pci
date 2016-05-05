package com.wonders.xlab.patient.module.medicineremind.edit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindDataBean;
import com.wonders.xlab.patient.module.medicineremind.edit.MedicineRemindEditActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.medicine.add.AddMedicineActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.medicine.edit.EditMedicineDoseActivity;
import com.wonders.xlab.patient.module.medicineremind.edit.viewholder.MedicineRemindEditVH;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditRVAdapter extends RecyclerView.Adapter<MedicineRemindEditVH> {
    private Context context;
    private MedicineRemindDataBean medicineRemindDataBean;

    public MedicineRemindEditRVAdapter(Context context) {
        this.context = context;
    }

    public void setMedicineRemindDataBean(MedicineRemindDataBean medicineRemindDataBean) {
        this.medicineRemindDataBean = medicineRemindDataBean;
        notifyDataSetChanged();
    }

    @Override
    public MedicineRemindEditVH onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case UIMedicineRemindEdit.HEADER:
                View header = ((MedicineRemindEditActivity) context).getLayoutInflater().inflate(UIMedicineRemindEdit.HEADER, parent, false);
                return new MedicineRemindEditVH(header);
            case UIMedicineRemindEdit.CONTENT:
                View content = ((MedicineRemindEditActivity) context).getLayoutInflater().inflate(UIMedicineRemindEdit.CONTENT, parent, false);
                return new MedicineRemindEditVH(content);
            case UIMedicineRemindEdit.FOOTER:
                View footer = ((MedicineRemindEditActivity) context).getLayoutInflater().inflate(UIMedicineRemindEdit.FOOTER, parent, false);
                return new MedicineRemindEditVH(footer);
            default:
                throw new UnsupportedOperationException("不支持的viewType类型");
        }
    }

    @Override
    public void onBindViewHolder(MedicineRemindEditVH holder, int position) {
        switch (getItemViewType(position)) {
            case UIMedicineRemindEdit.HEADER:
                if (medicineRemindDataBean != null) {
                    holder.remindTime.setText(medicineRemindDataBean.getTimeStr());
                    holder.startTime.setText(medicineRemindDataBean.getStartTime());
                    holder.endTime.setText(medicineRemindDataBean.getEndTime());
                }
                break;
            case UIMedicineRemindEdit.CONTENT:
                if (medicineRemindDataBean != null) {
                    if (medicineRemindDataBean.getMedicines() != null) {
                        MedicineRemindDataBean.Medicine medicine = medicineRemindDataBean.getMedicines().get(position - 1);
                        holder.medicineName.setText(medicine.getMedicineName());
                        holder.medicineDose.setText(medicine.getMedicineNum() + medicine.getMedicineUnit());
                        holder.medicineView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, EditMedicineDoseActivity.class));
                            }
                        });
                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }

                break;
            case UIMedicineRemindEdit.FOOTER:
                if (medicineRemindDataBean != null) {
                    holder.medicineAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context, AddMedicineActivity.class));
                        }
                    });
                    holder.remindDesc.setText(medicineRemindDataBean.getReminderDesc());
                }


                break;
        }
    }

    @Override
    public int getItemCount() {
        return medicineRemindDataBean.getMedicines() == null ? 2 : medicineRemindDataBean.getMedicines().size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return UIMedicineRemindEdit.HEADER;
        } else if (position == getItemCount() - 1) {
            return UIMedicineRemindEdit.FOOTER;
        } else {
            return UIMedicineRemindEdit.CONTENT;
        }


    }


}
