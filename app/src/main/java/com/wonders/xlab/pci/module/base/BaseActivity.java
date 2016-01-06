package com.wonders.xlab.pci.module.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class BaseActivity extends AppCompatActivity {

    private List<BaseModel> mBaseModelList;

    public void addModel(BaseModel model) {
        if (mBaseModelList == null) {
            mBaseModelList = new ArrayList<>();
        }
        mBaseModelList.add(model);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.update(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
    }

    public void showSnackbar(View view, String message, boolean autoDismiss) {
        if (view != null) {
            if (autoDismiss) {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show();
            }
        }
    }
}
