package com.wonders.xlab.patient.module.me.otto;

/**
 * Created by WZH on 16/6/17.
 */
public class UserInfoUpdateOtto {
    private String sex;
    private String age;

    public UserInfoUpdateOtto(String sex, String age) {
        this.sex = sex;
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
