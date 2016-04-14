package com.wonders.xlab.pci.doctor.mvp.entity.request;

import java.util.List;

/**
 * Created by hua on 16/4/12.
 * 包括邀请医生和移除医生
 */
public class GroupUpdateMemberBody {

    /**
     * name : id为6的诊所
     * description : id为6的诊所阿萨德发生的发生的
     * doctors : [{"id":6,"imId":"doctor122131231"},{"id":7,"imId":"doctor122131231"}]
     */
    private String id;
    /**
     * id : 6
     * imId : doctor122131231
     */

    private List<DoctorsEntity> doctors;

    public List<DoctorsEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorsEntity> doctors) {
        this.doctors = doctors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class DoctorsEntity {
        private String id;
        private String imId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImId() {
            return imId;
        }

        public void setImId(String imId) {
            this.imId = imId;
        }
    }
}
