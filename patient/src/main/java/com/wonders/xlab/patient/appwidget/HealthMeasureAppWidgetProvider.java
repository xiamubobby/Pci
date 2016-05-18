package com.wonders.xlab.patient.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.dailyreport.DailyReportActivity;
import com.wonders.xlab.patient.module.dailyreport.datarecord.symptom.SymptomActivity;

/**
 * Created by hua on 16/5/17.
 */
public class HealthMeasureAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d("HealthMeasureAppWidgetP", "onRestored");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("HealthMeasureAppWidgetP", "onDisabled");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("HealthMeasureAppWidgetP", "onEnabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("HealthMeasureAppWidgetP", "onDeleted");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d("HealthMeasureAppWidgetP", "onAppWidgetOptionsChanged");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("HealthMeasureAppWidgetP", "onReceive");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("HealthMeasureAppWidgetP", "onUpdate");
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_health_measure);
            views.setOnClickPendingIntent(R.id.btn_bs, getDailyReportPendingIntent(context, DailyReportActivity.SHOW_TAB_POSITION_BS));
            views.setOnClickPendingIntent(R.id.btn_bp, getDailyReportPendingIntent(context, DailyReportActivity.SHOW_TAB_POSITION_BP));
            views.setOnClickPendingIntent(R.id.btn_symptom, getSymptomPendingIntent(context));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    private PendingIntent getDailyReportPendingIntent(Context context, int defaultPosition) {
        Intent intent = new Intent(context, DailyReportActivity.class);
        Bundle data = new Bundle();
        intent.putExtra(DailyReportActivity.DEFAULT_SHOW_TAB_POSITION, defaultPosition);
        data.putInt(DailyReportActivity.DEFAULT_SHOW_TAB_POSITION, defaultPosition);
        intent.putExtras(data);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DailyReportActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_NO_CREATE);
    }

    private PendingIntent getSymptomPendingIntent(Context context) {
        Intent intent = new Intent(context, SymptomActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SymptomActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}