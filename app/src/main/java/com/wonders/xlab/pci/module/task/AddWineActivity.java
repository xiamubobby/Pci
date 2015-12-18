package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddWineActivity extends AppbarActivity {

    @Bind(R.id.et_add_wine)
    EditText mEtAddWine;
    @Bind(R.id.til_add_wine)
    TextInputLayout mTilAddWine;
    @Bind(R.id.sp_add_wine)
    Spinner mSpAddWine;
    @Bind(R.id.fab_add_wine)
    FloatingActionButton mFab;

    private List<HashMap<String, String>> mWineTypeList;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_wine;
    }

    @Override
    public String getToolbarTitle() {
        return "饮酒";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWineTypeSpinner();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_wine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initWineTypeSpinner() {
        if (mWineTypeList == null) {
            mWineTypeList = new ArrayList<>();
        } else {
            mWineTypeList.clear();
        }
        for (int i = 0; i < 4; i++) {
            HashMap<String, String> wine = new HashMap<>();
            switch (i) {
                case 0:
                    wine.put("wine", "红酒");
                    break;
                case 1:
                    wine.put("wine", "白酒");
                    break;
                case 2:
                    wine.put("wine", "黄酒");
                    break;
                case 3:
                    wine.put("wine", "啤酒");
                    break;
            }
            mWineTypeList.add(wine);
        }
        mSpAddWine.setAdapter(new SimpleAdapter(this, mWineTypeList, R.layout.item_spinner_text, new String[]{"wine"}, new int[]{R.id.tv_spinner}));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
