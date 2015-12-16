package com.wonders.xlab.pci.module.task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.viewpager.WrapHeightViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.task.adapter.WeekViewVPAdapter;
import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.BloodSugarBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.rxbus.WeekViewClickBus;
import com.wonders.xlab.pci.mvn.model.DailyTaskModel;
import com.wonders.xlab.pci.mvn.view.DailyTaskView;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.labelview.LabelView;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class DailyTaskActivity extends AppbarActivity implements DailyTaskView {

    @Bind(R.id.tv_daily_task_title)
    TextView mTvDailyTaskTitle;
    @Bind(R.id.tab_daily_task_time_period_medicine)
    TabLayout mTabMedicine;
    @Bind(R.id.fl_daily_task_symptom)
    FlowLayout mFlSymptom;
    @Bind(R.id.et_daily_task_cigarette)
    EditText mEtDailyTaskCigarette;
    @Bind(R.id.et_daily_task_wine)
    EditText mEtDailyTaskWine;
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

    private WeekViewVPAdapter mWeekViewVPAdapter;

    private FragmentVPAdapter mMedicineVPAdapter;

    private CompositeSubscription mSubscription;

    private long mToday;

    private DailyTaskModel mDailyTaskModel;

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
        addModel(mDailyTaskModel);

        initToolbar();
        initRxBusEvent();

        mDailyTaskModel.fetchData();
    }

    private void init() {
        mLineChartBp.setNoDataText("");
        mLineChartBp.setNoDataTextDescription("暂无数据，点击下方记录新数据");
        mLineChartBp.setDescription("血压");
        mLineChartBp.setScaleEnabled(false);

        mLineChartBs.setNoDataText("");
        mLineChartBs.setNoDataTextDescription("暂无数据，点击下方记录新数据");
        mLineChartBs.setDescription("血糖(mol/L)");
        mLineChartBs.setScaleEnabled(false);
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

        mSubscription.add(RxBus.getRxBusSingleton().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof WeekViewClickBus) {
                    //点击日期
                    WeekViewClickBus weekViewClickBus = (WeekViewClickBus) o;
                    mTvDailyTaskDate.setText(DateUtil.format(weekViewClickBus.getTime(), DateUtil.DEFAULT_FORMAT));
                } else if (o instanceof MedicineRecordBean) {
                    //选中药物记录

                }
            }
        }));
    }

    @Override
    public void initWeekView(long today) {
        mToday = today;
        mWeekViewVPAdapter = new WeekViewVPAdapter(getFragmentManager(), today);
        mVpDailyTaskDate.setAdapter(mWeekViewVPAdapter);
        mVpDailyTaskDate.setCurrentItem(WeekViewVPAdapter.INITIAL_POSITION);
    }

    /**
     * TODO 正式的要分 早中晚
     *
     * @param recordBeanList
     */
    @Override
    public void initMedicineRecordView(List<MedicineRecordBean> recordBeanList) {
        if (mMedicineVPAdapter == null) {
            mMedicineVPAdapter = new FragmentVPAdapter(getFragmentManager());
        }

        mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(recordBeanList), getString(R.string.morning));
        mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(recordBeanList), getString(R.string.afternoon));
        mMedicineVPAdapter.addFragment(MedicineRecordFragment.newInstance(recordBeanList), getString(R.string.night));
        mVpDailyTaskMedicine.setAdapter(mMedicineVPAdapter);
        mTabMedicine.setupWithViewPager(mVpDailyTaskMedicine);
    }

    /**
     * 主诉症状
     * @param beanList
     */
    @Override
    public void initSymptomView(List<SymptomBean> beanList) {
        mFlSymptom.removeAllViews();
        for (SymptomBean bean : beanList) {
            final View itemView = mInflater.inflate(R.layout.item_task_symptom_label, null, false);

            LabelView symptom = (LabelView) itemView.findViewById(R.id.tv_item_task_symptom);
            symptom.setText(bean.getSymptom());
            mFlSymptom.addView(itemView);
        }

        final View addView = mInflater.inflate(R.layout.item_task_symptom_add_new, null, false);

        TextView addTv = (TextView) addView.findViewById(R.id.tv_item_task_symptom_add_new);
        addTv.setText("点击纪录");
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mFlSymptom.addView(addView);
    }

    /**
     * 血压测量
     * @param beanList
     */
    @Override
    public void initBloodPressure(List<BloodPressureBean> beanList) {
        if (beanList == null || beanList.size() <= 0) {
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
        dataSets.add(spDataSet);
        dataSets.add(dpDataSet);
        dataSets.add(bpDataSet);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        mLineChartBp.setData(data);

    }

    /**
     * 血糖测量
     * @param beanList
     */
    @Override
    public void initBloodSugar(List<BloodSugarBean> beanList) {
        if (beanList == null || beanList.size() <= 0) {
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

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        mLineChartBs.setData(data);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
