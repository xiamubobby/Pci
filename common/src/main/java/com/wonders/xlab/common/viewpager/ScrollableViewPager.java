package com.wonders.xlab.common.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import xlab.wonders.com.common.R;

/**
 * Created by hua on 15/11/3.
 */
public class ScrollableViewPager extends ViewPager {
    private boolean mScrollable = true;

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScrollableViewPager, 0, 0);
        int indexCounts = array.getIndexCount();
        for (int i = 0; i < indexCounts; i++) {
            int index = array.getIndex(i);
            if (index == R.styleable.ScrollableViewPager_scrollable) {
                mScrollable = array.getBoolean(index, true);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onTouchEvent(ev);

        } else {
            return false;
        }
    }

    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    public boolean canScroll() {
        return mScrollable;
    }
}
