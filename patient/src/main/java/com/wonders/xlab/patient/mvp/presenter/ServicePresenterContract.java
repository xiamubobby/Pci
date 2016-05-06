package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.alldoctor.adapter.AllDoctorItemBean;
import com.wonders.xlab.patient.module.service.ServiceListCellDataUnit;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServicePresenterContract {
    public interface ViewListener extends BasePagePresenterListener {
        void showAllServiceList(ArrayList<ServiceListCellDataUnit> list);

        void appendAllServiceList(ArrayList<ServiceListCellDataUnit> list);
    }

    public interface Actions extends IBasePresenter {
        void getAllServices(String serviceId, boolean isRefresh);
    }
}
