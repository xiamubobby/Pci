package com.wonders.xlab.pci.doctor.module.notification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.NotifiOthersItemBinding;
import com.wonders.xlab.pci.doctor.module.notification.adapter.bean.NotifiOthersBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersRVAdapter extends SimpleRVAdapter<NotifiOthersBean> {

    private boolean mIsSelectedAll = false;
    private boolean mIsShowingRadioButton = false;

    public void showRadioButton(final boolean show) {
        mIsShowingRadioButton = show;
        Observable.from(getBeanList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NotifiOthersBean>() {
                    @Override
                    public void call(NotifiOthersBean notifiOthersBean) {
                        notifiOthersBean.radioVisible.set(show);
                    }
                });
    }

    public void toggleSelect() {
        selectAll(!mIsSelectedAll);
    }

    public void selectAll(final boolean select) {
        mIsSelectedAll = select;
        Observable.from(getBeanList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NotifiOthersBean>() {
                    @Override
                    public void call(NotifiOthersBean notifiOthersBean) {
                        notifiOthersBean.selected.set(select);
                    }
                });
    }

    public void deleteSelectedItem() {
        Observable.from(getBeanList())
                .filter(new Func1<NotifiOthersBean, Boolean>() {
                    @Override
                    public Boolean call(NotifiOthersBean notifiOthersBean) {
                        return notifiOthersBean.selected.get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NotifiOthersBean>() {
                    @Override
                    public void call(NotifiOthersBean notifiOthersBean) {
                        remove(getBeanList().indexOf(notifiOthersBean));
                    }
                });
    }

    @Override
    public void setDatas(List<NotifiOthersBean> mBeanList) {
        super.setDatas(mBeanList);
        showRadioButton(mIsShowingRadioButton);
    }

    @Override
    public void appendDatas(List<NotifiOthersBean> mBeanList) {
        super.appendDatas(mBeanList);
        showRadioButton(mIsShowingRadioButton);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notifi_others_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean selected = getBean(holder.getAdapterPosition()).selected.get();
                getBean(holder.getAdapterPosition()).selected.set(!selected);
            }
        });
        viewHolder.mRbNotifiOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getBean(holder.getAdapterPosition()).selected.set(isChecked);
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rb_notifi_others)
        CheckBox mRbNotifiOthers;

        NotifiOthersItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = NotifiOthersItemBinding.bind(itemView);
        }
    }
}
