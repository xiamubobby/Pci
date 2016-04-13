package com.wonders.xlab.pci.doctor.mvp.entity.request;

import java.util.List;

/**
 * Created by hua on 16/4/12.
 */
public class GroupCreateBody {

    /**
     * name : id为6的诊所
     * description : id为6的诊所阿萨德发生的发生的
     * doctors : [{"id":6,"imId":"doctor122131231"},{"id":7,"imId":"doctor122131231"}]
     */
    private String id;
    private String name;
    private String description;
    /**
     * id : 6
     * imId : doctor122131231
     */

    private List<DoctorsEntity> doctors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
