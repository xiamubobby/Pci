package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.SendMessageEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.SendMessageBody;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/3/2.
 */
public interface SendMessageAPI {
    /* {
        "target_type" : "chatgroups",
            "target" : ["166710012339552740"],
        "msg" : {
        "type" : "txt",
                "msg" : "测试看看"
    },
        "from" : "13621673988"
    }*/
    @POST("v1/im/sendMessage/{time}")
    Observable<Response<SendMessageEntity>> sendMessage(@Body SendMessageBody body, @Path("time") long time);
}
