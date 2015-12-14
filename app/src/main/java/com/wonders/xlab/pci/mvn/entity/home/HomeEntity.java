package com.wonders.xlab.pci.mvn.entity.home;

import com.wonders.xlab.pci.mvn.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class HomeEntity extends BaseEntity {

    /**
     * name : name
     * portrait : 1
     * type : 0
     * title : 1
     * content : 1
     * updatetime : 10000
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String name;
        private String portrait;
        private String type;
        private String title;
        private String content;
        private String updatetime;

        public void setName(String name) {
            this.name = name;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getName() {
            return name;
        }

        public String getPortrait() {
            return portrait;
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getUpdatetime() {
            return updatetime;
        }
    }
}
