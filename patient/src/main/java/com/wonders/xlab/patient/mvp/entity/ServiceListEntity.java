package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceListEntity extends BaseEntity {
    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private List<Content> content;
        private Boolean last;
        private Integer totalPages;
        private Integer totalElements;
        private Boolean first;
        private Integer numberOfElements;
        //String sort; //it is said that this matters not
        private Integer size;
        private Integer number;

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public Boolean getLast() {
            return last;
        }

        public void setLast(Boolean last) {
            this.last = last;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(Integer totalElements) {
            this.totalElements = totalElements;
        }

        public Boolean getFirst() {
            return first;
        }

        public void setFirst(Boolean first) {
            this.first = first;
        }

        public Integer getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public class Content {
            private Long id;
            private String title;
            private String description;
            private Float price;
            private String imageUrl;
            private String organizationName;
            private String organizationImageUrl;
            private String organizationStatus;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Float getPrice() {
                return price;
            }

            public void setPrice(Float price) {
                this.price = price;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getOrganizationName() {
                return organizationName;
            }

            public void setOrganizationName(String organizationName) {
                this.organizationName = organizationName;
            }

            public String getOrganizationImageUrl() {
                return organizationImageUrl;
            }

            public void setOrganizationImageUrl(String organizationImageUrl) {
                this.organizationImageUrl = organizationImageUrl;
            }

            public String getOrganizationStatus() {
                return organizationStatus;
            }

            public void setOrganizationStatus(String organizationStatus) {
                this.organizationStatus = organizationStatus;
            }
        }
    }
}
