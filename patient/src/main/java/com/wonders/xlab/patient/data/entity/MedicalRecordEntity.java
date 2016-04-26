package com.wonders.xlab.patient.data.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordEntity extends BaseEntity {

    /**
     * content : [{"id":11,"userCases":[{"id":74,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/400/interlace/1/q/10"},{"id":75,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/400/interlace/1/q/10"}],"title":"刘二","uploadTime":1459241220000},{"id":10,"userCases":[{"id":66,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/400/interlace/1/q/10"},{"id":67,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/400/interlace/1/q/10"},{"id":68,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173127.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173127.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173127.png?imageView2/0/w/400/interlace/1/q/10"},{"id":69,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170918.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170918.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170918.png?imageView2/0/w/400/interlace/1/q/10"},{"id":70,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170618.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170618.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-170618.png?imageView2/0/w/400/interlace/1/q/10"},{"id":71,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150057.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150057.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150057.png?imageView2/0/w/400/interlace/1/q/10"},{"id":72,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160320-000853.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160320-000853.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160320-000853.png?imageView2/0/w/400/interlace/1/q/10"},{"id":73,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150049.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150049.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160318-150049.png?imageView2/0/w/400/interlace/1/q/10"}],"title":"猜猜猜","uploadTime":1459233092000},{"id":9,"userCases":[{"id":65,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/400/interlace/1/q/10"}],"title":"测试","uploadTime":1459232442000},{"id":1,"userCases":[{"id":9,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg?imageView2/0/w/400/interlace/1/q/10"},{"id":4,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/400/interlace/1/q/10"},{"id":3,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/400/interlace/1/q/10"},{"id":1,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/2.pic.jpg","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/2.pic.jpg?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/2.pic.jpg?imageView2/0/w/400/interlace/1/q/10"}],"title":"快速的减肥链接","uploadTime":1452755675000},{"id":2,"userCases":[{"id":10,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png?imageView2/0/w/400/interlace/1/q/10"},{"id":8,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/JPEG_20151213_232904_525451009.jpg?imageView2/0/w/400/interlace/1/q/10"},{"id":5,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png?imageView2/0/w/400/interlace/1/q/10"},{"id":2,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-08-17-00-08.png?imageView2/0/w/400/interlace/1/q/10"}],"title":"dfdfd","uploadTime":1452755675000},{"id":3,"userCases":[{"id":11,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-13-09-05-14.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-13-09-05-14.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-13-09-05-14.png?imageView2/0/w/400/interlace/1/q/10"},{"id":7,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png?imageView2/0/w/400/interlace/1/q/10"},{"id":6,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-11-05-20-35-30.png?imageView2/0/w/400/interlace/1/q/10"}],"title":"快速的减肥链接","uploadTime":1452755675000}]
     * totalPages : 1
     * totalElements : 6
     * last : true
     * first : true
     * numberOfElements : 6
     * sort : null
     * size : 20
     * number : 0
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int totalPages;
        private int totalElements;
        private boolean last;
        private boolean first;
        private int numberOfElements;
        private Object sort;
        private int size;
        private int number;
        /**
         * id : 11
         * userCases : [{"id":74,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/400/interlace/1/q/10"},{"id":75,"caseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png","midCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/800/interlace/1/q/50","minCaseUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160323-173005.png?imageView2/0/w/400/interlace/1/q/10"}]
         * title : 刘二
         * uploadTime : 1459241220000
         */

        private List<ContentEntity> content;

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

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
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

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class ContentEntity {
            private int id;
            private String title;
            private long uploadTime;
            /**
             * id : 74
             * caseUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png
             * midCaseUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/800/interlace/1/q/50
             * minCaseUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_20160326-131343.png?imageView2/0/w/400/interlace/1/q/10
             */

            private List<UserCasesEntity> userCases;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getUploadTime() {
                return uploadTime;
            }

            public void setUploadTime(long uploadTime) {
                this.uploadTime = uploadTime;
            }

            public List<UserCasesEntity> getUserCases() {
                return userCases;
            }

            public void setUserCases(List<UserCasesEntity> userCases) {
                this.userCases = userCases;
            }

            public static class UserCasesEntity {
                private int id;
                private String caseUrl;
                private String midCaseUrl;
                private String minCaseUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCaseUrl() {
                    return caseUrl;
                }

                public void setCaseUrl(String caseUrl) {
                    this.caseUrl = caseUrl;
                }

                public String getMidCaseUrl() {
                    return midCaseUrl;
                }

                public void setMidCaseUrl(String midCaseUrl) {
                    this.midCaseUrl = midCaseUrl;
                }

                public String getMinCaseUrl() {
                    return minCaseUrl;
                }

                public void setMinCaseUrl(String minCaseUrl) {
                    this.minCaseUrl = minCaseUrl;
                }
            }
        }
    }
}
