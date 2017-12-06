package com.open_source.worldwide.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;

import java.util.ArrayList;

public class WidgetServices extends RemoteViewsService {

    private int recipeId;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        if (intent.getData() != null) {
            recipeId = Integer.valueOf(intent.getData().getSchemeSpecificPart());
            Bundle bundle = intent.getBundleExtra("bundle");
            ingredients = bundle.getParcelableArrayList(Constants.INGREDIENT_KEY);
            Log.i("WidgetServices", "onGetViewFactory: " + ingredients);
        }

        return new WidgeRemoteViewsFactory(this.getApplicationContext()) {
        };

    }


    private class WidgeRemoteViewsFactory implements RemoteViewsFactory {

        Context mContext;


        public WidgeRemoteViewsFactory(Context applicationContext) {

            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            // Construct a RemoteViews item based on the app widget item XML file, and set the
            // text and image on the position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_card_view);

            Ingredient ingredient = ingredients.get(position);

            //extract and set all necessary data on views
            String quantity_measure = ingredient.getQuantity() + " " + ingredient.getMeasure();
            rv.setTextViewText(R.id.ingredient_measure_tv_widget, quantity_measure);
            String ingredientDetails = "of " + ingredient.getIngredient();
            rv.setTextViewText(R.id.ingredient_material_tv_widget, ingredientDetails);

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
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}

