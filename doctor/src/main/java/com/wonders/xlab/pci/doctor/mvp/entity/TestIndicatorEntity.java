package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by jimmy on 16/5/5.
 */
public class TestIndicatorEntity extends BaseEntity {

    /**
     * ret_code : 0
     * ret_values : {"data":{"more":true,"more_params":{"flag":"2","order":""},"content":[{"office_type":1,"hospital_name":"上海市第六人民医院临港新城医院","report_type":"内分泌代谢科","list":[{"item_name":"总25-羟基维生素D","item_value":"24.33","item_status":0,"refer_value":"20～32ng/mL"}],"date":"2015-10-30"}]},"msg":"success","code":0}
     * message : 成功
     */

    /**
     * data : {"more":true,"more_params":{"flag":"2","order":""},"content":[{"office_type":1,"hospital_name":"上海市第六人民医院临港新城医院","report_type":"内分泌代谢科","list":[{"item_name":"总25-羟基维生素D","item_value":"24.33","item_status":0,"refer_value":"20～32ng/mL"}],"date":"2015-10-30"}]}
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
         * more_params : {"flag":"2","order":""}
         * content : [{"office_type":1,"hospital_name":"上海市第六人民医院临港新城医院","report_type":"内分泌代谢科","list":[{"item_name":"总25-羟基维生素D","item_value":"24.33","item_status":0,"refer_value":"20～32ng/mL"}],"date":"2015-10-30"}]
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
             * flag : 2
             * order :
             */

            private MoreParamsBean more_params;
            /**
             * office_type : 1
             * hospital_name : 上海市第六人民医院临港新城医院
             * report_type : 内分泌代谢科
             * list : [{"item_name":"总25-羟基维生素D","item_value":"24.33","item_status":0,"refer_value":"20～32ng/mL"}]
             * date : 2015-10-30
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
                private int office_type;
                private String hospital_name;
                private String report_type;
                private String date;
                /**
                 * item_name : 总25-羟基维生素D
                 * item_value : 24.33
                 * item_status : 0
                 * refer_value : 20～32ng/mL
                 */

                private List<ListBean> list;

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

                public String getReport_type() {
                    return report_type;
                }

                public void setReport_type(String report_type) {
                    this.report_type = report_type;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    private String item_name;
                    private String item_value;
                    private int item_status;
                    private String refer_value;

                    public String getItem_name() {
                        return item_name;
                    }

                    public void setItem_name(String item_name) {
                        this.item_name = item_name;
                    }

                    public String getItem_value() {
                        return item_value;
                    }

                    public void setItem_value(String item_value) {
                        this.item_value = item_value;
                    }

                    public int getItem_status() {
                        return item_status;
                    }

                    public void setItem_status(int item_status) {
                        this.item_status = item_status;
                    }

                    public String getRefer_value() {
                        return refer_value;
                    }

                    public void setRefer_value(String refer_value) {
                        this.refer_value = refer_value;
                    }
                }
            }
        }
    }
}
