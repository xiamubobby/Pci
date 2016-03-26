package com.wonders.xlab.pci.module.record.report.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.report.ReportDetailEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface ReportDetailAPI {
    @FormUrlEncoded
    @POST("userCaseReview/listUserCaseReviewRecords")
    Observable<ReportDetailEntity> getReports(@Field("userId") String userId,@Field("page") int page,@Field("schedule") int schedule);
}
