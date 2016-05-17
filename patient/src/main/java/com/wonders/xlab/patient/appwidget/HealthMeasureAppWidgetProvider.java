package com.wonders.xlab.patient.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.dailyreport.DailyReportActivity;
import com.wonders.xlab.patient.module.dailyreport.datarecord.symptom.SymptomActivity;

/**
 * Created by hua on 16/5/17.
 */
public class HealthMeasureAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_health_measure);
            views.setOnClickPendingIntent(R.id.btn_bs, getDailyReportPendingIntent(context, DailyReportActivity.SHOW_TAB_POSITION_BS));
//            views.setOnClickPendingIntent(R.id.btn_bp, getDailyReportPendingIntent(context, DailyReportActivity.SHOW_TAB_POSITION_BP));
            views.setOnClickPendingIntent(R.id.btn_symptom, getPendingOthersIntent(context, SymptomActivity.class));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    private PendingIntent getDailyReportPendingIntent(Context context, int defaultPosition) {
        Intent intent = new Intent(context, DailyReportActivity.class);
        Bundle data = new Bundle();
        data.putInt(DailyReportActivity.DEFAULT_SHOW_TAB_POSITION, defaultPosition);
        intent.putExtras(data);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private PendingIntent getPendingOthersIntent(Context context, Class targetClazz) {
        Intent intent = new Intent(context, targetClazz);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}