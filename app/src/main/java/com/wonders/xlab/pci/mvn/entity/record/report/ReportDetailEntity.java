package com.wonders.xlab.pci.mvn.entity.record.report;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailEntity extends BaseEntity {

    /**
     * content : [{"id":1,"createdDate":1450340950000,"lastModifiedDate":1450340950000,"userCases":[{"id":3,"createdDate":1450340950000,"lastModifiedDate":1450340950000,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","name":"name"}],"title":"快速的减肥链接"}]
     * last : true
     * totalPages : 1
     * totalElements : 2
     * size : 20
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 2
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
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
         * createdDate : 1450340950000
         * lastModifiedDate : 1450340950000
         * userCases : [{"id":3,"createdDate":1450340950000,"lastModifiedDate":1450340950000,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","name":"name"}]
         * title : 快速的减肥链接
         */

        private List<ContentEntity> content;

        public void setLast(boolean last) {
            this.last = last;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public boolean isLast() {
            return last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public int getSize() {
            return size;
        }

        public int getNumber() {
            return number;
        }

        public Object getSort() {
            return sort;
        }

        public boolean isFirst() {
            return first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public static class ContentEntity {
            private int id;
            private long createdDate;
            private long lastModifiedDate;
            private String title;
            /**
             * id : 3
             * createdDate : 1450340950000
             * lastModifiedDate : 1450340950000
             * caseUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png
             * name : name
             */

            private List<UserCasesEntity> userCases;

            public void setId(int id) {
                this.id = id;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public void setLastModifiedDate(long lastModifiedDate) {
                this.lastModifiedDate = lastModifiedDate;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUserCases(List<UserCasesEntity> userCases) {
                this.userCases = userCases;
            }

            public int getId() {
                return id;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public long getLastModifiedDate() {
                return lastModifiedDate;
            }

            public String getTitle() {
                return title;
            }

            public List<UserCasesEntity> getUserCases() {
                return userCases;
            }

            public static class UserCasesEntity {
                private int id;
                private long createdDate;
                private long lastModifiedDate;
                private String caseUrl;
                private String name;

                public void setId(int id) {
                    this.id = id;
                }

                public void setCreatedDate(long createdDate) {
                    this.createdDate = createdDate;
                }

                public void setLastModifiedDate(long lastModifiedDate) {
                    this.lastModifiedDate = lastModifiedDate;
                }

                public void setCaseUrl(String caseUrl) {
                    this.caseUrl = caseUrl;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public long getCreatedDate() {
                    return createdDate;
                }

                public long getLastModifiedDate() {
                    return lastModifiedDate;
                }

                public String getCaseUrl() {
                    return caseUrl;
                }

                public String getName() {
                    return name;
                }
            }
        }
    }
}
