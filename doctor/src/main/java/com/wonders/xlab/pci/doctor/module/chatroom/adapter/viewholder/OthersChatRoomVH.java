package com.wonders.xlab.pci.doctor.module.chatroom.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.pci.doctor.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.ChatRoomItemOthersBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/19.
 */
public class OthersChatRoomVH extends MultiViewHolder<OthersChatRoomBean> {
    @Bind(R.id.iv_chat_room_item_others_portrait)
    ImageView mIvChatRoomItemOthersPortrait;

    private ChatRoomItemOthersBinding binding;

    public OthersChatRoomVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        binding = ChatRoomItemOthersBinding.bind(itemView);
    }

    @Override
    public void onBindViewHolder(OthersChatRoomBean data) {
        ImageViewManager.setImageViewWithUrl(itemView.getContext(), mIvChatRoomItemOthersPortrait, data.portraitUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
        binding.setChat(data);
    }
}
