
package com.wonders.xlab.pci.doctor.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.wonders.xlab.pci.doctor.R;

import im.hua.utils.DensityUtil;


/**
 * Custom implementation of the MarkerView.
 *
 */
public class BPMarkerView extends MarkerView {
    private Context mContext;

    private TextView tvContent;

    private String[] mXVals;

    public BPMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mContext = context;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    public void setXVals(String[] xVals) {
        mXVals = xVals;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText(String.format("%s", Utils.formatNumber(ce.getHigh(), 0, true)));
        } else {

//            String dateStr = mXVals[highlight.getDataSetIndex()];
//            int da = highlight.getStackIndex();
//            tvContent.setText(String.format("%s\n%s", dateStr, Utils.formatNumber(e.getVal(), 0, true)));
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        int xOffset;

        int screenWidth = DensityUtil.getWindowWidthPixel(mContext);
        if (screenWidth < xpos + getWidth()) {
            xOffset = -getWidth();
        } else if (xpos <= getWidth() / 2) {
            xOffset = 0;
        } else {
            xOffset = -(getWidth() / 2);
        }
        return xOffset;
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
