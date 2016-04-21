package com.wonders.xlab.pci.doctor.data.entity.request;

import java.util.List;

/**
 * Created by hua on 16/4/12.
 * 包括邀请医生和移除医生
 */
public class GroupUpdateMemberBody {

    /**
     * id : 6
     * imId : doctor122131231
     */

    private Owner owner;
    private List<DoctorsEntity> doctors;

    public List<DoctorsEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorsEntity> doctors) {
        this.doctors = doctors;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }


    public static class DoctorsEntity {
        private String id;
        private String imId;
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Owner{
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
