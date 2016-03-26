package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.MedicineEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/23.
 */
public interface MedicineAPI {
    @FormUrlEncoded
    @POST("userMedicineRecord/listUserMedicineRecord")
    Observable<MedicineEntity> getMedicines(@Field("userId") String userId);
}
