package com.compose.weatherapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.compose.weatherapp.R
import com.compose.weatherapp.ui.MainActivity

class WeatherWidget : AppWidgetProvider()  {


    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)



        appWidgetIds?.forEach {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val views = RemoteViews(context?.packageName, R.layout.layout_widget)
            views.setOnClickPendingIntent(R.id.tvTemperature, pendingIntent)
            appWidgetManager?.updateAppWidget(it, views)

        }

    }
}