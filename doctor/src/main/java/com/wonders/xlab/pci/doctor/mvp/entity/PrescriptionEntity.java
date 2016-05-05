package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by jimmy on 16/5/5.
 */
public class PrescriptionEntity extends BaseEntity {


    /**
     * data : {"more":true,"more_params":{"flag":"1","order":""},"content":[{"medicine_names":["(自)碘甘油","(甲)甲硝唑片"],"recipe_id":"b728d106a5cc4f8b9ffbcf29a275681c","print_time":"2011-08-08","hospital_name":"上海市口腔病医院"}]}
     * msg : success
     * code : 0
     */

    private RetValuesBean ret_values;

    public RetValuesBean getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesBean ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesBean {
        /**
         * more : true
         * more_params : {"flag":"1","order":""}
         * content : [{"medicine_names":["(自)碘甘油","(甲)甲硝唑片"],"recipe_id":"b728d106a5cc4f8b9ffbcf29a275681c","print_time":"2011-08-08","hospital_name":"上海市口腔病医院"}]
         */

        private DataBean data;
        private String msg;
        private int code;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
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

        public static class DataBean {
            private boolean more;
            /**
             * flag : 1
             * order :
             */

            private MoreParamsBean more_params;
            /**
             * medicine_names : ["(自)碘甘油","(甲)甲硝唑片"]
             * recipe_id : b728d106a5cc4f8b9ffbcf29a275681c
             * print_time : 2011-08-08
             * hospital_name : 上海市口腔病医院
             */

            private List<ContentBean> content;

            public boolean isMore() {
                return more;
            }

            public void setMore(boolean more) {
                this.more = more;
            }

            public MoreParamsBean getMore_params() {
                return more_params;
            }

            public void setMore_params(MoreParamsBean more_params) {
                this.more_params = more_params;
            }

            public List<ContentBean> getContent() {
                return content;
            }

            public void setContent(List<ContentBean> content) {
                this.content = content;
            }

            public static class MoreParamsBean {
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

            public static class ContentBean {
                private String recipe_id;
                private String print_time;
                private String hospital_name;
                private List<String> medicine_names;

                public String getRecipe_id() {
                    return recipe_id;
                }

                public void setRecipe_id(String recipe_id) {
                    this.recipe_id = recipe_id;
                }

                public String getPrint_time() {
                    return print_time;
                }

                public void setPrint_time(String print_time) {
                    this.print_time = print_time;
                }

                public String getHospital_name() {
                    return hospital_name;
                }

                public void setHospital_name(String hospital_name) {
                    this.hospital_name = hospital_name;
                }

                public List<String> getMedicine_names() {
                    return medicine_names;
                }

                public void setMedicine_names(List<String> medicine_names) {
                    this.medicine_names = medicine_names;
                }
            }
        }
    }
}
