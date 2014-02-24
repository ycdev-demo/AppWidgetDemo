package me.ycdev.demo.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "appwidgetdemo";
    public static final String CLICK_ACTION = "me.ycdev.demo.appwidget.action.WIDGET_CLICK";

    @Override
    public void onReceive(Context cxt, Intent intent) {
        super.onReceive(cxt, intent);
        Log.i(TAG, "widget received: " + intent);
        String action = intent.getAction();
        if (CLICK_ACTION.equals(action)) {
            Toast.makeText(cxt, intent.getDataString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context cxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(cxt, appWidgetManager, appWidgetIds);
        ComponentName thisWidget = new ComponentName(cxt, MyAppWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(cxt);
        manager.updateAppWidget(thisWidget, getRemoteView(cxt));
        Log.i(TAG, "update widget");
    }

    private RemoteViews getRemoteView(Context cxt) {
        RemoteViews views = new RemoteViews(cxt.getPackageName(),
                R.layout.my_app_widget);

        views.setOnClickPendingIntent(R.id.btn1, getPendingIntent(cxt, 1));
        views.setOnClickPendingIntent(R.id.btn2, getPendingIntent(cxt, 2));

        return views;
    }

    private PendingIntent getPendingIntent(Context cxt, int btnId) {
        Intent intent = new Intent(CLICK_ACTION);
        // if don't set component, the widget will not respond the click event if app was stopped
        intent.setClass(cxt, MyAppWidgetProvider.class);
        intent.putExtra("btn_id", btnId);
        intent.setData(Uri.parse("btn_id:"+ btnId));
        PendingIntent pi = PendingIntent.getBroadcast(cxt, 0, intent, 0);
        return pi;
    }

}
