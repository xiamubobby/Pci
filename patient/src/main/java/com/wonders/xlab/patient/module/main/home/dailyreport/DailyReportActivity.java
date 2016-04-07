package com.wonders.xlab.patient.module.main.home.dailyreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bp.BPAddActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bp.BPGuideActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bs.BSAddActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bs.BSGuideActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.symptom.SymptomActivity;
import com.wonders.xlab.patient.module.main.home.dailyreport.fragment.BPReportFragment;
import com.wonders.xlab.patient.module.main.home.dailyreport.fragment.BSReportFragment;
import com.wonders.xlab.patient.module.main.home.dailyreport.fragment.SymptomReportFragment;
import com.wonders.xlab.patient.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.otto.ShowMeasureChooseDialogOtto;
import com.wonders.xlab.patient.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 每日记录
 * 血压和血糖数据在每次记录，服务器返回成功后缓存在本地，不从服务器获取，不考虑跨设备需要看到其他设备记录的情况，这么做主要是为了界面的流畅以及省流量，
 * 因为这个界面进入的频率很高
 * 不适症状需要从服务器获取，因为其中有医生的建议和确认的状态
 */
public class DailyReportActivity extends AppbarActivity {
    public final static int SHOW_TAB_POSITION_BS = 0;
    public final static int SHOW_TAB_POSITION_BP = 1;
    public final static int SHOW_TAB_POSITION_SYMPTOM = 2;

    public final static String DEFAULT_SHOW_TAB_POSITION = "defaultShowTabPositiion";

    private int mDefaultShowTabPosition = 0;

    @Bind(R.id.view_pager_daily_record)
    ViewPager mViewPager;
    @Bind(R.id.tab_daily_record)
    CommonTabLayout mTab;

    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.daily_record_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "每日记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle data = intent.getExtras();
            if (null != data) {
                mDefaultShowTabPosition = data.getInt(DEFAULT_SHOW_TAB_POSITION, 0);
            }
        }

        OttoManager.register(this);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        //1
        if (null == mFragmentVPAdapter) {
            mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
            mFragmentVPAdapter.addFragment(BSReportFragment.newInstance());
            mFragmentVPAdapter.addFragment(BPReportFragment.newInstance());
            mFragmentVPAdapter.addFragment(SymptomReportFragment.newInstance());
        }
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mFragmentVPAdapter);

        //2
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("血糖", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("血压", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 2:
                    tabEntities.add(new TabEntity("不适症状", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
            }
        }
        mTab.setTabData(tabEntities);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(mDefaultShowTabPosition, true);
    }

    private void recordNewData(Class targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    /**
     * @param type 0:血压   1：血糖
     */
    private void showConfirmDialog(final int type) {
        LinearLayout customView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_daily_record_dialog_multi_check, null);

        RadioGroup radioGroup = (RadioGroup) customView.findViewById(R.id.rg_daily_record_dialog_item);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_item_daily_record_dialog_single_check_auto:
                        SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), true);
                        break;
                    case R.id.rb_item_daily_record_dialog_single_check_manual:
                        SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), false);
                        break;
                }
            }
        });
        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.cb_item_daily_record_dialog_default);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showLongToast("如果需要切换输入方式，只需要在：主界面->我->设置中取消默认设置即可");
                } else {
                    SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), true);
                }
                SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_measure_default), isChecked);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("请选择健康数据的测量方式")
                .setView(customView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean hasSetDefault = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_measure_default), false);
                        boolean useEquipment = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_use_equipment), true);
                        if (!hasSetDefault) {
                            SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), true);
                        } else {
                            if (useEquipment) {
                                //TODO umeng
                                MobclickAgent.onEvent(DailyReportActivity.this, UmengEventId.HOME_DAILY_RECORD_DEFAULT_MEASURE_EQUIPMENT);
                            } else {
                                //TODO umeng
                                MobclickAgent.onEvent(DailyReportActivity.this, UmengEventId.HOME_DAILY_RECORD_DEFAULT_MEASURE_MANUAL);
                            }
                        }
                        switch (type) {
                            case 0:
                                if (useEquipment) {
                                    recordNewData(BPGuideActivity.class);
                                } else {
                                    recordNewData(BPAddActivity.class);
                                }
                                break;
                            case 1:
                                if (useEquipment) {
                                    recordNewData(BSGuideActivity.class);
                                } else {
                                    recordNewData(BSAddActivity.class);
                                }
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPManager.get(DailyReportActivity.this).putBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), true);
                    }
                });
        builder.create().show();
    }

    @Subscribe
    public void showBP(BPSaveSuccessOtto otto) {
        changeShowPager(1);
    }

    @Subscribe
    public void showBS(BSSaveSuccessOtto otto) {
        changeShowPager(0);
    }

    @Subscribe
    public void showSymptom(SymptomSaveSuccessOtto otto) {
        changeShowPager(2);
    }

    @Subscribe
    public void showChooseDialog(ShowMeasureChooseDialogOtto otto) {
        boolean hasSetDefault = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_measure_default), false);
        boolean useEquipment = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_use_equipment), true);
        if (hasSetDefault) {
            if (useEquipment) {
                switch (otto.getMeasureType()) {
                    case ShowMeasureChooseDialogOtto.TYPE_BP:
                        recordNewData(BPGuideActivity.class);
                        break;
                    case ShowMeasureChooseDialogOtto.TYPE_BS:
                        recordNewData(BSGuideActivity.class);
                        break;
                }
            } else {
                switch (otto.getMeasureType()) {
                    case ShowMeasureChooseDialogOtto.TYPE_BP:
                        recordNewData(BPAddActivity.class);
                        break;
                    case ShowMeasureChooseDialogOtto.TYPE_BS:
                        recordNewData(BSAddActivity.class);
                        break;
                }
            }
        } else {
            showConfirmDialog(otto.getMeasureType());
        }
    }

    private void changeShowPager(int position) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(position, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean hasSetDefault = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_measure_default), false);

        boolean useEquipment = SPManager.get(DailyReportActivity.this).getBoolean(getString(R.string.setting_pref_key_use_equipment), true);

        switch (item.getItemId()) {
            case R.id.menu_daily_record_bp:
                //TODO umeng
                MobclickAgent.onEvent(this, UmengEventId.HOME_DAILY_RECORD_MENU_MEASURE_BP);

                if (hasSetDefault) {
                    if (useEquipment) {
                        recordNewData(BPGuideActivity.class);
                    } else {
                        recordNewData(BPAddActivity.class);
                    }
                } else {
                    showConfirmDialog(0);
                }
                break;
            case R.id.menu_daily_record_bs:
                //TODO umeng
                MobclickAgent.onEvent(this, UmengEventId.HOME_DAILY_RECORD_MENU_MEASURE_BS);
                if (hasSetDefault) {
                    if (useEquipment) {
                        recordNewData(BSGuideActivity.class);
                    } else {
                        recordNewData(BSAddActivity.class);
                    }
                } else {
                    showConfirmDialog(1);
                }
                break;
            case R.id.menu_daily_record_symptom:
                MobclickAgent.onEvent(this, UmengEventId.HOME_DAILY_RECORD_MENU_MEASURE_SYMPTOM);
                recordNewData(SymptomActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
