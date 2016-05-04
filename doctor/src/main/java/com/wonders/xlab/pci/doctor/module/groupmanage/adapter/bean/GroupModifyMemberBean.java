package com.wonders.xlab.pci.doctor.module.groupmanage.adapter.bean;

import android.os.Parcel;
import android.text.TextUtils;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyMemberBean extends GroupDoctorBean{
    /**
     * item type
     */
    public final static int TYPE_MEMBER = 0;//小组成员
    public final static int TYPE_ADD = 1;//加号
    public final static int TYPE_MINUS = 2;//减号

    private int type = TYPE_MEMBER;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }

        if (o instanceof GroupModifyMemberBean) {
            GroupModifyMemberBean bean = (GroupModifyMemberBean) o;

            if (bean.getType() == this.type && bean.getType() != TYPE_MEMBER) {
                return true;
            } else if (doctorId == null || TextUtils.isEmpty(doctorId.get())) {
                return false;
            }
            return this.doctorId.get().equals(bean.doctorId.get());
        }
        return false;
    }

    public void setDoctorBean(GroupDoctorBean bean) {
        doctorId.set(bean.doctorId.get());
        doctorImId.set(bean.doctorImId.get());
        doctorName.set(bean.doctorName.get());
        doctorAvatarUrl.set(bean.doctorAvatarUrl.get());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.type);
    }

    public GroupModifyMemberBean() {
    }

    protected GroupModifyMemberBean(Parcel in) {
        super(in);
        this.type = in.readInt();
    }

    public static final Creator<GroupModifyMemberBean> CREATOR = new Creator<GroupModifyMemberBean>() {
        @Override
        public GroupModifyMemberBean createFromParcel(Parcel source) {
            return new GroupModifyMemberBean(source);
        }

        @Override
        public GroupModifyMemberBean[] newArray(int size) {
            return new GroupModifyMemberBean[size];
        }
    };
}
