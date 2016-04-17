package com.wonders.xlab.pci.doctor.data.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageListEntity extends BaseEntity {

    /**
     * servicePackageId : 2
     * servicePackageName : 免费随访
     * servicePackageIconUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * value : 7天
     * colorString : #7d7d7d
     * published : true
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String servicePackageId;
        private String servicePackageName;
        private String servicePackageIconUrl;
        private String value;
        private String colorString;
        private boolean published;

        public String getServicePackageId() {
            return servicePackageId;
        }

        public void setServicePackageId(String servicePackageId) {
            this.servicePackageId = servicePackageId;
        }

        public String getServicePackageName() {
            return servicePackageName;
        }

        public void setServicePackageName(String servicePackageName) {
            this.servicePackageName = servicePackageName;
        }

        public String getServicePackageIconUrl() {
            return servicePackageIconUrl;
        }

        public void setServicePackageIconUrl(String servicePackageIconUrl) {
            this.servicePackageIconUrl = servicePackageIconUrl;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColorString() {
            return colorString;
        }

        public void setColorString(String colorString) {
            this.colorString = colorString;
        }

        public boolean isPublished() {
            return published;
        }

        public void setPublished(boolean published) {
            this.published = published;
        }
    }
}
