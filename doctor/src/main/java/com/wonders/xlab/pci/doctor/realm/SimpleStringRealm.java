package com.wonders.xlab.pci.doctor.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/18.
 */
public class SimpleStringRealm extends RealmObject{
    private String mString;

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }
}
