package com.open_source.worldwide.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;

import java.util.ArrayList;


public class RecipeWidget extends AppWidgetProvider {


    private static final String TAG = RecipeWidget.class.toString();

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int recipeId = prefs.getInt(Constants.RECIPE_ID_KEY, 0);
        Log.i(TAG, "updateAppWidget: " + appWidgetId);

        Intent intent = new Intent(context, WidgetServices.class);
//        intent.putExtra(Constants.RECIPE_ID_KEY, recipeId);
        intent.setData(Uri.fromParts("content", String.valueOf(recipeId), null));

        ArrayList<Recipe> recipes = JsonUtils.getRecipesFromJson(context);
        Recipe recipe = recipes.get(recipeId);
        
        views.setTextViewText(R.id.recipe_title_widget, recipe.getName());

        views.setRemoteAdapter(R.id.recipes_lv, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);


    }
}

