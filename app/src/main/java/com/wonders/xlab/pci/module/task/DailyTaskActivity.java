package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.viewpager.XViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.task.adapter.WeekViewVPAdapter;
import com.wonders.xlab.pci.module.task.bean.BloodPressureBean;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.bean.SymptomBean;
import com.wonders.xlab.pci.module.task.rxbus.WeekViewClickBus;
import com.wonders.xlab.pci.mvn.model.DailyTaskModel;
import com.wonders.xlab.pci.mvn.view.DailyTaskView;
import com.zhy.view.flowlayout.FlowLayout;

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
    @Bind(R.id.tab_daily_task_time_period_bp)
    TabLayout mTabDailyTaskTimePeriodBp;
    @Bind(R.id.et_daily_task_cigarette)
    EditText mEtDailyTaskCigarette;
    @Bind(R.id.tab_daily_task_time_period_bs)
    TabLayout mTabDailyTaskTimePeriodBs;
    @Bind(R.id.et_daily_task_wine)
    EditText mEtDailyTaskWine;
    @Bind(R.id.vp_daily_task_date)
    ViewPager mVpDailyTaskDate;
    @Bind(R.id.tv_daily_task_date)
    TextView mTvDailyTaskDate;
    @Bind(R.id.vp_daily_task_medicine)
    XViewPager mVpDailyTaskMedicine;
    @Bind(R.id.vp_daily_task_bp)
    XViewPager mVpDailyTaskBp;
    @Bind(R.id.vp_daily_task_bs)
    XViewPager mVpDailyTaskBs;
    @Bind(R.id.lc_daily_task_bp)
    LineChart mLineChartBp;

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

        mDailyTaskModel = new DailyTaskModel(this);
        addModel(mDailyTaskModel);

        initToolbar();
        initRxBusEvent();

        mDailyTaskModel.fetchData();
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

    @Override
    public void initBloodPressure(List<BloodPressureBean> beanList) {
        LineChart lineChart = new LineChart(this);

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
    }
}
