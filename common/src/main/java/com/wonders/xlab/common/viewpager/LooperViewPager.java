package com.wonders.xlab.common.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hua on 16/3/17.
 */
public class LooperViewPager extends ViewPager {
    private Handler mHandler = new Handler();

    private int mTopCounts = 0;

    private long mIntervalTimeInMill = 5000;

    public LooperViewPager(Context context) {
        super(context);
        init();
    }

    public LooperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private boolean mIsTouching = false;

    private void init() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mIsTouching = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mIsTouching = true;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mIsTouching = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        mIsTouching = false;
                        break;
                }
                return false;
            }
        });

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (!mIsTouching) {
                    PagerAdapter adapter = getAdapter();
                    if (null != adapter) {
                        mTopCounts = adapter.getCount();
                        if (mTopCounts > 0) {
                            int currentItem = getCurrentItem();
                            if (currentItem < mTopCounts - 1) {
                                setCurrentItem(currentItem + 1, true);
                            } else {
                                setCurrentItem(0, true);
                            }
                        }
                    }
                }
                mHandler.sendEmptyMessageDelayed(0, mIntervalTimeInMill);

                return false;
            }
        });

        mHandler.sendEmptyMessageDelayed(0, mIntervalTimeInMill);
    }

    public void setLooperIntervalTimeInMill(long intervalTimeInMill) {
        mIntervalTimeInMill = intervalTimeInMill;
    }

}