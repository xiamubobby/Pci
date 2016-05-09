package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;
import com.wonders.xlab.patient.mvp.model.MedicineRemindListModel;
import com.wonders.xlab.patient.mvp.model.MedicineRemindListModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindPresenter extends BasePagePresenter implements MedicineRemindPresenterContract.Actions, MedicineRemindListModelContract.Callback {

    @Inject
    AIManager mAIManager;

    private MedicineRemindListModelContract.Actions mRemindListModel;
    private MedicineRemindPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindPresenter(MedicineRemindListModel remindListModel, MedicineRemindPresenterContract.ViewListener viewListener) {
        mRemindListModel = remindListModel;
        mViewListener = viewListener;
    }

    @Override
    public void getMedicineReminds(boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.showReachTheLastPageNotice("没有更多数据了");
            return;
        }
        mViewListener.showLoading("");
        mRemindListModel.getMedicineRemindList(mAIManager.getPatientId(), getNextPageIndex(), DEFAULT_PAGE_SIZE, this);
    }

    @Override
    public void onReceiveMedicineRemindListSuccess(MedicineRemindListEntity.RetValuesEntity<MedicineRemindListEntity.ContentEntity> valuesEntity) {
        //TODO 分页
        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());
        Observable.from(valuesEntity.getContent())
                .flatMap(new Func1<MedicineRemindListEntity.ContentEntity, Observable<MedicineRemindBean>>() {
                    @Override
                    public Observable<MedicineRemindBean> call(MedicineRemindListEntity.ContentEntity contentEntity) {
                        final MedicineRemindBean bean = new MedicineRemindBean();
                        bean.id.set(contentEntity.getId());
                        bean.amOrPmStr.set("AM");
                        bean.timeInStr.set(contentEntity.getRemindersTime());
                        bean.expiredDateInStr.set(DateUtil.format(contentEntity.getEndDate(),"yyyy-MM-dd"));
                        Observable.from(contentEntity.getMedicationUsages())
                                .map(new Func1<MedicineRemindListEntity.ContentEntity.MedicationUsagesEntity, String>() {
                                    @Override
                                    public String call(MedicineRemindListEntity.ContentEntity.MedicationUsagesEntity medicationUsagesEntity) {
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

                                    }

                                    @Override
                                    public void onNext(String s) {
                                        mStringBuilder.append(s).append("，");
                                    }
                                });
                        bean.isChecked.set(contentEntity.isExpire());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<MedicineRemindBean>() {
                    List<MedicineRemindBean> medicineRemindBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mViewListener.showMedicineRemind(medicineRemindBeanList);
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

    @Override
    public void onReceiveFailed(int code, String message) {

    }
}
