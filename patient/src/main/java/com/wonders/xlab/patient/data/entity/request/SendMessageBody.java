package com.wonders.xlab.patient.data.entity.request;

import java.util.List;
import java.util.Map;

/**
 * Created by hua on 16/3/1.
 */
public class SendMessageBody {

    /**
     * targetType : chatgroups
     * targets : ["166710012339552740"]
     * msg : {"type":"txt","msg":"测试看看"}
     * from : 13621673988
     */

    private String targetType;
    /**
     * type : txt
     * msg : 测试看看
     */

    private String msg;
    private String from;
    private List<String> targets;
    private Map<String,Object> ext;

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTargets() {
        return targets;
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
}
