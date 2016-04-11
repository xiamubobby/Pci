package com.wonders.xlab.patient.module.chatroom.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.ChatRoomItemOthersBinding;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

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

        long todayTimeInMill = Calendar.getInstance().getTimeInMillis();
        long sendTimeInMill = data.recordTimeInMill.get();
        if (DateUtil.isTheSameDay(todayTimeInMill, sendTimeInMill)) {
            data.recordTimeInStr.set(DateUtil.format(sendTimeInMill, "HH:mm"));
        } else if (DateUtil.isTheSameYear(sendTimeInMill, todayTimeInMill)) {
            data.recordTimeInStr.set(DateUtil.format(sendTimeInMill, "MM-dd HH:mm"));
        } else {
            data.recordTimeInStr.set(DateUtil.format(sendTimeInMill, "yyyy-MM-dd HH:mm"));
        }
        binding.setChat(data);
    }
}
