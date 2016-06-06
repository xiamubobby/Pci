package com.wonders.xlab.patient.module.me.address;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.me.address.di.AddressModule;
import com.wonders.xlab.patient.module.me.address.di.DaggerAddressComponent;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppbarActivity implements AddressContract.ViewListener {

    public final static String ADDRESS_ID = "id";
    public final static String ADDRESS = "address";
    public final static String ADDRESS_RESULT = "result";

    @Bind(R.id.tv_address_province)
    TextView mTextProvince;

    @Bind(R.id.tv_address_city)
    TextView mTextCity;

    @Bind(R.id.tv_address_area)
    TextView mTextArea;

    @Bind(R.id.et_address_detail)
    EditText mEtTextDetail;

    private String mProvince;
    private String mCity;
    private String mArea;
    private String mDetail;

    private String addressCode;
    private String address;
    private int provinceCode = -1;
    private int cityCode = -1;
    private int areaCode = -1;
    private String selectedProvince, selectedCity, selectedArea;
    private AddressEntity addressEntity;
    private AddressContract.Presenter mAddressPresenter;
    private Dialog addressDialog;
    private AddressAdapter addressAdapter;
    private int selectedPosition;
    private String selectedStr;

    @Override
    public int getContentLayout() {
        return R.layout.address_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null == intent) {
            throw new IllegalArgumentException("you must set the intent");
        }
        Bundle data = intent.getExtras();
        if (null != data) {
            addressCode = data.getString(ADDRESS_ID, "");
            address = data.getString(ADDRESS, "");
            addressCode = "123123123";
        }
        loadAddress();
        showAddress();
        initSelectDialog();
        mAddressPresenter = DaggerAddressComponent.builder()
                .applicationComponent(XApplication.getComponent())
                .addressModule(new AddressModule(this))
                .build()
                .getAddressPresenter();

        addPresenter(mAddressPresenter);

    }

    private void loadAddress() {
        try {
            AssetManager assetManager = this.getAssets();
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

    private void showAddress() {
        if (!addressCode.equals("")) {
            provinceCode = Integer.parseInt(addressCode.substring(0, 3));
            cityCode = Integer.parseInt(addressCode.substring(3, 6));
            areaCode = Integer.parseInt(addressCode.substring(6));
            List<AddressEntity.ProvinceEntity> provinces = addressEntity.getData();
            Log.e("SSS","...................................................................................."+provinces.size());
            if (provinceCode < provinces.size() && provinceCode >= 0) {
                mProvince = provinces.get(provinceCode).getName();
                mTextProvince.setText(mProvince);
                List<AddressEntity.ProvinceEntity.CityEntity> cities = addressEntity.getData().get(provinceCode).getCity();
                Log.e("SSS","...................................................................................."+cities.size());
                if (cityCode < cities.size() && cityCode >= 0) {
                    mCity = cities.get(cityCode).getName();
                    mTextCity.setText(mCity);
                    List<String> areas = addressEntity.getData().get(provinceCode).getCity().get(cityCode).getArea();
                    Log.e("SSS","...................................................................................."+areas.size());
                    if (areaCode < areas.size() && areaCode >= 0) {
                        mArea = areas.get(areaCode);
                        mTextArea.setText(mArea);
                    }
                }
            }


        }
        if (!address.equals("")) {
            mEtTextDetail.setText(address);
        }
    }

    @OnClick(R.id.tv_address_province)
    public void selectProvince() {
        showSelectDialog(0);
    }

    @OnClick(R.id.tv_address_city)
    public void selectCity() {
        if (provinceCode < 0) {
            showShortToast("请选择省份");
            return;
        }
        showSelectDialog(1);
    }

    @OnClick(R.id.tv_address_area)
    public void selectArea() {
        if (cityCode < 0) {
            showShortToast("请选择城市");
            return;
        }
        showSelectDialog(2);
    }

    private void initSelectDialog() {
        LinearLayout customView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_address_select, null);
        RecyclerView recyclerView = (RecyclerView) customView.findViewById(R.id.address_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter();
        recyclerView.setAdapter(addressAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("请选择")
                .setView(customView);
        addressDialog = builder.create();

    }

    private void showSelectDialog(final int type) {
        selectedStr = "";
        List<String> data = new ArrayList<>();
        if (type == 0) {
            for (AddressEntity.ProvinceEntity province : addressEntity.getData()) {
                data.add(province.getName());
            }
        } else if (type == 1) {
            for (AddressEntity.ProvinceEntity.CityEntity city : addressEntity.getData().get(provinceCode).getCity()) {
                data.add(city.getName());
            }
        } else if (type == 2) {
            for (String area : addressEntity.getData().get(provinceCode).getCity().get(cityCode).getArea()) {
                data.add(area);
            }
        }
        addressAdapter.setData(data);
        addressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (type == 0) {
                    provinceCode = selectedPosition;
                    selectedProvince = selectedStr;
                    mTextProvince.setText(selectedProvince);
                } else if (type == 1) {
                    cityCode = selectedPosition;
                    selectedCity = selectedStr;
                    mTextCity.setText(selectedCity);
                } else if (type == 2) {
                    areaCode = selectedPosition;
                    selectedArea = selectedStr;
                    mTextArea.setText(selectedArea);
                }
            }
        });
        if (!addressDialog.isShowing()) {
            addressDialog.show();
        }
    }


    public class AddressCell extends RecyclerView.ViewHolder {

        private TextView name;

        public AddressCell(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_spinner);
        }
    }

    public class AddressAdapter extends RecyclerView.Adapter<AddressCell> {


        private List<String> data;

        public void setData(List<String> data) {
            if (data == null) {
                return;
            }
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public AddressCell onCreateViewHolder(ViewGroup parent, int viewType) {
            View addressCell = getLayoutInflater().inflate(R.layout.item_spinner_text, parent, false);
            return new AddressCell(addressCell);
        }

        @Override
        public void onBindViewHolder(AddressCell holder, final int position) {

            final String name = data.get(position);
            if (!TextUtils.isEmpty(name)) {
                holder.name.setText(name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressDialog.dismiss();
                        selectedPosition = position;
                        selectedStr = name;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                mProvince = mTextProvince.getText().toString();
                mCity = mTextCity.getText().toString();
                mArea = mTextArea.getText().toString();
                mDetail = mEtTextDetail.getText().toString();
                if (TextUtils.isEmpty(mProvince)) {
                    showShortToast("请选择省份");
                } else {
                    addressCode = changeCode(provinceCode);
                    if (TextUtils.isEmpty(mCity)) {
                        showShortToast("请选择城市");
                    } else {
                        addressCode += changeCode(cityCode);
                        if (TextUtils.isEmpty(mArea)) {
                            showShortToast("请选择地区");
                        } else {
                            addressCode += changeCode(areaCode);
                            if (TextUtils.isEmpty(mDetail)) {
                                showShortToast("请填写详细地址");
                            } else {
                                address = mDetail;
                                UserInfoBody body = new UserInfoBody();
                                body.setAddress(address);
                                body.setAddressCode(addressCode);
                                showShortToast("....." + address + ",,,,,," + addressCode);
                                mAddressPresenter.saveAddress(body);
                            }
                        }
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String changeCode(Integer code) {
        String newCode = "";
        if (code.toString().length() == 1) {
            newCode = "00" + provinceCode;
        } else if (code.toString().length() == 2) {
            newCode = "0" + provinceCode;
        } else {
            newCode = code.toString();
        }
        return newCode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void saveAddressSuccess(String message) {
        showShortToast(message);
        String result = mProvince + mCity + mArea + mDetail;
        Intent intent = new Intent();
        intent.putExtra(ADDRESS_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView(String message) {
        showShortToast(message);
    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }
}
