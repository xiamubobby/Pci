package com.wonders.xlab.patient.mvp.entity.request;

/**
 * Created by hua on 16/5/10.
 */
public class UserInfoBody {

    /**
     * address : 上海市黄浦区外马路974号
     * bracketNum : 2
     * lastOperationDate : 1462780939996
     * surgeon : 张医师
     * hospitalId : 1
     * caseHistory : 病史
     */

    private String addressCode;
    private String address;
    private int bracketNum;
    private long lastOperationDate;
    private String surgeon;
    private String hospitalId;
    private String caseHistory;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBracketNum() {
        return bracketNum;
    }

    public void setBracketNum(int bracketNum) {
        this.bracketNum = bracketNum;
    }

    public long getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(long lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }

    public String getSurgeon() {
        return surgeon;
    }

    public void setSurgeon(String surgeon) {
        this.surgeon = surgeon;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCaseHistory() {
        return caseHistory;
    }

    public void setCaseHistory(String caseHistory) {
        this.caseHistory = caseHistory;
    }
}
