package com.wonders.xlab.patient.module.chatroom.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.ChatRoomItemMeBinding;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/19.
 */
public class MeChatRoomVH extends MultiViewHolder<MeChatRoomBean> {
    @Bind(R.id.iv_chat_room_item_me_portrait)
    ImageView mIvChatRoomItemMePortrait;

    private ChatRoomItemMeBinding binding;

    public MeChatRoomVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        /**
         * 坑
         * 绑定只能在这里进行，不能在{@link #onBindViewHolder(MeChatRoomBean)}中绑定
         * TODO 还没搞懂为什么
         * 应该是因为{@link #onBindViewHolder(MeChatRoomBean)}方法已经经过了view的初始化，此时在此方法内部在进行bind就没有意义的，所以bind需要在刚开始初始化的时候进行
         */
        binding = ChatRoomItemMeBinding.bind(itemView);

    }

    @Override
    public void onBindViewHolder(MeChatRoomBean data) {
        ImageViewManager.setImageViewWithUrl(itemView.getContext(), mIvChatRoomItemMePortrait, data.portraitUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);

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
