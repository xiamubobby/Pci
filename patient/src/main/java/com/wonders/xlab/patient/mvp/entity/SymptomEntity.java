package com.wonders.xlab.patient.mvp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomEntity extends BaseEntity {

    /**
     * symptomDtos : [{"name":"今天是否有疼痛或者不适部位","symptoms":[{"id":10,"name":"胸闷","symptomUrl":"","new":false},{"id":11,"name":"心前区疼痛","symptomUrl":"","new":false},{"id":12,"name":"后背疼痛","symptomUrl":"","new":false},{"id":13,"name":"颈部疼痛","symptomUrl":"","new":false},{"id":14,"name":"咽喉疼痛","symptomUrl":"","new":false},{"id":15,"name":"肩膀疼痛","symptomUrl":"","new":false},{"id":16,"name":"手臂疼痛","symptomUrl":"","new":false},{"id":17,"name":"胃疼","symptomUrl":"","new":false}]},{"name":"今天是否有以下症状","symptoms":[{"id":1,"name":"感冒","symptomUrl":"","new":false},{"id":2,"name":"发热","symptomUrl":"","new":false},{"id":3,"name":"腹胀","symptomUrl":"","new":false},{"id":4,"name":"腹泻","symptomUrl":"","new":false},{"id":5,"name":"腹痛","symptomUrl":"","new":false},{"id":6,"name":"口腔异味","symptomUrl":"","new":false},{"id":7,"name":"智齿","symptomUrl":"","new":false},{"id":8,"name":"牙龈肿痛","symptomUrl":"","new":false},{"id":9,"name":"牙龈出血","symptomUrl":"","new":false}]},{"name":"症状全无","symptoms":[{"id":27,"name":"以上症状全无","symptomUrl":"","new":false}]},{"name":"今天是否有心绞痛症状","symptoms":[{"id":18,"name":"1分钟以内，类似刺痛","symptomUrl":"","new":false},{"id":19,"name":"2-20分钟左右，类似刺痛","symptomUrl":"","new":false},{"id":20,"name":"半小时以上至数小时持续疼痛","symptomUrl":"","new":false}]}]
     * tips : 风险提示：本工具仅供记录院外不适症状，供医生复诊参考，不并不能作为急救工具使用。如有问题请及时就医
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String tips;
        /**
         * name : 今天是否有疼痛或者不适部位
         * symptoms : [{"id":10,"name":"胸闷","symptomUrl":"","new":false},{"id":11,"name":"心前区疼痛","symptomUrl":"","new":false},{"id":12,"name":"后背疼痛","symptomUrl":"","new":false},{"id":13,"name":"颈部疼痛","symptomUrl":"","new":false},{"id":14,"name":"咽喉疼痛","symptomUrl":"","new":false},{"id":15,"name":"肩膀疼痛","symptomUrl":"","new":false},{"id":16,"name":"手臂疼痛","symptomUrl":"","new":false},{"id":17,"name":"胃疼","symptomUrl":"","new":false}]
         */

        private List<SymptomDtoEntity> symptomDtos;

        public void setTips(String tips) {
            this.tips = tips;
        }

        public void setSymptomDtos(List<SymptomDtoEntity> symptomDtos) {
            this.symptomDtos = symptomDtos;
        }

        public String getTips() {
            return tips;
        }

        public List<SymptomDtoEntity> getSymptomDtos() {
            return symptomDtos;
        }

        public static class SymptomDtoEntity {
            private String name;
            /**
             * id : 10
             * name : 胸闷
             * symptomUrl :
             * new : false
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
                private String symptomUrl;
                @SerializedName("new")
                private boolean newX;

                public void setId(int id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setSymptomUrl(String symptomUrl) {
                    this.symptomUrl = symptomUrl;
                }

                public void setNewX(boolean newX) {
                    this.newX = newX;
                }

                public int getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getSymptomUrl() {
                    return symptomUrl;
                }

                public boolean isNewX() {
                    return newX;
                }
            }
        }
    }
}
