package com.wonders.xlab.pci.doctor.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiGroupInviteRealm extends RealmObject {
    private String groupId;
    private String groupName;
    private String groupDesc;
    private String ownerName;
    private String ownerHospital;
    private String ownerDepartment;
    private String ownerJobTitle;
    private RealmList<SimpleStringRealm> avatarUrls;
    private long recordTimeInMill;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerHospital() {
        return ownerHospital;
    }

    public void setOwnerHospital(String ownerHospital) {
        this.ownerHospital = ownerHospital;
    }

    public String getOwnerDepartment() {
        return ownerDepartment;
    }

    public void setOwnerDepartment(String ownerDepartment) {
        this.ownerDepartment = ownerDepartment;
    }

    public String getOwnerJobTitle() {
        return ownerJobTitle;
    }

    public void setOwnerJobTitle(String ownerJobTitle) {
        this.ownerJobTitle = ownerJobTitle;
    }

    public RealmList<SimpleStringRealm> getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(RealmList<SimpleStringRealm> avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }
}
