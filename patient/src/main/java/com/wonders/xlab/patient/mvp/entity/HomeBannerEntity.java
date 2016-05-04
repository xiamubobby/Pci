package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/5.
 */
public class HomeBannerEntity extends BaseEntity {

    /**
     * id : 1
     * title : 百度
     * imageUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * linkUrl : www.baidu.com
     * enable : true
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int id;
        private String title;
        private String imageUrl;
        private String linkUrl;
        private boolean enable;

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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
