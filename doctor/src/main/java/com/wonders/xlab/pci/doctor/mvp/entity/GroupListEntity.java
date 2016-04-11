package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupListEntity extends BaseEntity {

    /**
     * content : [{"id":1,"name":"XXX心血管小组","owner":{"id":5,"tel":"13621673988","name":"刘二","jobTitle":"副主任医师","type":"Attending","avatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","description":"医生简介","hospital":{"id":2,"name":"瑞金医院"},"department":{"id":1,"name":"儿科"},"imId":"doctor13621673988"},"multi":true,"servingPeople":0,"servedPeopleCount":0,"description":"小组简介","doctors":null,"createTime":1459997647000,"avatarUrls":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"size":1,"canGrant":false,"publish":false},{"id":10,"name":"id为6的诊所","owner":{"id":11,"tel":"17721013012","name":"小丸子","jobTitle":"助理医师","type":"AssistantNurse","avatarUrl":null,"description":null,"hospital":{"id":1,"name":"第一人民医院"},"department":{"id":1,"name":"儿科"},"imId":"doctor17721013012"},"multi":false,"servingPeople":0,"servedPeopleCount":0,"description":"id为6的诊所阿萨德发生的发生的","doctors":null,"createTime":1459997647000,"avatarUrls":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"size":3,"canGrant":false,"publish":false}]
     * last : true
     * totalPages : 1
     * totalElements : 2
     * size : 10
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 2
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private boolean last;
        private int totalPages;
        private int totalElements;
        private int size;
        private int number;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        /**
         * id : 1
         * name : XXX心血管小组
         * owner : {"id":5,"tel":"13621673988","name":"刘二","jobTitle":"副主任医师","type":"Attending","avatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","description":"医生简介","hospital":{"id":2,"name":"瑞金医院"},"department":{"id":1,"name":"儿科"},"imId":"doctor13621673988"}
         * multi : true
         * servingPeople : 0
         * servedPeopleCount : 0
         * description : 小组简介
         * doctors : null
         * createTime : 1459997647000
         * avatarUrls : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         * size : 1
         * canGrant : false
         * publish : false
         */

        private List<ContentEntity> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class ContentEntity {
            private String id;
            private String name;
            /**
             * id : 5
             * tel : 13621673988
             * name : 刘二
             * jobTitle : 副主任医师
             * type : Attending
             * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
             * description : 医生简介
             * hospital : {"id":2,"name":"瑞金医院"}
             * department : {"id":1,"name":"儿科"}
             * imId : doctor13621673988
             */

            private OwnerEntity owner;
            private boolean multi;
            private int servingPeople;
            private int servedPeopleCount;
            private String description;
            private Object doctors;
            private long createTime;
            private String size;
            private boolean canGrant;
            private boolean publish;
            private List<String> avatarUrls;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public OwnerEntity getOwner() {
                return owner;
            }

            public void setOwner(OwnerEntity owner) {
                this.owner = owner;
            }

            public boolean isMulti() {
                return multi;
            }

            public void setMulti(boolean multi) {
                this.multi = multi;
            }

            public int getServingPeople() {
                return servingPeople;
            }

            public void setServingPeople(int servingPeople) {
                this.servingPeople = servingPeople;
            }

            public int getServedPeopleCount() {
                return servedPeopleCount;
            }

            public void setServedPeopleCount(int servedPeopleCount) {
                this.servedPeopleCount = servedPeopleCount;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getDoctors() {
                return doctors;
            }

            public void setDoctors(Object doctors) {
                this.doctors = doctors;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public boolean isCanGrant() {
                return canGrant;
            }

            public void setCanGrant(boolean canGrant) {
                this.canGrant = canGrant;
            }

            public boolean isPublish() {
                return publish;
            }

            public void setPublish(boolean publish) {
                this.publish = publish;
            }

            public List<String> getAvatarUrls() {
                return avatarUrls;
            }

            public void setAvatarUrls(List<String> avatarUrls) {
                this.avatarUrls = avatarUrls;
            }

            public static class OwnerEntity {
                private String id;
                private String tel;
                private String name;
                private String jobTitle;
                private String type;
                private String avatarUrl;
                private String description;
                /**
                 * id : 2
                 * name : 瑞金医院
                 */

                private HospitalEntity hospital;
                /**
                 * id : 1
                 * name : 儿科
                 */

                private DepartmentEntity department;
                private String imId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public HospitalEntity getHospital() {
                    return hospital;
                }

                public void setHospital(HospitalEntity hospital) {
                    this.hospital = hospital;
                }

                public DepartmentEntity getDepartment() {
                    return department;
                }

                public void setDepartment(DepartmentEntity department) {
                    this.department = department;
                }

                public String getImId() {
                    return imId;
                }

                public void setImId(String imId) {
                    this.imId = imId;
                }

                public static class HospitalEntity {
                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class DepartmentEntity {
                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
