package com.wonders.xlab.patient.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/5/22.
 */
public class GenerateOrderPaymentEntity extends BaseEntity {

    /**
     * orderId : 1
     * charge : {"id":"ch_L8qn10mLmr1GS8e5OODmHaL4","object":"charge","created":1410834527,"livemode":true,"paid":false,"refunded":false,"app":"app_1Gqj58ynP0mHeX1q","channel":"upacp","order_no":"123456789","client_ip":"127.0.0.1","amount":100,"amount_settle":100,"currency":"cny","subject":"Your Subject","body":"Your Body","extra":{},"time_paid":null,"time_expire":1410838127,"time_settle":null,"transaction_no":null,"refunds":{"object":"list","url":"/v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds","has_more":false,"data":[]},"amount_refunded":0,"failure_code":null,"failure_msg":null,"metadata":{},"credential":{"object":"credential","upacp":{"tn":"201409161028470000000","mode":"01"}},"description":null}
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int orderId;
        /**
         * id : ch_L8qn10mLmr1GS8e5OODmHaL4
         * object : charge
         * created : 1410834527
         * livemode : true
         * paid : false
         * refunded : false
         * app : app_1Gqj58ynP0mHeX1q
         * channel : upacp
         * order_no : 123456789
         * client_ip : 127.0.0.1
         * amount : 100
         * amount_settle : 100
         * currency : cny
         * subject : Your Subject
         * body : Your Body
         * extra : {}
         * time_paid : null
         * time_expire : 1410838127
         * time_settle : null
         * transaction_no : null
         * refunds : {"object":"list","url":"/v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds","has_more":false,"data":[]}
         * amount_refunded : 0
         * failure_code : null
         * failure_msg : null
         * metadata : {}
         * credential : {"object":"credential","upacp":{"tn":"201409161028470000000","mode":"01"}}
         * description : null
         */

        private Object charge;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public Object getCharge() {
            return charge;
        }

        public void setCharge(Object charge) {
            this.charge = charge;
        }

    }
}
