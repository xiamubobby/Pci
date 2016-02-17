package com.wonders.xlab.pci.assist.deviceconnection.otto;

/**
 * Created by hua on 15/12/31.
 */
public class FindDeviceOtto {
    private String mDeviceName;
    private String mDeviceAddress;

    public FindDeviceOtto(String deviceName, String deviceAddress) {
        mDeviceName = deviceName;
        mDeviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public String getDeviceAddress() {
        return mDeviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        mDeviceAddress = deviceAddress;
    }
}
