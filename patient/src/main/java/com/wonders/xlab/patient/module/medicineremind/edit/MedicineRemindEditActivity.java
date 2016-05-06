package com.wonders.xlab.patient.module.medicineremind.edit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicineRemindEditActivity extends AppbarActivity {

    @Bind(R.id.nb_medicine_remind_edit_am_or_pm)
    NumberPicker mNbAMOrPM;
    @Bind(R.id.nb_medicine_remind_edit_hour)
    NumberPicker mNbHour;
    @Bind(R.id.nb_medicine_remind_edit_minutes)
    NumberPicker mNbMinutes;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_remind_edit_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mNbAMOrPM.setMaxValue(1);
        mNbAMOrPM.setDisplayedValues(new String[]{"上午", "下午"});

        mNbHour.setMaxValue(11);
        mNbHour.setDisplayedValues(new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12"});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
