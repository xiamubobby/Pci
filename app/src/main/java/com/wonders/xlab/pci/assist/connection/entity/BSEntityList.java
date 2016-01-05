package com.wonders.xlab.pci.assist.connection.entity;

import java.util.List;

/**
 * Created by hua on 16/1/5.
 */
public class BSEntityList {
    private List<BSEntity> mBSEntityList;

    public BSEntityList(List<BSEntity> BSEntityList) {
        mBSEntityList = BSEntityList;
    }

    public List<BSEntity> getBSEntityList() {
        return mBSEntityList;
    }

    public void setBSEntityList(List<BSEntity> BSEntityList) {
        mBSEntityList = BSEntityList;
    }
}
