package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchAllRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.MedicineSearchHistoryRVAdapter;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.di.DaggerMedicineSearchComponent;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.di.MedicineSearchModule;
import com.wonders.xlab.patient.util.CharacterParser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;

import static android.support.v7.widget.RecyclerView.OnScrollListener;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 如果需要接收选择的结果，通过注册otto监听{@link MedicineRealmBean}即可
 */
public class MedicineSearchActivity extends AppbarActivity implements MedicineSearchContract.ViewListener {

    @Bind(R.id.et_medicine_search_name)
    EditText mEtMedicineSearchName;
    @Bind(R.id.recycler_view_medicine_search_history)
    RecyclerView mRecyclerViewHistory;
    @Bind(R.id.recycler_view_medicine_search_all_medicine)
    CommonRecyclerView mRecyclerViewAllMedicine;
    @Bind(R.id.section_sidebar)
    EasyRecyclerViewSidebar mSectionSidebar;
    @Bind(R.id.tv_medicine_search_float_view)
    TextView mTvFloatView;
    @Bind(R.id.card_medicine_search_float_view)
    CardView mCardFloatView;

    private MedicineSearchAllRVAdapter mAllRVAdapter;
    private MedicineSearchHistoryRVAdapter mHistoryRVAdapter;

    private MedicineSearchContract.Presenter mPresenter;

    private int index;
    private boolean autoScrolling;

    @Override
    public int getContentLayout() {
        return R.layout.medicine_search_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerViewAllMedicine.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewHistory.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerViewHistory.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAllMedicine.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllMedicines();
            }
        });

        mPresenter = DaggerMedicineSearchComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicineSearchModule(new MedicineSearchModule(this))
                .build()
                .getMedicineSearchPresenter();

        mPresenter.getSearchHistory();
        mPresenter.getAllMedicines();
    }

    @OnClick(R.id.btn_medicine_search)
    public void search() {
        String name = mEtMedicineSearchName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mPresenter.getAllMedicines();
        } else {
            mPresenter.search(name);
        }
        KeyboardUtil.hide(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void showSoftInput(EditText inputText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputText.requestFocus();
            inputMethodManager.showSoftInput(inputText, 0);
        }
    }

    /**
     * 显示剂量输入对话框
     *
     * @param bean
     */
    private void showInputAlertDialog(final MedicineRealmBean bean) {
        final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(MedicineSearchActivity.this).inflate(R.layout.medicine_search_dialog, null, false);
        final EditText editText = (EditText) linearLayout.findViewById(R.id.et_medicine_search_dialog);
        final TextView textView = (TextView) linearLayout.findViewById(R.id.tv_medicine_search_dialog);
        textView.setText(bean.getFormOfDrug());
        final AlertDialog alertDialog = new AlertDialog.Builder(MedicineSearchActivity.this)
                .setTitle(bean.getMedicineName())
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        KeyboardUtil.hide(MedicineSearchActivity.this);
                    }
                })
                .setView(linearLayout).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dose = editText.getText().toString();
//                        if (!TextUtils.isEmpty(dose) ) {
//                            mPresenter.saveSearchHistory(bean);
//                            /**
//                             * post threw the otto event bus
//                             */
//                            bean.setDose(dose);
//                            OttoManager.post(bean);
//                            alertDialog.dismiss();
//                            KeyboardUtil.hide(MedicineSearchActivity.this);
//                            MedicineSearchActivity.this.finish();
//                        } else {
//                            showShortToast("请输入每次服用剂量");
//                        }
                        if (!TextUtils.isEmpty(dose)) {
                            bean.setDose(dose);
                        }

                        OttoManager.post(bean);
                        alertDialog.dismiss();
                        KeyboardUtil.hide(MedicineSearchActivity.this);
                        MedicineSearchActivity.this.finish();
                        showSoftInput(editText);
                    }
                });
            }
        });
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editText.requestFocus();
        alertDialog.show();
    }

    private void initMedicineSearchAllAdapter() {
        if (null == mAllRVAdapter) {
            mAllRVAdapter = new MedicineSearchAllRVAdapter();
            mAllRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(final int position) {
                    showInputAlertDialog(mAllRVAdapter.getBean(position));
                }
            });
            mRecyclerViewAllMedicine.setAdapter(mAllRVAdapter);
        }
    }

    @Override
    public void showMedicineList(List<MedicineRealmBean> beanList, String[] sections) {
        mSectionSidebar.setFloatView(mCardFloatView);
        mSectionSidebar.setSections(sections);
        mSectionSidebar.setOnTouchSectionListener(new EasyRecyclerViewSidebar.OnTouchSectionListener() {
            @Override
            public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) {

            }

            @Override
            public void onTouchLetterSection(int sectionIndex, EasySection letterSection) {
                autoScrolling = false;
                mTvFloatView.setText(letterSection.letter);
                goToPositionOfLetter(letterSection.letter);
            }
        });
        initMedicineSearchAllAdapter();
        mAllRVAdapter.setDatas(beanList);
    }


    public void goToPositionOfLetter(String letter) {
        index = 0;
        autoScrolling = true;
        for (int i = 0; i < mAllRVAdapter.getBeanList().size(); i++) {
            String firstChinese = mAllRVAdapter.getBeanList().get(i).getMedicineName().substring(0, 1);
            String letterTarget = CharacterParser.getInstance().getSelling(firstChinese).substring(0, 1).toUpperCase();
            if (letterTarget.equals(letter)) {
                index = i;
                break;
            }
        }
        mRecyclerViewAllMedicine.getRecyclerView().getLayoutManager().smoothScrollToPosition(mRecyclerViewAllMedicine.getRecyclerView(), null, index);

        mRecyclerViewAllMedicine.getRecyclerView().addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        if (autoScrolling) {
                            int curPosition = ((LinearLayoutManager) mRecyclerViewAllMedicine.getRecyclerView().getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                            if (curPosition != index) {
                                if (index < mAllRVAdapter.getBeanList().size() - 1) {
                                    index += 1;
                                    mRecyclerViewAllMedicine.getRecyclerView().getLayoutManager().smoothScrollToPosition(mRecyclerViewAllMedicine.getRecyclerView(), null, index);

                                }

                            }
                        }

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    public void appendMedicineList(List<MedicineRealmBean> beanList) {
        initMedicineSearchAllAdapter();
        mAllRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showSearchHistoryList(List<MedicineRealmBean> beanList) {
        if (null == mHistoryRVAdapter) {
            mHistoryRVAdapter = new MedicineSearchHistoryRVAdapter();
            mHistoryRVAdapter.setRemoveClickListener(new MedicineSearchHistoryRVAdapter.OnRemoveClickListener() {
                @Override
                public void onClick(String id) {
                    mPresenter.removeSearchHistoryById(id);
                }
            });
            mHistoryRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    showInputAlertDialog(mHistoryRVAdapter.getBean(position));
                }
            });
            mRecyclerViewHistory.setAdapter(mHistoryRVAdapter);
        }
        mHistoryRVAdapter.setDatas(beanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void hideLoading() {
        if (null == mRecyclerViewAllMedicine) {
            return;
        }
        mRecyclerViewAllMedicine.hideRefreshOrLoadMore(true, true);
    }
}
