package com.wonders.xlab.patient.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by hua on 16/4/26.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PresenterScope {
}
