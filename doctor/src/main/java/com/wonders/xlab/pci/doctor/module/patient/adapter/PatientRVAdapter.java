package com.wonders.xlab.pci.doctor.module.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.PatientItemBinding;
import com.wonders.xlab.pci.doctor.module.otto.MainBottomUnreadNotifyCountOtto;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.util.ImageViewManager;
import com.wonders.xlab.pci.doctor.util.UnReadMessageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;

/**
 * Created by hua on 16/2/19.
 */
public class PatientRVAdapter extends SimpleRVAdapter<PatientBean> {

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        OttoManager.register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        OttoManager.unregister(this);
    }

    /**
     * 更新数字图标
     *
     * @param otto
     */
    @Subscribe
    public void changeDoctorNotifyCounts(MainBottomUnreadNotifyCountOtto otto) {
        for (int i = 0; i < getBeanList().size(); i++) {
            if (otto.getImGroupId().equals(getBeanList().get(i).getImGroupId())) {
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PatientViewHolder viewHolder = (PatientViewHolder) holder;
        PatientBean bean = getBean(position);

        int counts = UnReadMessageUtil.getUnreadMessageCounts(bean.getImGroupId());
        BGABadgeViewHelper badgeViewHelper = viewHolder.mIvPatientItemPortrait.getBadgeViewHelper();
        badgeViewHelper.setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        if (counts > 0 && counts < 100) {
            viewHolder.mIvPatientItemPortrait.showTextBadge(String.valueOf(counts));
        } else if (counts >= 100) {
            viewHolder.mIvPatientItemPortrait.showTextBadge("99+");
        } else {
            viewHolder.mIvPatientItemPortrait.hiddenBadge();
        }

        viewHolder.mBinding.setPatient(bean);
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPatientItemPortrait, bean.getPortrait(), R.drawable.ic_default_avatar_patient);

    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_patient_item_portrait)
        BGABadgeImageView mIvPatientItemPortrait;

        PatientItemBinding mBinding;

        public PatientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBinding = PatientItemBinding.bind(itemView);
        }

    }
}
