package com.wonders.xlab.patient.module.base;

import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/3/14.
 */
public abstract class CustomUltimateViewAdapter<T> extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    private List<T> mBeanList = new ArrayList<>();

    @Override
    public int getAdapterItemCount() {
        return mBeanList.size();
    }

    public void insertToFist(T bean) {
        super.insertFirstInternal(mBeanList, bean);
    }

    public void appendToLast(T bean) {
        super.insertLastInternal(mBeanList, bean);
    }

    public void remove(int position) {
        super.removeInternal(mBeanList, position);
    }

    public void clear() {
        super.removeAllInternal(mBeanList);
    }

    /**
     * 因为这里需要有移动的动画效果，所以采用了遍历的方式来交换位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void swap(int fromPosition, int toPosition) {
        super.swapPositions(mBeanList, fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    public void setDataList(List<T> beanList) {
        if (null != this.mBeanList) {
            this.mBeanList.clear();
        }
        super.insertInternal(beanList, this.mBeanList);
    }

    public void appendDataList(List<T> beanList) {
        super.insertInternal(beanList, this.mBeanList);
    }

    public List<T> getDataList() {
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        return mBeanList;
    }

    public T getDataItem(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            return mBeanList.get(position);
        }
        return null;

    }

}
