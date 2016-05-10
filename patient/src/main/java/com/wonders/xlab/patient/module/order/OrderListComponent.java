package com.wonders.xlab.patient.module.order;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.OrderListPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/9.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = OrderListModule.class)
public interface OrderListComponent {
    OrderListPresenter getOrderListPresenter();
}
