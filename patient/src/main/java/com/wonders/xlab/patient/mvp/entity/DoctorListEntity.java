package com.wonders.xlab.patient.mvp.entity;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by xiamubobby on 16/7/5.
 */

public class DoctorListEntity extends BaseEntity {

    private DoctorPatientRelationDoctorList ret_values;

    public DoctorPatientRelationDoctorList getRet_values() {
        return ret_values;
    }

    public void setRet_values(DoctorPatientRelationDoctorList ret_values) {
        this.ret_values = ret_values;
    }

    public class DoctorPatientRelationDoctorList {

        private List<DoctorPatientRelationDoctor> doctorPatientRelationDoctorList;

        public List<DoctorPatientRelationDoctor> getDoctorPatientRelationDoctorList() {
            return doctorPatientRelationDoctorList;
        }

        public void setDoctorPatientRelationDoctorList(List<DoctorPatientRelationDoctor> doctorPatientRelationDoctorList) {
            this.doctorPatientRelationDoctorList = doctorPatientRelationDoctorList;
        }

        public class DoctorPatientRelationDoctor {

            /**
             * 医生id
             */
            private long doctorId;

            /**
             * 医生名
             */
            private String name;

            /**
             * 医生职称
             */
            private String jobTitle;

            /**
             * 医生头像
             */
            private String avatarUrl;

            /**
             * 医生所在医院名
             */
            private String hospital;

            /**
             * 医生所在科室名
             */
            private String department;

            public long getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(long doctorId) {
                this.doctorId = doctorId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getJobTitle() {
                return jobTitle;
            }

            public void setJobTitle(String jobTitle) {
                this.jobTitle = jobTitle;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getHospital() {
                return hospital;
            }

            public void setHospital(String hospital) {
                this.hospital = hospital;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

        }

    }
}
