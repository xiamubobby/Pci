package com.wonders.xlab.patient.mvp.entity.common;

import java.util.List;

/**
 * Created by hua on 16/5/23.
 */
public class ChargeEntity {
    private String id;
    private String object;
    private int created;
    private boolean livemode;
    private boolean paid;
    private boolean refunded;
    private String app;
    private String channel;
    private String order_no;
    private String client_ip;
    private int amount;
    private int amount_settle;
    private String currency;
    private String subject;
    private String body;
    private ExtraEntity extra;
    private Object time_paid;
    private int time_expire;
    private Object time_settle;
    private Object transaction_no;
    /**
     * object : list
     * url : /v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds
     * has_more : false
     * data : []
     */

    private RefundsEntity refunds;
    private int amount_refunded;
    private Object failure_code;
    private Object failure_msg;
    private MetadataEntity metadata;
    /**
     * object : credential
     * upacp : {"tn":"201409161028470000000","mode":"01"}
     */

    private CredentialEntity credential;
    private Object description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_settle() {
        return amount_settle;
    }

    public void setAmount_settle(int amount_settle) {
        this.amount_settle = amount_settle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ExtraEntity getExtra() {
        return extra;
    }

    public void setExtra(ExtraEntity extra) {
        this.extra = extra;
    }

    public Object getTime_paid() {
        return time_paid;
    }

    public void setTime_paid(Object time_paid) {
        this.time_paid = time_paid;
    }

    public int getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(int time_expire) {
        this.time_expire = time_expire;
    }

    public Object getTime_settle() {
        return time_settle;
    }

    public void setTime_settle(Object time_settle) {
        this.time_settle = time_settle;
    }

    public Object getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(Object transaction_no) {
        this.transaction_no = transaction_no;
    }

    public RefundsEntity getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsEntity refunds) {
        this.refunds = refunds;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public Object getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(Object failure_code) {
        this.failure_code = failure_code;
    }

    public Object getFailure_msg() {
        return failure_msg;
    }

    public void setFailure_msg(Object failure_msg) {
        this.failure_msg = failure_msg;
    }

    public MetadataEntity getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataEntity metadata) {
        this.metadata = metadata;
    }

    public CredentialEntity getCredential() {
        return credential;
    }

    public void setCredential(CredentialEntity credential) {
        this.credential = credential;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public static class ExtraEntity {
    }

    public static class RefundsEntity {
        private String object;
        private String url;
        private boolean has_more;
        private List<?> data;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }

    public static class MetadataEntity {
    }

    public static class CredentialEntity {
        private String object;
        /**
         * tn : 201409161028470000000
         * mode : 01
         */

        private UpacpEntity upacp;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public UpacpEntity getUpacp() {
            return upacp;
        }

        public void setUpacp(UpacpEntity upacp) {
            this.upacp = upacp;
        }

        public static class UpacpEntity {
            private String tn;
            private String mode;

            public String getTn() {
                return tn;
            }

            public void setTn(String tn) {
                this.tn = tn;
            }

            public String getMode() {
                return mode;
            }

            public void setMode(String mode) {
                this.mode = mode;
            }
        }
    }
}
