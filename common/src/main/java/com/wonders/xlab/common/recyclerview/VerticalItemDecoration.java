package com.wonders.xlab.common.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import im.hua.utils.DensityUtil;

/**
 * Created by hua on 16/1/28.
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;

    private Paint mPaint;

    private int mDividerHeight = 12;//dp

    public void setDividerHeight(int height) {
        mDividerHeight = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent);
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();//展现出来的子View的个数
        for (int i = 0; i < childCount - 1; i++) {
            //最后一个不用分割线
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + DensityUtil.dp2px(mContext, mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    public VerticalItemDecoration(Context context, @ColorInt int mDividerColor, int dividerHeight) {
        super();
        mContext = context;

        this.mDividerHeight = dividerHeight;

        mPaint = new Paint();
        mPaint.setColor(mDividerColor);
    }
}
