package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddCigaretteActivity extends AppbarActivity {

    @Bind(R.id.fab_add_cigarette)
    FloatingActionButton mFab;
    @Bind(R.id.et_add_cigarette)
    EditText mEtAddCigarette;
    @Bind(R.id.til_add_cigarette)
    TextInputLayout mTilAddCigarette;

    private List<HashMap<String, Integer>> counts = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_cigarette;
    }

    @Override
    public String getToolbarTitle() {
        return "吸烟";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counts = mEtAddCigarette.getText().toString();
                if (counts.length() <= 0) {
                    mTilAddCigarette.setError("请输入吸烟根数");
//                    Snackbar.make(view, "请输入吸烟根数", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (Integer.parseInt(counts) <= 0) {
                    mTilAddCigarette.setError("吸烟根数必须大于0");
//                    Snackbar.make(view, "吸烟根数必须大于0", Snackbar.LENGTH_LONG).show();
                    return;
                }
                mTilAddCigarette.setError("");

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
