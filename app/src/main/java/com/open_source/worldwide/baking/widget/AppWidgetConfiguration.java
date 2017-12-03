package com.open_source.worldwide.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppWidgetConfiguration extends AppCompatActivity {

    private int mAppWidgetId;

    @BindView(R.id.add_to_widget_btn)
    Button addWidgetButton;

    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuartion_app_widget);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(this.getPackageName(),
                R.layout.recipe_widget);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);


    }

    public void updateRecipeId(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.recipe_one_rb:
                recipeId = 0;
                return;
            case R.id.recipe_two_rb:
                recipeId = 1;
                return;
            case R.id.recipe_three_rb:
                recipeId = 2;
                return;
            case R.id.recipe_four_rb:
                recipeId = 3;
        }

    }

    public void updateWidget(View view) {
        Log.i("widget", "onClick: " + recipeId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        resultValue.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        setResult(RESULT_OK, resultValue);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.RECIPE_ID_KEY, recipeId).apply();

        Intent intent = new Intent(getApplication(), RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = new int[]{mAppWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
        finish();
    }
}
