package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.pci.doctor.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.bean.MedicalRecordPhotoBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/24.
 */
public class MedicalRecordPhotoVH extends MultiViewHolder<MedicalRecordPhotoBean> {
    @Bind(R.id.tv_medical_record_photo_item_time)
    TextView mTime;
    @Bind(R.id.tv_medical_record_photo_item_title)
    TextView mTitle;
    @Bind(R.id.recycler_view_medical_record_photo_item)
    RecyclerView mRecyclerView;

    private OnPhotoClickListener mOnPhotoClickListener;

    public MedicalRecordPhotoVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(final MedicalRecordPhotoBean data) {
        mTime.setText(data.getTimeStr());
        mTitle.setText(data.getTitle());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));

        PhotoRVAdapter photoRVAdapter = new PhotoRVAdapter();
        photoRVAdapter.setDatas(data.getPhotoThumbnails());
        photoRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mOnPhotoClickListener) {
                    mOnPhotoClickListener.onPhotoClick(position);
                }
            }
        });
        mRecyclerView.setAdapter(photoRVAdapter);

    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        mOnPhotoClickListener = onPhotoClickListener;
    }

    class PhotoRVAdapter extends SimpleRVAdapter<String> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_image_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            ImageViewManager.setImageViewWithUrl(itemView.getContext(),viewHolder.mIvSimple,getBean(position), ImageViewManager.PLACE_HOLDER_EMPTY);
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_simple)
            ImageView mIvSimple;

            public ImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

    }

    public interface OnPhotoClickListener{
        void onPhotoClick(int selectedPosition);
    }
}
