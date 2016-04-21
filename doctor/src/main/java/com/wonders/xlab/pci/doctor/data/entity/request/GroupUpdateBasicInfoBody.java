package com.wonders.xlab.pci.doctor.data.entity.request;

/**
 * Created by hua on 16/4/12.
 */
public class GroupUpdateBasicInfoBody {

    /**
     * description : id为6的诊所阿萨德发生的发生的
     * doctors : [{"id":6,"imId":"doctor122131231"},{"id":7,"imId":"doctor122131231"}]
     */
    private Owner owner;
    private String name;
    private String description;

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public static class Owner {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
