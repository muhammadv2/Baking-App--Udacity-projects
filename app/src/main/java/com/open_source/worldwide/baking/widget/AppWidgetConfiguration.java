package com.open_source.worldwide.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.loaders.IngredientsLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppWidgetConfiguration extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    private int mAppWidgetId;

    @BindView(R.id.add_to_widget_btn)
    Button addWidgetButton;

    private int recipeId;
    private String recipeName;

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
                recipeName = "Nutella Pie";
                return;
            case R.id.recipe_two_rb:
                recipeId = 1;
                recipeName = "Brownies";
                return;
            case R.id.recipe_three_rb:
                recipeId = 2;
                recipeName = "Yellow Cake";
                return;
            case R.id.recipe_four_rb:
                recipeId = 3;
                recipeName = "Cheesecake";
        }

    }

    public void updateWidget(View view) {

        getSupportLoaderManager().initLoader(707, null, this);

    }

    @Override
    public android.support.v4.content.Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        return new IngredientsLoader(this, recipeId);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList> loader, ArrayList data) {

        Toast.makeText(this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        resultValue.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        setResult(RESULT_OK, resultValue);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.RECIPE_ID_KEY, recipeId).apply();
        editor.putString(Constants.RECIPE_NAME, recipeName).apply();

        Intent intent = new Intent(getApplication(), RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putParcelableArrayListExtra(Constants.INGREDIENT_KEY, data);
        int[] ids = new int[]{mAppWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList> loader) {

    }
}
