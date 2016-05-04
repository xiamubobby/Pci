package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/5/4.
 */
public class MedicalRecordsEntity extends BaseEntity {

    /**
     * data : {"more":true,"more_params":{"flag":"1","order":""},"content":[{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压病","date":"2016-04-29"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压","date":"2016-03-31"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高脂血症","date":"2016-03-17"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高脂血症","date":"2016-03-13"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压病","date":"2016-02-17"},{"office_name":"口腔预防科","office_type":1,"hospital_name":"上海市口腔病医院","diagnose_result":"牙周炎","date":"2011-08-08"}]}
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
         * content : [{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压病","date":"2016-04-29"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压","date":"2016-03-31"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高脂血症","date":"2016-03-17"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高脂血症","date":"2016-03-13"},{"office_name":"华坪全科","office_type":1,"hospital_name":"上海市闵行区江川社区卫生服务中心","diagnose_result":"高血压病","date":"2016-02-17"},{"office_name":"口腔预防科","office_type":1,"hospital_name":"上海市口腔病医院","diagnose_result":"牙周炎","date":"2011-08-08"}]
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
             * office_name : 华坪全科
             * office_type : 1
             * hospital_name : 上海市闵行区江川社区卫生服务中心
             * diagnose_result : 高血压病
             * date : 2016-04-29
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
                private int flag;
                private String order;

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
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
                private int office_type;
                private String hospital_name;
                private String diagnose_result;
                private String date;

                public String getOffice_name() {
                    return office_name;
                }

                public void setOffice_name(String office_name) {
                    this.office_name = office_name;
                }

                public int getOffice_type() {
                    return office_type;
                }

                public void setOffice_type(int office_type) {
                    this.office_type = office_type;
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

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }
        }
    }
}
