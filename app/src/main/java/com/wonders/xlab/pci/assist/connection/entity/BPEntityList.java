package com.wonders.xlab.pci.assist.connection.entity;

import java.util.List;

/**
 * Created by hua on 16/1/5.
 */
public class BPEntityList {
    private List<BPEntity> bp;

    public BPEntityList() {
    }

    public BPEntityList(List<BPEntity> BPModelList) {
        bp = BPModelList;
    }

    public List<BPEntity> getBp() {
        return bp;
    }

    public void setBp(List<BPEntity> bp) {
        this.bp = bp;
    }
}
