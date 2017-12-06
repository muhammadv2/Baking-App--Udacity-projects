package com.open_source.worldwide.baking.loaders;

import android.content.Context;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.NetworkUtils;

import java.util.ArrayList;

public class RecipesLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList> {

    private ArrayList mResult;
    private Context context;

    public RecipesLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mResult != null) {
            deliverResult(mResult);
        } else {
            forceLoad(); // Force an asynchronous load

        }
    }

    @Override
    public void deliverResult(ArrayList data) {
        super.deliverResult(data);
        mResult = data;
        super.deliverResult(data);
    }

    @Override
    public ArrayList loadInBackground() {
        return NetworkUtils.fetchData(context, Constants.RECIPE_KEY, 0);

    }
}



