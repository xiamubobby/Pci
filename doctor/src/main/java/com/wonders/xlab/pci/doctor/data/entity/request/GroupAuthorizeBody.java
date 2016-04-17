package com.wonders.xlab.pci.doctor.data.entity.request;

import java.util.List;

/**
 * Created by hua on 16/4/14.
 */
public class GroupAuthorizeBody {

    /**
     * id : 5
     * type : Manager
     */

    private List<DtosEntity> dtos;

    public List<DtosEntity> getDtos() {
        return dtos;
    }

    public void setDtos(List<DtosEntity> dtos) {
        this.dtos = dtos;
    }

    public static class DtosEntity {
        private String doctorId;
        private String type;

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
