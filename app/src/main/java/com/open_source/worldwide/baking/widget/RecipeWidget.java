package com.open_source.worldwide.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;

import java.util.ArrayList;


public class RecipeWidget extends AppWidgetProvider {


    private static int recipeId;

    private static ArrayList<Ingredient> ingredients;


    private static final String TAG = RecipeWidget.class.toString();

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        recipeId = prefs.getInt(Constants.RECIPE_ID_KEY, 0);
        String recipeName = prefs.getString(Constants.RECIPE_NAME, "Recipe");

        Intent intent = new Intent(context, WidgetServices.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.INGREDIENT_KEY, ingredients);
        intent.setData(Uri.fromParts("content", String.valueOf(recipeId), null));
        intent.putExtra("bundle", bundle);
        Log.i(TAG, "updateAppWidget: " + ingredients);

        views.setTextViewText(R.id.recipe_title_widget, recipeName);

        views.setRemoteAdapter(R.id.recipes_lv, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        int last = appWidgetIds.length - 1;
        int updateOnlyThis = appWidgetIds[last];

        updateAppWidget(context, appWidgetManager, updateOnlyThis);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        ingredients = intent.getParcelableArrayListExtra(Constants.INGREDIENT_KEY);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
        Log.i(TAG, "onReceive: " + (ingredients == null));
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

