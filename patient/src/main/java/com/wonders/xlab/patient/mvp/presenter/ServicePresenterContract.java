package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.service.ServiceListCellDataUnit;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServicePresenterContract {
    public interface ViewListener extends BasePagePresenterListener {
        void showAllServiceList(List<ServiceListCellDataUnit> list);

        void appendAllServiceList(List<ServiceListCellDataUnit> list);
    }

    public interface Actions extends IBasePresenter {
        void getAllServices(boolean isRefresh);
    }
}
