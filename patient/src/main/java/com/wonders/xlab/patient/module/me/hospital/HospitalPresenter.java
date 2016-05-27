package com.wonders.xlab.patient.module.me.hospital;

import com.wonders.xlab.patient.module.me.hospital.adapter.HospitalBean;
import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;
import com.wonders.xlab.patient.mvp.entity.HospitalDicEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/5/27.
 */
public class HospitalPresenter extends BasePresenter implements HospitalContract.Presenter, HospitalContract.Callback {
    private HospitalContract.ViewListener mViewListener;

    private HospitalContract.Model mModel;

    @Inject
    public HospitalPresenter(HospitalContract.ViewListener viewListener, HospitalModel model) {
        mViewListener = viewListener;
        mModel = model;
        addModel(mModel);
    }

    @Override
    public void getAllHospitals() {
        mViewListener.showLoading("");
        mModel.getAllHospitals(this);
    }

    @Override
    public void onReceiveHospitalsSuccess(HospitalAllEntity entity) {
        Observable.from(entity.getRet_values().getContent())
                .flatMap(new Func1<HospitalDicEntity, Observable<HospitalBean>>() {
                    @Override
                    public Observable<HospitalBean> call(HospitalDicEntity hospitalDicEntity) {
                        HospitalBean bean = new HospitalBean();
                        bean.setId(hospitalDicEntity.getId());
                        bean.setName(hospitalDicEntity.getName());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<HospitalBean>() {
                    List<HospitalBean> hashMaps = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mViewListener.hideLoading();
                        mViewListener.showHospitalList(hashMaps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewListener.hideLoading();
                        mViewListener.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(HospitalBean bean) {
                        hashMaps.add(bean);
                    }
                });
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
