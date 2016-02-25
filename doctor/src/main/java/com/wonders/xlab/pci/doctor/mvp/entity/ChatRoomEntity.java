package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomEntity extends BaseEntity {

    /**
     * id : 1
     * fromWho : 111
     * content : 看看
     * type : User
     * groupId : 1
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private int id;
        private String fromWho;
        private String content;
        private String type;
        private int groupId;

        public void setId(int id) {
            this.id = id;
        }

        public void setFromWho(String fromWho) {
            this.fromWho = fromWho;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getId() {
            return id;
        }

        public String getFromWho() {
            return fromWho;
        }

        public String getContent() {
            return content;
        }

        public String getType() {
            return type;
        }

        public int getGroupId() {
            return groupId;
        }
    }
}
