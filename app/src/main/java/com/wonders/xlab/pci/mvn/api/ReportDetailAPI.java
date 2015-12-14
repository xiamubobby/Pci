package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.entity.report.ReportDetailEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface ReportDetailAPI {
    @GET("get")
    Observable<ReportDetailEntity> getReports();
}
