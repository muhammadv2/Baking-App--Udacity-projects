package com.open_source.worldwide.baking.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.NetworkUtils;

import java.util.ArrayList;


public class StepsLoader extends AsyncTaskLoader<ArrayList> {

    private ArrayList mResult;
    private Context context;
    private int recipeId;

    public StepsLoader(Context context, int recipeId) {
        super(context);
        this.context = context;
        this.recipeId = recipeId;
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
        return NetworkUtils.fetchData(context, Constants.STEP_Key, recipeId);

    }
}
