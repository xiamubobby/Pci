package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.data.entity.GroupPackageDetailEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupPackagePublishModel;
import com.wonders.xlab.pci.doctor.data.model.IGroupPackageDetailModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupPackagePublishModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupPackageDetailModel;
import com.wonders.xlab.pci.doctor.data.presenter.IGroupServiceModifyPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServiceModifyPresenter extends BasePresenter implements IGroupServiceModifyPresenter, GroupPackageDetailModel.GroupPackageDetailModelListener, GroupPackagePublishModel.GroupPackageCreateModelListener {
    private GroupServiceModifyPresenterListener mListener;
    private IGroupPackageDetailModel mPackageDetailModel;
    private IGroupPackagePublishModel mPackageCreateModel;

    public GroupServiceModifyPresenter(GroupServiceModifyPresenterListener listener) {
        mListener = listener;
        mPackageDetailModel = new GroupPackageDetailModel(this);
        mPackageCreateModel = new GroupPackagePublishModel(this);
        addModel(mPackageDetailModel);
        addModel(mPackageCreateModel);
    }

    @Override
    public void getServicePackageInfo(String doctorGroupId, String servicePackageId) {
        mPackageDetailModel.getPackageDetail(doctorGroupId,servicePackageId);
    }

    @Override
    public void publishPackage(GroupPackagePublishBody body) {
        mPackageCreateModel.publishPackage(body);
    }

    @Override
    public void onReceivePackageDetailSuccess(GroupPackageDetailEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        GroupPackageDetailEntity.RetValuesEntity.NodeEntity nodeEntity = valuesEntity.getNode();
        List<GroupPackageDetailEntity.RetValuesEntity.PriceEntity> priceEntities = valuesEntity.getPrice();

        List<HashMap<String,String>> defaultValues = new ArrayList<>();

        for (GroupPackageDetailEntity.RetValuesEntity.PriceEntity priceEntity : priceEntities) {
            HashMap<String, String> map = new HashMap<>();
            map.put("valueStr", priceEntity.getNumber() + nodeEntity.getUnitName());
            map.put("value", String.valueOf(priceEntity.getNumber()));
            defaultValues.add(map);
        }
        int flag = -1;
        int defaultPosition = 0;
        int defaultValue = valuesEntity.getNode().getValue();
        for (int i=0 ;i<defaultValues.size(); i++) {
            HashMap<String, String> map = defaultValues.get(i);
            int value = Integer.parseInt(map.get("value"));
            if (value == defaultValue) {
                defaultPosition = i;
                flag = i;
                break;
            }
        }
        if (-1 == flag) {
            HashMap<String, String> map = new HashMap<>();
            map.put("valueStr", defaultValue + nodeEntity.getUnitName());
            map.put("value", String.valueOf(defaultValue));
            defaultValues.add(map);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("valueStr", "自定义");
        map.put("value", "-1");
        defaultValues.add(map);

        PackageInfoBean packageInfoBean = new PackageInfoBean(nodeEntity.getDoctorPackageId(),nodeEntity.getUnitTitle(),nodeEntity.getUnitName(),defaultValues,"套餐说明",nodeEntity.getContent(), defaultPosition);
        mListener.showServicePackageInfo(packageInfoBean);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener,code,message);
    }

    @Override
    public void onCreatePackageSuccess(String message) {
        mListener.publishSuccess(message);

    }

    public interface GroupServiceModifyPresenterListener extends BasePresenterListener{
        void showServicePackageInfo(PackageInfoBean packageInfoBean);

        void publishSuccess(String message);
    }
}
