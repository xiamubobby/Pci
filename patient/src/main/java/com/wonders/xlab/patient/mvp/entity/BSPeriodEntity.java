package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/21.
 */
public class BSPeriodEntity extends BaseEntity {


    /**
     * currentTime : 早餐前
     * timeString : ["早餐前","早餐后","午餐前","午餐后","晚餐前","晚餐后","凌晨","随机"]
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int currentTime;
        private List<String> timeString;

        public int getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }

        public List<String> getTimeString() {
            return timeString;
        }

        public void setTimeString(List<String> timeString) {
            this.timeString = timeString;
        }
    }
}
