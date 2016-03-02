package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.SendMessageEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.SendMessageBody;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
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
    @POST("im/sendChatContent/{time}")
    Observable<SendMessageEntity> sendMessage(@Body SendMessageBody body, @Path("time") long time);
}
