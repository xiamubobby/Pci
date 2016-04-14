package com.wonders.xlab.pci.doctor.mvp.entity.request;

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
        private int id;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
