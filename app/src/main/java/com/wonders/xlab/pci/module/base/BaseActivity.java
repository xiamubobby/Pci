package com.wonders.xlab.pci.module.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.wonders.xlab.pci.mvn.model.BaseModel;
import com.wonders.xlab.pci.service.XEMChatService;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, XEMChatService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
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

}
