package com.wonders.xlab.patient.module.chatroom.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.ChatRoomItemOthersBinding;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/19.
 */
public class OthersChatRoomVH extends MultiViewHolder<OthersChatRoomBean> {
    @Bind(R.id.iv_chat_room_item_others_portrait)
    ImageView mIvPortrait;

    private ChatRoomItemOthersBinding binding;

    public OthersChatRoomVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        binding = ChatRoomItemOthersBinding.bind(itemView);
    }

    @Override
    public void onBindViewHolder(OthersChatRoomBean data) {
        ImageViewManager.setImageViewWithUrl(itemView.getContext(), mIvPortrait, data.portraitUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
        data.recordTimeInStr.set(DateUtil.formatShowDateTime(data.recordTimeInMill.get(), "yyyy年MM月dd日 HH:mm", "MM月dd日 HH:mm", "HH:mm"));
        binding.setChat(data);
    }
}
