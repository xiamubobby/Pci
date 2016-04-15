package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageCreateBody;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageDetailEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageCreateModel;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageDetailModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupPackageCreateModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupPackageDetailModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServiceModifyPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServiceModifyPresenter extends BasePresenter implements IGroupServiceModifyPresenter, GroupPackageDetailModel.GroupPackageDetailModelListener, GroupPackageCreateModel.GroupPackageCreateModelListener {
    private GroupServiceModifyPresenterListener mListener;
    private IGroupPackageDetailModel mPackageDetailModel;
    private IGroupPackageCreateModel mPackageCreateModel;

    public GroupServiceModifyPresenter(GroupServiceModifyPresenterListener listener) {
        mListener = listener;
        mPackageDetailModel = new GroupPackageDetailModel(this);
        mPackageCreateModel = new GroupPackageCreateModel(this);
        addModel(mPackageDetailModel);
        addModel(mPackageCreateModel);
    }

    @Override
    public void getServicePackageInfo(String doctorGroupId, String servicePackageId, boolean published) {
        mPackageDetailModel.getPackageDetail(doctorGroupId,servicePackageId,published);
    }

    @Override
    public void updatePackage(GroupPackageCreateBody body) {

    }

    @Override
    public void onReceivePackageDetailSuccess(GroupPackageDetailEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        GroupPackageDetailEntity.RetValuesEntity.NodeEntity nodeEntity = valuesEntity.getNode();
        List<GroupPackageDetailEntity.RetValuesEntity.PriceEntity> priceEntities = valuesEntity.getPrice();

        List<HashMap<String,String>> defaultValues = new ArrayList<>();

        for (GroupPackageDetailEntity.RetValuesEntity.PriceEntity priceEntity : priceEntities) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value", priceEntity.getNumber() + nodeEntity.getUnitName());
            map.put("tag", String.valueOf(priceEntity.getNumber()));
            defaultValues.add(map);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("value", "自定义");
        map.put("tag", "-1");
        defaultValues.add(map);

        PackageInfoBean packageInfoBean = new PackageInfoBean(nodeEntity.getUnitTitle(),nodeEntity.getUnitName(),defaultValues,"套餐说明",nodeEntity.getContent());
        mListener.showServicePackageInfo(packageInfoBean);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.hideLoading();
        mListener.showNetworkError(message);
    }

    @Override
    public void onCreatePackageSuccess(String message) {

    }

    public interface GroupServiceModifyPresenterListener extends BasePresenterListener{
        void showServicePackageInfo(PackageInfoBean packageInfoBean);
    }
}
