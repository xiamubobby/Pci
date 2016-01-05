package com.wonders.xlab.pci.assist.connection.entity;

import java.util.List;

/**
 * Created by hua on 16/1/5.
 */
public class BPEntityList {
    private List<BPEntity> mBPEntityList;

    public BPEntityList(List<BPEntity> BPEntityList) {
        mBPEntityList = BPEntityList;
    }

    public List<BPEntity> getBPEntityList() {
        return mBPEntityList;
    }

    public void setBPEntityList(List<BPEntity> BPEntityList) {
        mBPEntityList = BPEntityList;
    }
}
