package im.hua.uikit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 加载动画
 */
public class LoadingDotView extends LinearLayout {

    private int mDotCount = 6;
    private Context context;
    private Animation animation;

    public LoadingDotView(Context context) {
        super(context, null);
    }

    public LoadingDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setOrientation(HORIZONTAL);
        for (int i = 0; i < mDotCount; i++) {
            TextView textView = new TextView(context);
            textView.setText("·");
            textView.setTextColor(Color.BLACK);
            addView(textView);
        }
    }

    public void setDotCount(int dotCount) {
        mDotCount = dotCount;
    }

    public LoadingDotView setDuration(long duration) {
        animation = new LoadDotAnimation(this);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(duration);
        return this;
    }

    /**
     * @param duration if duration <= 0,start immediately
     *                 otherwise start after #duration millis
     * @if it's not the first time to call this,you should invoke stopAnimation() before invoking this
     */
    public void startAnimation(long duration) {
        animation.reset();
        if (duration <= 0) {
            this.startAnimation(animation);
        } else {
            animation.setStartTime(AnimationUtils.currentAnimationTimeMillis() + duration);
            this.setAnimation(animation);
        }
    }

    public LoadingDotView setDotColor(int color) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextColor(color);
        }
        return this;
    }

    public LoadingDotView setDotSize(float size) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        }
        return this;
    }

    public void stopAnimation() {
        this.clearAnimation();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(VISIBLE);
        }
    }

}

class LoadDotAnimation extends Animation {

    private View[] views;

    public LoadDotAnimation(ViewGroup viewGroup) {
        if (viewGroup.getChildCount() > 0) {
            views = new View[viewGroup.getChildCount()];
            for (int i = 0; i < views.length; i++) {
                views[i] = viewGroup.getChildAt(i);
                views[i].setVisibility(View.INVISIBLE);
            }
        }
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                for (int i = 0; i < views.length; i++) {
                    views[i].setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (views != null && views.length > 0) {
            if (interpolatedTime * views.length >= 1) {
                if (interpolatedTime > 0.99) {
                    views[views.length - 1].setVisibility(View.VISIBLE);
                } else {
                    views[(int) (interpolatedTime * views.length) - 1].setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
