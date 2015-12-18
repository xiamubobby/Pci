package com.wonders.xlab.pci.mvn.entity.task;

import com.wonders.xlab.pci.mvn.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomEntity extends BaseEntity {

    /**
     * title : title
     * content : ["content1"]
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String title;
        private List<String> content;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getContent() {
            return content;
        }
    }
}
