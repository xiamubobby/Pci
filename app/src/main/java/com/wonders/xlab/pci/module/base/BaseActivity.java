package com.wonders.xlab.pci.module.base;

import android.support.v7.app.AppCompatActivity;

import com.wonders.xlab.pci.mvn.BaseModel;

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

}
