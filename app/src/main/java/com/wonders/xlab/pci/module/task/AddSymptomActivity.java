package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.mvn.entity.task.SymptomEntity;
import com.wonders.xlab.pci.mvn.model.task.SymptomModel;
import com.wonders.xlab.pci.mvn.view.task.SymptomView;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddSymptomActivity extends AppbarActivity implements SymptomView {

    @Bind(R.id.container_add_symptom)
    LinearLayout mContainerAddSymptom;
    @Bind(R.id.fab_add_symptom)
    FloatingActionButton mFabAddSymptom;

    private SymptomModel mSymptomModel;

    private LayoutInflater mInflater;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_symptom;
    }

    @Override
    public String getToolbarTitle() {
        return "主诉症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mSymptomModel = new SymptomModel(this);
        addModel(mSymptomModel);

        mInflater = LayoutInflater.from(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_symptom);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSymptomModel.getSymptoms();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showSymptoms(List<SymptomEntity.RetValuesEntity> entityList) {
        mContainerAddSymptom.removeAllViews();
        for (SymptomEntity.RetValuesEntity entity : entityList) {
            LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.item_symptom, mContainerAddSymptom, false);
            TextView title = (TextView) itemView.findViewById(R.id.tv_item_symptom_title);
            title.setText(entity.getTitle());

            FlowLayout contents = (FlowLayout) itemView.findViewById(R.id.fl_item_symptom);
            for (String symptomName : entity.getContent()) {
                final TextView labelView = (TextView) mInflater.inflate(R.layout.item_symptom_content_label, itemView, false);
                labelView.setText(symptomName);
                //将它所属的分组的标题设置在label的tag中
                labelView.setTag(entity.getTitle());
                labelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        labelView.setSelected(!labelView.isSelected());
                    }
                });

                contents.addView(labelView);
            }
            mContainerAddSymptom.addView(itemView);
        }
    }

    @Override
    public void getSymptomsFailed(String message) {

    }

    @Override
    public void saveSuccess() {

    }

    @Override
    public void saveFailed(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
