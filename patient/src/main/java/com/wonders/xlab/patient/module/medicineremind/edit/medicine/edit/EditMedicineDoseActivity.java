package com.wonders.xlab.patient.module.medicineremind.edit.medicine.edit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/5/5.
 */
public class EditMedicineDoseActivity extends AppbarActivity {
    @Bind(R.id.et_medicine_dose_edit_num)
    EditText medicineNum;
    @Bind(R.id.tv_medicine_dose_edit_unit)
    TextView medicineUnit;
    @Bind(R.id.img_medicine_dose_edit_arrow)
    ImageView arrow;

    private String unitStr = "片";

    @Override
    public int getContentLayout() {
        return R.layout.medicine_dose_edit_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "选择服用数量";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        medicineUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showUnitDialog() {
        LinearLayout customView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_medicine_unit_check, null);
        TextView slice = (TextView) customView.findViewById(R.id.unit_slice);
        slice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitStr = "片";
            }
        });
        TextView grain = (TextView) customView.findViewById(R.id.unit_grain);
        grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitStr = "粒";
            }
        });
        TextView ml = (TextView) customView.findViewById(R.id.unit_ml);
        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitStr = "毫升";
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(customView).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        medicineUnit.setText(unitStr);
                    }
                });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medicine_dose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_medicine_remind_add:
                showShortToast("保存成功");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
