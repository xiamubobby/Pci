package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by jimmy on 16/5/5.
 */
public class SurgicalHistoryEntity extends BaseEntity {

    /**
     * data : {"more":true,"more_params":{"flag":"1","order":""},"content":[{"office_name":"内分泌代谢科","hospitalized_time":"2015-10-29~2015-11-05","doctor_suggest":"","hospital_name":"上海市第六人民医院临港新城医院","diagnose_result":""}]}
     * msg : success
     * code : 0
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        /**
         * more : true
         * more_params : {"flag":"1","order":""}
         * content : [{"office_name":"内分泌代谢科","hospitalized_time":"2015-10-29~2015-11-05","doctor_suggest":"","hospital_name":"上海市第六人民医院临港新城医院","diagnose_result":""}]
         */

        private DataEntity data;
        private String msg;
        private int code;

        public DataEntity getData() {
            return data;
        }

        public void setData(DataEntity data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static class DataEntity {
            private boolean more;
            /**
             * flag : 1
             * order :
             */

            private MoreParamsEntity more_params;
            /**
             * office_name : 内分泌代谢科
             * hospitalized_time : 2015-10-29~2015-11-05
             * doctor_suggest :
             * hospital_name : 上海市第六人民医院临港新城医院
             * diagnose_result :
             */

            private List<ContentEntity> content;

            public boolean isMore() {
                return more;
            }

            public void setMore(boolean more) {
                this.more = more;
            }

            public MoreParamsEntity getMore_params() {
                return more_params;
            }

            public void setMore_params(MoreParamsEntity more_params) {
                this.more_params = more_params;
            }

            public List<ContentEntity> getContent() {
                return content;
            }

            public void setContent(List<ContentEntity> content) {
                this.content = content;
            }

            public static class MoreParamsEntity {
                private String flag;
                private String order;

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public String getOrder() {
                    return order;
                }

                public void setOrder(String order) {
                    this.order = order;
                }
            }

            public static class ContentEntity {
                private String office_name;
                private String hospitalized_time;
                private String doctor_suggest;
                private String hospital_name;
                private String diagnose_result;

                public String getOffice_name() {
                    return office_name;
                }

                public void setOffice_name(String office_name) {
                    this.office_name = office_name;
                }

                public String getHospitalized_time() {
                    return hospitalized_time;
                }

                public void setHospitalized_time(String hospitalized_time) {
                    this.hospitalized_time = hospitalized_time;
                }

                public String getDoctor_suggest() {
                    return doctor_suggest;
                }

                public void setDoctor_suggest(String doctor_suggest) {
                    this.doctor_suggest = doctor_suggest;
                }

                public String getHospital_name() {
                    return hospital_name;
                }

                public void setHospital_name(String hospital_name) {
                    this.hospital_name = hospital_name;
                }

                public String getDiagnose_result() {
                    return diagnose_result;
                }

                public void setDiagnose_result(String diagnose_result) {
                    this.diagnose_result = diagnose_result;
                }
            }
        }
    }
}
