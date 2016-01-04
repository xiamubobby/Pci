package com.wonders.xlab.pci.module.base;

import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class BaseFragment extends Fragment {
    private List<BaseModel> mBaseModelList;

    public void addModel(BaseModel model) {
        if (mBaseModelList == null) {
            mBaseModelList = new ArrayList<>();
        }
        mBaseModelList.add(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
    }

    public void showSnackbar(View view, String message) {
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
