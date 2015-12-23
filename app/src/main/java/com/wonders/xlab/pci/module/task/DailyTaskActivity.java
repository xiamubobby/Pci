package com.wonders.xlab.pci.module.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.viewpager.WrapHeightViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.task.adapter.WeekViewVPAdapter;
import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.BloodSugarBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SmokeBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.bean.WineBean;
import com.wonders.xlab.pci.module.task.mvn.entity.DailyTaskEntity;
import com.wonders.xlab.pci.module.task.mvn.model.DailyTaskModel;
import com.wonders.xlab.pci.module.task.mvn.model.TakeMedicineModel;
import com.wonders.xlab.pci.module.task.mvn.view.DailyTaskView;
import com.wonders.xlab.pci.module.task.mvn.view.TakeMedicineView;
import com.wonders.xlab.pci.module.task.rxbus.TaskRefreshBus;
import com.wonders.xlab.pci.module.task.rxbus.WeekViewClickBus;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class DailyTaskActivity extends AppbarActivity implements DailyTaskView, TakeMedicineView {

    @Bind(R.id.tv_daily_task_title)
    TextView mTvDailyTaskTitle;
    @Bind(R.id.tab_daily_task_time_period_medicine)
    TabLayout mTabMedicine;
    @Bind(R.id.fl_daily_task_symptom)
    FlowLayout mFlSymptom;
    @Bind(R.id.vp_daily_task_date)
    WrapHeightViewPager mVpDailyTaskDate;
    @Bind(R.id.tv_daily_task_date)
    TextView mTvDailyTaskDate;
    @Bind(R.id.vp_daily_task_medicine)
    WrapHeightViewPager mVpDailyTaskMedicine;
    @Bind(R.id.lc_daily_task_bp)
    LineChart mLineChartBp;
    @Bind(R.id.lc_daily_task_bs)
    LineChart mLineChartBs;
    @Bind(R.id.container_daily_task_smoke)
    LinearLayout mContainerSmoke;
    @Bind(R.id.container_daily_task_wine)
    LinearLayout mContainerWine;
    @Bind(R.id.fam_daily_task)
    FloatingActionsMenu mFamDailyTask;
    @Bind(R.id.tv_daily_task_week)
    TextView mTvDailyTaskWeek;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private WeekViewVPAdapter mWeekViewVPAdapter;

    private FragmentVPAdapter mMedicineVPAdapter;

    private CompositeSubscription mSubscription;

    private long mToday = -1;

    private long mCurrentSelectedTime;

    private DailyTaskModel mDailyTaskModel;

    private TakeMedicineModel mTakeMedicineModel;

    private LayoutInflater mInflater;

    @Override
    public int getContentLayout() {
        return R.layout.activity_daily_task;
    }

    @Override
    public String getToolbarTitle() {
        return "每日健康任务";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);

        init();

        mDailyTaskModel = new DailyTaskModel(this);
        mTakeMedicineModel = new TakeMedicineModel(this);
        addModel(mDailyTaskModel);
        addModel(mTakeMedicineModel);

        initToolbar();
        initRxBusEvent();

        mDailyTaskModel.fetchData(AIManager.getInstance(this).getUserId(), null);
    }

    private void init() {
        mVpDailyTaskDate.setOffscreenPageLimit(0);
        mLineChartBp.setNoDataText("");
        mLineChartBp.setNoDataTextDescription(getResources().getString(R.string.task_empty_tip));
        mLineChartBp.setDescription("血压");
        mLineChartBp.setScaleEnabled(false);

        mLineChartBs.setNoDataText("");
        mLineChartBs.setNoDataTextDescription(getResources().getString(R.string.task_empty_tip));
        mLineChartBs.setDescription("血糖(mol/L)");
        mLineChartBs.setScaleEnabled(false);

        mTvDailyTaskDate.setText(DateUtil.format(Calendar.getInstance().getTimeInMillis(), DateUtil.DEFAULT_FORMAT));
    }

    private void initToolbar() {
        getToolbar().inflateMenu(R.menu.menu_daily_task);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_daily_task_today:
                        mTvDailyTaskDate.setText(DateUtil.format(mToday, DateUtil.DEFAULT_FORMAT));
                        if (mVpDailyTaskDate != null && mWeekViewVPAdapter != null && mWeekViewVPAdapter.getCount() > WeekViewVPAdapter.INITIAL_POSITION) {
                            mVpDailyTaskDate.setCurrentItem(WeekViewVPAdapter.INITIAL_POSITION);
                            mDailyTaskModel.fetchData(AIManager.getInstance(DailyTaskActivity.this).getUserId(), null);
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 接收事件
     */
    private void initRxBusEvent() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof WeekViewClickBus) {
                    //点击日期
                    WeekViewClickBus weekViewClickBus = (WeekViewClickBus) o;
                    mTvDailyTaskDate.setText(DateUtil.format(weekViewClickBus.getTime(), DateUtil.DEFAULT_FORMAT));
                    mDailyTaskModel.fetchData(AIManager.getInstance(DailyTaskActivity.this).getUserId(), weekViewClickBus.getTime());
                } else if (o instanceof MedicineRecordBean) {
                    //选中药物记录
                    MedicineRecordBean medicine = (MedicineRecordBean) o;
                    mTakeMedicineModel.takeMedicine(medicine.getMedicineId());
                } else if (o instanceof TaskRefreshBus) {
                    mDailyTaskModel.fetchData(AIManager.getInstance(DailyTaskActivity.this).getUserId(), null);
                }
            }
        }));
    }

    @Override
    public void getTaskFailed(String message) {
        showSnackbar(mCoordinator, message);
    }

    @Override
    public void initWeekView(long today, String week,List<DailyTaskEntity.RetValuesEntity.UserActivityDtosEntity> mRemindList) {
        if (mToday == -1) {
            mToday = today;
        }
        mCurrentSelectedTime = today;

        mTvDailyTaskDate.setText(DateUtil.format(today, DateUtil.DEFAULT_FORMAT));
        mTvDailyTaskWeek.setText(getResources().getString(R.string.daily_task_week, week));
        if (mWeekViewVPAdapter == null) {
            mWeekViewVPAdapter = new WeekViewVPAdapter(getFragmentManager(), mToday,mRemindList);
            mVpDailyTaskDate.setAdapter(mWeekViewVPAdapter);
            mVpDailyTaskDate.setCurrentItem(WeekViewVPAdapter.INITIAL_POSITION);
        }
//        mWeekViewVPAdapter.setRemindList(mRemindList);
    }

    /**
     * TODO 正式的要分 早中晚
     *
     * @param morningMedicine
     * @param noonMedicine
     * @param nightMedicine
     */
    @Override
    public void initMedicineRecordView(List<MedicineRecordBean> morningMedicine, List<MedicineRecordBean> noonMedicine, List<MedicineRecordBean> nightMedicine) {
        boolean canModify = (mCurrentSelectedTime == mToday);

        if (mMedicineVPAdapter == null) {
            mMedicineVPAdapter = new FragmentVPAdapter(getFragmentManager());
            mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(morningMedicine, canModify), getString(R.string.morning));
            mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(noonMedicine, canModify), getString(R.string.afternoon));
            mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(nightMedicine, canModify), getString(R.string.night));
            mVpDailyTaskMedicine.setAdapter(mMedicineVPAdapter);
            mTabMedicine.setupWithViewPager(mVpDailyTaskMedicine);

        } else {
            for (int i = 0; i < mMedicineVPAdapter.getCount(); i++) {
                MedicineRecordFragment fragment = (MedicineRecordFragment) mMedicineVPAdapter.getItem(i);
                switch (i) {
                    case 0:
                        fragment.setDatas(morningMedicine);
                        break;
                    case 1:
                        fragment.setDatas(noonMedicine);
                        break;
                    case 2:
                        fragment.setDatas(nightMedicine);
                        break;
                }
            }
        }


    }

    /**
     * 主诉症状
     *
     * @param beanList
     */
    @Override
    public void initSymptomView(List<SymptomBean> beanList) {
        mFlSymptom.removeAllViews();
        if (beanList.size() <= 0) {
            mFlSymptom.addView(mInflater.inflate(R.layout.empty_tips, mFlSymptom, false));
            return;
        }
        for (SymptomBean bean : beanList) {
            final View itemView = mInflater.inflate(R.layout.item_task_symptom_label, null, false);

            TextView symptom = (TextView) itemView.findViewById(R.id.tv_item_task_symptom);
            symptom.setText(bean.getSymptom());
            mFlSymptom.addView(itemView);
        }
    }

    /**
     * 血压测量
     *
     * @param beanList
     */
    @Override
    public void initBloodPressure(List<BloodPressureBean> beanList) {
        if (beanList.size() <= 0) {
            mLineChartBp.clear();
            return;
        }
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> spVals = new ArrayList<>();
        ArrayList<Entry> dpVals = new ArrayList<>();
        ArrayList<Entry> bpVals = new ArrayList<>();


        for (int i = 0; i < beanList.size(); i++) {
            BloodPressureBean bean = beanList.get(i);

            xVals.add(DateUtil.format(bean.getTime(), "HH:mm"));
            spVals.add(new Entry(bean.getSystolicPressure(), i));
            dpVals.add(new Entry(bean.getDiastolicPressure(), i));
            bpVals.add(new Entry(bean.getBloodPressure(), i));
        }

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        LineDataSet spDataSet = new LineDataSet(spVals, "收缩压");
        LineDataSet dpDataSet = new LineDataSet(dpVals, "舒张压");
        LineDataSet bpDataSet = new LineDataSet(bpVals, "血压");
        initLineDataSet(spDataSet, Color.RED);
        initLineDataSet(dpDataSet, Color.GREEN);
        initLineDataSet(bpDataSet, Color.BLUE);
        spDataSet.setCircleColor(R.color.colorPrimary);
        dpDataSet.setCircleColor(R.color.colorPrimary);
        bpDataSet.setCircleColor(R.color.colorPrimary);
        dataSets.add(spDataSet);
        dataSets.add(dpDataSet);
        dataSets.add(bpDataSet);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        mLineChartBp.setData(data);
        mLineChartBp.animateXY(500, 500);
    }

    /**
     * 血糖测量
     *
     * @param beanList
     */
    @Override
    public void initBloodSugar(List<BloodSugarBean> beanList) {
        if (beanList.size() <= 0) {
            mLineChartBs.clear();
            return;
        }
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> sbsVals = new ArrayList<>();


        for (int i = 0; i < beanList.size(); i++) {
            BloodSugarBean bean = beanList.get(i);

            xVals.add(DateUtil.format(bean.getTime(), "HH:mm"));
            sbsVals.add(new Entry(bean.getBloodSugar(), i));
        }

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        LineDataSet spDataSet = new LineDataSet(sbsVals, "血糖");
        initLineDataSet(spDataSet, Color.GREEN);
        dataSets.add(spDataSet);
        spDataSet.setCircleColor(R.color.colorPrimary);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        mLineChartBs.setData(data);
        mLineChartBs.animateXY(500, 500);
    }

    @Override
    public void initSmokeView(List<SmokeBean> beanList) {
        mContainerSmoke.removeAllViews();
        if (beanList.size() <= 0) {
            mContainerSmoke.addView(mInflater.inflate(R.layout.empty_tips, mContainerSmoke, false));
            return;
        }
        for (SmokeBean bean : beanList) {
            View view = mInflater.inflate(R.layout.item_task_simple_text, null, false);
            TextView tvTime = (TextView) view.findViewById(R.id.tv_item_task_simple_text_time);
            TextView tvValue = (TextView) view.findViewById(R.id.tv_item_task_simple_text_value);
            tvTime.setText(DateUtil.format(bean.getTime(), DateUtil.DEFAULT_FORMAT));
            tvValue.setText(bean.getValue());
            mContainerSmoke.addView(view);
        }
    }

    @Override
    public void initWineView(List<WineBean> beanList) {
        mContainerWine.removeAllViews();
        if (beanList.size() <= 0) {
            mContainerWine.addView(mInflater.inflate(R.layout.empty_tips, mContainerWine, false));
            return;
        }
        for (WineBean bean : beanList) {
            View view = mInflater.inflate(R.layout.item_task_simple_text, null, false);
            TextView tvTime = (TextView) view.findViewById(R.id.tv_item_task_simple_text_time);
            TextView tvValue = (TextView) view.findViewById(R.id.tv_item_task_simple_text_value);

            tvTime.setText(DateUtil.format(bean.getTime(), DateUtil.DEFAULT_FORMAT));
            tvValue.setText(bean.getValue());
            mContainerWine.addView(view);
        }
    }

    private void initLineDataSet(LineDataSet dataSet, int color) {
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSet.setColor(color);
        dataSet.setDrawValues(false);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(Color.RED);
        dataSet.setDrawCircleHole(false);
        dataSet.setHighlightEnabled(false);
        dataSet.setHighLightColor(Color.RED);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.fam_daily_task_bp)
    public void onRecordBpClick() {
        recordNewData(AddBPActivity.class);
    }

    @OnClick(R.id.fam_daily_task_bs)
    public void onRecordBsClick() {
        recordNewData(AddBSActivity.class);
    }

    @OnClick(R.id.fam_daily_task_smoke)
    public void onRecordCigaretteClick() {
        recordNewData(AddCigaretteActivity.class);
    }

    @OnClick(R.id.fam_daily_task_wine)
    public void onRecordWineClick() {
        recordNewData(AddWineActivity.class);
    }

    @OnClick(R.id.fam_daily_task_symptom)
    public void onRecordSymptomClick() {
        recordNewData(AddSymptomActivity.class);
    }

    private void recordNewData(Class targetActivity) {
        startActivity(new Intent(this, targetActivity));
        mFamDailyTask.collapse();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void takeMedicineSuccess() {

    }

    @Override
    public void takeMedicineFailed(String message) {
        showSnackbar(mCoordinator, message);
        mDailyTaskModel.fetchData(AIManager.getInstance(this).getUserId(), mCurrentSelectedTime);
    }
}
