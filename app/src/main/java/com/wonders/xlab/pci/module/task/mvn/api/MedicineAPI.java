package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.MedicineEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/23.
 */
public interface MedicineAPI {
    @FormUrlEncoded
    @POST("userMedicineRecord/listUserMedicineRecord")
    Observable<MedicineEntity> getMedicines(@Field("userId") String userId);
}
