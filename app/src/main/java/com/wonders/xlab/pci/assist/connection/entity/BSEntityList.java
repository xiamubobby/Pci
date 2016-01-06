package com.wonders.xlab.pci.assist.connection.entity;

import java.util.List;

/**
 * Created by hua on 16/1/5.
 */
public class BSEntityList {
    private List<BSEntity> bs;

    public BSEntityList(List<BSEntity> BSAAModelList) {
        bs = BSAAModelList;
    }

    public List<BSEntity> getBs() {
        return bs;
    }

    public void setBs(List<BSEntity> bs) {
        this.bs = bs;
    }
}
