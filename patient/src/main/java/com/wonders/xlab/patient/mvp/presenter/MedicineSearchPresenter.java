package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.data.realm.MedicineSearchHistoryRealm;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.MedicineEntity;
import com.wonders.xlab.patient.mvp.entity.MedicineListEntity;
import com.wonders.xlab.patient.mvp.model.MedicineModel;
import com.wonders.xlab.patient.mvp.model.MedicineModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineSearchPresenter extends BasePagePresenter implements MedicineSearchPresenterContract.Actions, MedicineModelContract.Callback {
    private MedicineSearchPresenterContract.ViewListener mViewListener;
    private MedicineModelContract.Actions mSearchModel;

    @Inject
    Realm mRealm;

    @Inject
    public MedicineSearchPresenter(MedicineSearchPresenterContract.ViewListener viewListener, MedicineModel searchModel) {
        mViewListener = viewListener;
        mSearchModel = searchModel;
        addModel(mSearchModel);
    }

    @Override
    public void search(String medicineName) {
        mViewListener.showLoading("正在搜索");
        mSearchModel.search(medicineName, this);
    }

    @Override
    public void getAllMedicines() {
        mViewListener.showLoading("正在查询所有药品");
        mSearchModel.getAllMedicines(this);
    }

    @Override
    public void getSearchHistory() {
        RealmResults<MedicineSearchHistoryRealm> historyRealms = mRealm.allObjects(MedicineSearchHistoryRealm.class);

        Observable.from(historyRealms.subList(0, Math.min(2, historyRealms.size())))
                .flatMap(new Func1<MedicineSearchHistoryRealm, Observable<MedicineRealmBean>>() {
                    @Override
                    public Observable<MedicineRealmBean> call(MedicineSearchHistoryRealm realm) {
                        MedicineRealmBean bean = new MedicineRealmBean();
                        bean.setId(realm.getId());
                        bean.setCompanyName(realm.getCompanyName());
                        bean.setFormOfDrug(realm.getFormOfDrug());
                        bean.setDose(realm.getDose());
                        bean.setMedicineName(realm.getMedicineName());
                        bean.setSpecifications(realm.getSpecifications());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<MedicineRealmBean>() {
                    List<MedicineRealmBean> beanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mViewListener.showSearchHistoryList(beanList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(MedicineRealmBean bean) {
                        beanList.add(bean);
                    }
                });

    }

    @Override
    public void saveSearchHistory(MedicineRealmBean bean) {
        mRealm.beginTransaction();
        MedicineSearchHistoryRealm realm = mRealm.createObject(MedicineSearchHistoryRealm.class);
        realm.setId(bean.getId());
        realm.setFormOfDrug(bean.getFormOfDrug());
        realm.setDose(bean.getDose());
        realm.setMedicineName(bean.getMedicineName());
        realm.setCompanyName(bean.getCompanyName());
        realm.setSpecifications(bean.getSpecifications());
        mRealm.commitTransaction();
    }

    @Override
    public void removeSearchHistoryById(String id) {
        mRealm.beginTransaction();
        mRealm.allObjects(MedicineSearchHistoryRealm.class)
                .where()
                .equalTo("id", id)
                .findAll().clear();
        mRealm.commitTransaction();
    }

    @Override
    public void onReceiveMedicinesSuccess(MedicineListEntity entity) {
        Observable.from(entity.getRet_values())
                .flatMap(new Func1<MedicineEntity, Observable<MedicineRealmBean>>() {
                    @Override
                    public Observable<MedicineRealmBean> call(MedicineEntity medicineEntity) {
                        MedicineRealmBean bean = new MedicineRealmBean();
                        bean.setId(medicineEntity.getId());
                        bean.setCompanyName(medicineEntity.getCompany());
                        bean.setFormOfDrug(medicineEntity.getDosageForm());
                        bean.setMedicineName(medicineEntity.getName());
                        bean.setSpecifications(medicineEntity.getDoseSpecification());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<MedicineRealmBean>() {
                    List<MedicineRealmBean> beanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mViewListener.hideLoading();
                        mViewListener.showMedicineList(beanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(MedicineRealmBean medicineRealmBean) {
                        beanList.add(medicineRealmBean);
                    }
                });

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
