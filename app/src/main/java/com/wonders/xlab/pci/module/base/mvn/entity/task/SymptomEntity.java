package com.wonders.xlab.pci.module.base.mvn.entity.task;

import com.wonders.xlab.pci.module.base.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomEntity extends BaseEntity {


    /**
     * name : 今天是否有以下症状
     * symptoms : [{"id":1,"name":"感冒"},{"id":2,"name":"发热"}]
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
        /**
         * id : 1
         * name : 感冒
         */

        private List<SymptomsEntity> symptoms;

        public void setName(String name) {
            this.name = name;
        }

        public void setSymptoms(List<SymptomsEntity> symptoms) {
            this.symptoms = symptoms;
        }

        public String getName() {
            return name;
        }

        public List<SymptomsEntity> getSymptoms() {
            return symptoms;
        }

        public static class SymptomsEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
