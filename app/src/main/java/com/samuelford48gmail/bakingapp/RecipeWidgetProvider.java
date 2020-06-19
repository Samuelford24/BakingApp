package com.samuelford48gmail.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

public class RecipeWidgetProvider extends AppWidgetProvider {
    String recipe;
    /*
        @Override
        public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
            super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        recipe = intent.getStringExtra("Recipe");
        System.out.println("Received" + recipe);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        System.out.println(appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetlistview);

            Intent intent = new Intent(context, Rservice.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
            // intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // intent.putExtra("ingredients",ingredients);
            views.setRemoteAdapter(R.id.lvWIDGET, intent);
            views.setTextViewText(R.id.recipeName, recipe);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvWIDGET);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);


    }
}

