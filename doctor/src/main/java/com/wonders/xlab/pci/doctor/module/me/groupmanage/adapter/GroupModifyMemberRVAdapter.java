package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;
import com.wonders.xlab.pci.doctor.util.ImageViewManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.ForegroundImageView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyMemberRVAdapter extends SimpleRVAdapter<GroupModifyMemberBean> {

    private GroupModifyMemberBean minusBean;
    private GroupModifyMemberBean addBean;
    private boolean mIsAdmin;

    /**
     * 每次添加数据都是先把加号和减号去掉，然后再根据数据的条数再把加号和减号添加回去
     *
     * @param mBeanList
     */
    @Override
    public void appendDatas(List<GroupModifyMemberBean> mBeanList) {
        /**
         * 判断空
         * 先移除加减号
         * 重新把加减号加在最后
         * 加进来的数据去重，然后拼接在后面，如果已经在显示了则不要再添加
         */
        if (null == mBeanList || mBeanList.size() <= 0) {
            return;
        }
        Observable.from(getBeanList())
                .flatMap(new Func1<GroupModifyMemberBean, Observable<GroupModifyMemberBean>>() {
                    @Override
                    public Observable<GroupModifyMemberBean> call(GroupModifyMemberBean groupModifyMemberBean) {
                        return Observable.just(groupModifyMemberBean);
                    }
                })
                .filter(new Func1<GroupModifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(GroupModifyMemberBean groupModifyMemberBean) {
                        return GroupModifyMemberBean.TYPE_MEMBER != groupModifyMemberBean.getType();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GroupModifyMemberBean>() {
                    @Override
                    public void call(GroupModifyMemberBean bean) {
                        remove(getBeanList().indexOf(bean));
                    }
                });
        Observable.from(mBeanList)
                .flatMap(new Func1<GroupModifyMemberBean, Observable<GroupModifyMemberBean>>() {
                    @Override
                    public Observable<GroupModifyMemberBean> call(GroupModifyMemberBean groupModifyMemberBean) {
                        return Observable.just(groupModifyMemberBean);
                    }
                })
                .filter(new Func1<GroupModifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(GroupModifyMemberBean groupModifyMemberBean) {
                        return -1 == getBeanList().indexOf(groupModifyMemberBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupModifyMemberBean>() {

                    @Override
                    public void onCompleted() {
                        if (mIsAdmin) {
                            int size = getItemCount();
                            if (size < 9) {
                                appendToLast(addBean);
                            }
                            int agreedMemberSize = 0;
                            for (GroupModifyMemberBean bean : getBeanList()) {
                                if (bean.hasAgreed.get()) {
                                    agreedMemberSize++;
                                }
                            }
                            if (size >= agreedMemberSize) {
                                appendToLast(minusBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GroupModifyMemberBean bean) {
                        getBeanList().add(bean);
                        notifyItemInserted(getItemCount() - 1);
                    }
                });
    }

    @Override
    public void setDatas(List<GroupModifyMemberBean> mBeanList) {
        super.setDatas(mBeanList);
        if (null == mBeanList || mBeanList.size() <= 0) {
            appendToLast(addBean);
            return;
        }
        Observable.from(mBeanList)
                .flatMap(new Func1<GroupModifyMemberBean, Observable<GroupModifyMemberBean>>() {
                    @Override
                    public Observable<GroupModifyMemberBean> call(GroupModifyMemberBean groupModifyMemberBean) {
                        return Observable.just(groupModifyMemberBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupModifyMemberBean>() {
                    int agreedMemberSize = 0;

                    @Override
                    public void onCompleted() {
                        if (mIsAdmin) {
                            int size = getItemCount();
                            if (size < 9) {
                                appendToLast(addBean);
                            }
                            if (agreedMemberSize >= 2) {
                                appendToLast(minusBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GroupModifyMemberBean bean) {
                        if (bean.hasAgreed.get()) {
                            agreedMemberSize++;
                        }
                    }
                });

    }

    public void setIsAdmin(boolean isAdmin) {

        mIsAdmin = isAdmin;
    }

    public GroupModifyMemberRVAdapter() {
        addBean = new GroupModifyMemberBean();
        addBean.setType(GroupModifyMemberBean.TYPE_ADD);

        minusBean = new GroupModifyMemberBean();
        minusBean.setType(GroupModifyMemberBean.TYPE_MINUS);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_modify_member_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        GroupModifyMemberBean bean = getBean(position);
        switch (bean.getType()) {
            case GroupModifyMemberBean.TYPE_MEMBER:
                viewHolder.mTvName.setVisibility(View.VISIBLE);
                viewHolder.mTvName.setText(bean.doctorName.get());
                if (bean.hasAgreed.get()) {
                    viewHolder.mIvAvatar.setShowForeground(false);
                } else {
                    viewHolder.mIvAvatar.setShowForeground(true);
                }
                ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(), viewHolder.mIvAvatar, bean.doctorAvatarUrl.get(), R.drawable.ic_default_avatar_doctor);
                break;
            case GroupModifyMemberBean.TYPE_ADD:
                viewHolder.mTvName.setVisibility(View.GONE);
                viewHolder.mIvAvatar.setShowForeground(false);
                viewHolder.mIvAvatar.setImageResource(R.drawable.ic_group_member_add);
                break;
            case GroupModifyMemberBean.TYPE_MINUS:
                viewHolder.mTvName.setVisibility(View.GONE);
                viewHolder.mIvAvatar.setShowForeground(false);
                viewHolder.mIvAvatar.setImageResource(R.drawable.ic_group_member_minus);
                break;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_modify_member_avatar)
        ForegroundImageView mIvAvatar;
        @Bind(R.id.tv_group_modify_member_name)
        TextView mTvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
