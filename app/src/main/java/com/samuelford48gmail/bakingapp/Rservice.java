package com.samuelford48gmail.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class Rservice extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("Service", "onGetViewFactory");
        return new LVRemoteViewsFactory(getApplicationContext(), intent);
    }
}

class LVRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    String[] ingredientss;
    String ingredients;
    ArrayList<String> ingredientArrayList;
    private int appWidgetId;

    public LVRemoteViewsFactory(Context applicationContext, Intent intent) {
        Log.d("Service", "kdsfjkdslf");
        mContext = applicationContext;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, AppWidgetManager.INVALID_APPWIDGET_ID);
        // ingredients=intent.getStringExtra("ingredients");

    }

    private void getData() {
        //   SharedPreferences sharedPreferences = mContext.getSharedPreferences("Ingredients",Context.MODE_PRIVATE);
        //String ingredientList = ingredients
        // System.out.println(ingredientList);
        //   assert ingredientList != null;
        Log.d("Service", "getData");
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
        String ingredients = sharedPreferences.getString("Ingredients", "");
        System.out.println(ingredients);
        ingredientss = ingredients.split(",");
    }

    @Override
    public void onCreate() {
        System.out.println("OnCreate");
        Log.d("Service", "onCreate");
        getData();
    }

    @Override
    public void onDataSetChanged() {
        System.out.println("OnDataSetChanged");
        getData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        System.out.println("get COunt");
        if (ingredientss == null) {
            return 0;
        } else {
            return ingredientss.length;
        }

    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        if (ingredientss != null) {
            rv.setTextViewText(R.id.TVwidgetLV, ingredientss[i]);
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
