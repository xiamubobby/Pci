package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageDetailEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageDetailModel;
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
public class GroupServiceModifyPresenter extends BasePresenter implements IGroupServiceModifyPresenter, GroupPackageDetailModel.GroupPackageDetailModelListener {
    private GroupServiceModifyPresenterListener mListener;
    private IGroupPackageDetailModel mPackageDetailModel;

    public GroupServiceModifyPresenter(GroupServiceModifyPresenterListener listener) {
        mListener = listener;
        mPackageDetailModel = new GroupPackageDetailModel(this);
        addModel(mPackageDetailModel);
    }

    @Override
    public void getServicePackageInfo(String doctorGroupId, String servicePackageId, boolean published) {
        mPackageDetailModel.getPackageDetail(doctorGroupId,servicePackageId,published);
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
    public void onReceiveFailed(String message) {
        mListener.hideLoading();
        mListener.showError(message);
    }

    public interface GroupServiceModifyPresenterListener extends BasePresenterListener{
        void showServicePackageInfo(PackageInfoBean packageInfoBean);
    }
}
