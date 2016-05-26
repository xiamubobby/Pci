package com.wonders.xlab.patient.module.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.order.bean.OrderListBean;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jimmy on 16/5/9.
 */
public class OrderListAdapter extends SimpleRVAdapter<OrderListBean> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        OrderListBean order = getBean(position);
        viewHolder.orgName.setText(order.getOrganizationName());
        viewHolder.serviceStatus.setText(order.getStatus());
        viewHolder.orderNo.setText(String.format("订单号：%s", order.getOrderId()));
        viewHolder.serviceDetail.setText(order.getTitle());
        viewHolder.servicePrice.setText(String.format("实付款：%s", order.getPrice()));

        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(),
                viewHolder.orgImageUrl,
                order.getOrganizationImageUrl(),
                ImageViewManager.PLACE_HOLDER_EMPTY);

        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(),
                viewHolder.servicePic,
                order.getSpecificationImageUrl(),
                ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.order_list_org_image_url)
        ImageView orgImageUrl;
        @Bind(R.id.order_list_org_name)
        TextView orgName;
        @Bind(R.id.order_list_service_status)
        TextView serviceStatus;
        @Bind(R.id.order_service_pic)
        ImageView servicePic;
        @Bind(R.id.order_no)
        TextView orderNo;
        @Bind(R.id.service_detail)
        TextView serviceDetail;
        @Bind(R.id.service_price_item)
        TextView servicePrice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
