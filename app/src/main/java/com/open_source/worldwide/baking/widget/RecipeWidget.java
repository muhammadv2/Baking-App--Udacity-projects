package com.open_source.worldwide.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("shr", Context.MODE_PRIVATE);
        int recipeId = sharedPreferences.getInt(Constants.RECIPE_ID_KEY, 0);

        Log.i("widget", "updateAppWidget: " + recipeId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent intent = new Intent(context, WidgetServices.class);

        // Set up the RemoteViews object to use a RemoteViews adapter.
        // This adapter connects to a RemoteViewsService through the specified intent.
        // This is how you populate the data.
        views.setRemoteAdapter(R.id.recipes_lv, intent);

//
//        Intent startActivityIntent = new Intent(context, DetailsActivity.class);
//        PendingIntent startActivityPendingIntent =
//                PendingIntent.getActivity(context,
//                        0,
//                        startActivityIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        views.setPendingIntentTemplate(R.id.recipes_lv, startActivityPendingIntent);


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
}

