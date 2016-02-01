package com.wonders.xlab.pci.module.mydoctor.mvn;

/**
 * Created by hua on 16/1/28.
 */
public class DoctorInfoEntity {
    private String name;
    private long id;
    private String parentdepartname;
    private String hospitalname;
    private String title;
    private String pic;
    private String des;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setParentdepartname(String parentdepartname) {
        this.parentdepartname = parentdepartname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getParentdepartname() {
        return parentdepartname;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() {
        return pic;
    }

    public String getDes() {
        return des;
    }
}
