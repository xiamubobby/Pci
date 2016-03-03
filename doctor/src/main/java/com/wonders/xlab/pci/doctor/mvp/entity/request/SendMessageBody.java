package com.wonders.xlab.pci.doctor.mvp.entity.request;

import java.util.List;

/**
 * Created by hua on 16/3/1.
 */
public class SendMessageBody {

    /**
     * target_type : chatgroups
     * target : ["166710012339552740"]
     * msg : {"type":"txt","msg":"测试看看"}
     * from : 13621673988
     */

    private String target_type;
    /**
     * type : txt
     * msg : 测试看看
     */

    private MsgEntity msg;
    private String from;
    private List<String> target;

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public void setMsg(MsgEntity msg) {
        this.msg = msg;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public String getTarget_type() {
        return target_type;
    }

    public MsgEntity getMsg() {
        return msg;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTarget() {
        return target;
    }

    public static class MsgEntity {
        private String type;
        private String msg;

        public void setType(String type) {
            this.type = type;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getType() {
            return type;
        }

        public String getMsg() {
            return msg;
        }
    }
}