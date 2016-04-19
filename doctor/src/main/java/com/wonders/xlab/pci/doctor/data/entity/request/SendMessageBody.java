package com.wonders.xlab.pci.doctor.data.entity.request;

import java.util.List;
import java.util.Map;

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

    private String msg;
    private String from;
    private List<String> target;
    private Map<String,Object> ext;

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
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

    public String getFrom() {
        return from;
    }

    public List<String> getTarget() {
        return target;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
