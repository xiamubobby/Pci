package com.wonders.xlab.patient.module.medicineremind.list;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;
import com.wonders.xlab.patient.mvp.entity.MedicationUsagesEntity;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;
import com.wonders.xlab.patient.mvp.model.MedicineStateModifyModel;
import com.wonders.xlab.patient.mvp.model.MedicineStateModifyModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 */
public class MedicineRemindPresenter extends BasePagePresenter implements MedicineRemindPresenterContract.Actions, MedicineRemindListModelContract.Callback, MedicineStateModifyModelContract.Callback {

    @Inject
    AIManager mAIManager;

    private MedicineRemindListModelContract.Actions mRemindListModel;
    private MedicineStateModifyModelContract.Actions mStateModifyModel;
    private MedicineRemindPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindPresenter(MedicineRemindPresenterContract.ViewListener viewListener, MedicineRemindListModel remindListModel, MedicineStateModifyModel stateModifyModel) {
        mViewListener = viewListener;
        mRemindListModel = remindListModel;
        mStateModifyModel = stateModifyModel;
        addModel(mRemindListModel);
        addModel(mStateModifyModel);
    }

    @Override
    public void getMedicineReminds(boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.hideLoading();
            mViewListener.showReachTheLastPageNotice(Constant.Message.NO_MORE_DATA);
            return;
        }
        mViewListener.showLoading("");
        mRemindListModel.getMedicineRemindList(mAIManager.getPatientId(), getNextPageIndex(), DEFAULT_PAGE_SIZE, this);
    }

    @Override
    public void changeRemindState(String remindersRecordId, boolean manualCloseReminder) {
        mStateModifyModel.changeRemindState(remindersRecordId, manualCloseReminder, this);
    }

    @Override
    public void onReceiveMedicineRemindListSuccess(final MedicineRemindListEntity.RetValuesEntity<MedicineRemindListEntity.ContentEntity> valuesEntity) {
        mViewListener.hideLoading();
        //TODO 分页
        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());
        Observable.from(valuesEntity.getContent())
                .flatMap(new Func1<MedicineRemindListEntity.ContentEntity, Observable<MedicineRemindBean>>() {
                    @Override
                    public Observable<MedicineRemindBean> call(MedicineRemindListEntity.ContentEntity contentEntity) {
                        final MedicineRemindBean bean = new MedicineRemindBean();
                        bean.id.set(contentEntity.getId());
                        String amOrPm = distinctAMOrPMFromTimeStr(contentEntity.getRemindersTime());
                        bean.amOrPmStr.set(amOrPm);
                        bean.timeInStr.set(DateUtil.format(DateUtil.parse(contentEntity.getRemindersTime(), "HH:mm"), "hh:mm"));
                        Long endDate = contentEntity.getEndDate();
                        bean.expiredDateInStr.set(null == endDate ? "长期" : DateUtil.format(endDate, "yyyy-MM-dd"));
                        Observable.from(contentEntity.getMedicationUsages())
                                .map(new Func1<MedicationUsagesEntity, String>() {
                                    @Override
                                    public String call(MedicationUsagesEntity medicationUsagesEntity) {
                                        return medicationUsagesEntity.getMedicationName();
                                    }
                                })
                                .subscribe(new Subscriber<String>() {
                                    StringBuilder mStringBuilder = new StringBuilder();

                                    @Override
                                    public void onCompleted() {
                                        bean.medicineNameStr.set(mStringBuilder.toString().substring(0, mStringBuilder.length() - 1));
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mViewListener.showToast(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(String s) {
                                        mStringBuilder.append(s).append("，");
                                    }
                                });
                        bean.shouldAlarm.set(!contentEntity.isManualCloseReminder());
                        bean.isExpired.set(contentEntity.isExpire());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<MedicineRemindBean>() {
                    List<MedicineRemindBean> medicineRemindBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {

                        if (shouldAppend()) {
                            if (medicineRemindBeanList.size() == 0) {
                                mViewListener.showReachTheLastPageNotice(Constant.Message.NO_MORE_DATA);
                                return;
                            }
                            mViewListener.appendMedicineRemind(medicineRemindBeanList);
                        } else {
                            mViewListener.showMedicineRemind(medicineRemindBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicineRemindBean medicineRemindBean) {
                        medicineRemindBeanList.add(medicineRemindBean);
                    }
                });

    }

    @NonNull
    private String distinctAMOrPMFromTimeStr(String time) {
        String remindHour = time.split(":")[0];
        String amOrPm = "上午";
        if (TextUtils.isDigitsOnly(remindHour) && Integer.parseInt(remindHour) >= 12) {
            amOrPm = "下午";
        }
        return amOrPm;
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }

    @Override
    public void onChangeRemindStateSuccess(String message) {
        mViewListener.changeRemindStateSuccess(message);
    }
}
