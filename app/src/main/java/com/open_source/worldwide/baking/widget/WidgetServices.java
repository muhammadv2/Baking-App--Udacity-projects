package com.open_source.worldwide.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;

import java.util.ArrayList;

public class WidgetServices extends RemoteViewsService {


    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

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
            return 4;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            // position will always range from 0 to getCount() - 1.

            // Construct a RemoteViews item based on the app widget item XML file, and set the

            // text based on the position.

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_card_view);

            // feed row

            ArrayList<Recipe> recipes = JsonUtils.getRecipesFromJson(mContext);
            Recipe recipe = recipes.get(position);

            String recipeName = recipe.getName();

            int[] images = new int[]{R.drawable.nutella_1,
                    R.drawable.choco_2,R.drawable.yellow_3,R.drawable.straw_4};

            rv.setTextViewText(R.id.main_recipe_tv_widget, recipeName);
            rv.setImageViewResource(R.id.main_recipe_iv_widget,images[position]);

            // end feed row

            // Next, set a fill-intent, which will be used to fill in the pending intent template

            // that is set on the collection view in ListViewWidgetProvider.

//            Bundle extras = new Bundle();
//
//            extras.putInt(AppWidgetProvider.EXTRA_ITEM, position);
//
//            Intent fillInIntent = new Intent();
//
//            fillInIntent.putExtra("homescreen_meeting", data);
//
//            fillInIntent.putExtras(extras);

            // Make it possible to distinguish the individual on-click

            // action of a given item

//            rv.setOnClickFillInIntent(R.id.widget_container, fillInIntent);

            // Return the RemoteViews object.

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

