package com.wonders.xlab.patient.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.wonders.xlab.patient.mvp.entity.AddressEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by WZH on 16/6/6.
 */
public class MyAddressUtil {
    private Context context;

    private AddressEntity addressEntity;

    private String frontAddress;

    public MyAddressUtil(Context context) {
        this.context = context;
        loadAddress();
    }

    private void loadAddress() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream io = assetManager.open("china.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(io));
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(new JsonReader(in)).getAsJsonObject();
            Gson gson = new Gson();
            addressEntity = gson.fromJson(o, AddressEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public String getFrontAddress(String addressCode) {
        frontAddress = "";

        if (!TextUtils.isEmpty(addressCode)) {
            int provinceCode = Integer.parseInt(addressCode.substring(0, 3));
            int cityCode = Integer.parseInt(addressCode.substring(3, 6));
            int areaCode = Integer.parseInt(addressCode.substring(6));
            String mProvince = "";
            String mCity = "";
            String mArea = "";
            List<AddressEntity.ProvinceEntity> provinces = addressEntity.getData();
            if (provinceCode < provinces.size() && provinceCode >= 0) {
                mProvince = provinces.get(provinceCode).getName();
                List<AddressEntity.ProvinceEntity.CityEntity> cities = provinces.get(provinceCode).getCity();
                if (cityCode < cities.size() && cityCode >= 0) {
                    mCity = cities.get(cityCode).getName();
                    List<String> areas = addressEntity.getData().get(provinceCode).getCity().get(cityCode).getArea();
                    if (areaCode < areas.size() && areaCode >= 0) {
                        mArea = areas.get(areaCode);
                    }
                }
            }

            frontAddress = mProvince + mCity + mArea;
        }

        return frontAddress;
    }
}
