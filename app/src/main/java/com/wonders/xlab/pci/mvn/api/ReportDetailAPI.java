package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.entity.report.ReportDetailEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface ReportDetailAPI {
    @FormUrlEncoded
    @POST("userCaseReview/listUserCaseReviewRecords")
    Observable<ReportDetailEntity> getReports(@Field("userId") String userId,@Field("page") int page);
}
