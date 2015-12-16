package com.wonders.xlab.common.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hua on 15/12/16.
 */
public class WrapHeightRecyclerView extends RecyclerView {
    public WrapHeightRecyclerView(Context context) {
        super(context);
    }

    public WrapHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int heightSum = 0;
        int heightEven = 0;//偶数
        int heightOdd = 0;//奇数

        LayoutManager layoutManager = getLayoutManager();

        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();

           /* if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int spanCount = gridLayoutManager.getSpanCount();



            } else if (layoutManager instanceof LinearLayoutManager) {

            }else if (layoutManager instanceof StaggeredGridLayoutManager) {

            }*/

            if (i % 2 == 0) {
                heightEven += h;
            } else {
                heightOdd += h;
            }
        }

        heightSum = heightEven > heightOdd ? heightEven : heightOdd;
        Log.d("WrapHeightRecyclerView", "heightEven:" + heightEven);
        Log.d("WrapHeightRecyclerView", "heightOdd:" + heightOdd);
        Log.d("WrapHeightRecyclerView", "heightSum:" + heightSum);
        heightSpec = MeasureSpec.makeMeasureSpec(heightSum,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }
}
