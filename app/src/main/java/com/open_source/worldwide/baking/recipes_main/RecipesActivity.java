package com.open_source.worldwide.baking.recipes_main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.open_source.worldwide.baking.Adapters.MainScreenAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.loaders.RecipesLoader;
import com.open_source.worldwide.baking.models.Recipe;
import com.open_source.worldwide.baking.recipe_details.DetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity
        implements MainScreenAdapter.OnItemClickListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList> {

    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    private ArrayList<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        ButterKnife.bind(this);

        if (isOnline())
            getSupportLoaderManager().initLoader(101, null, this);


    }

    @Override
    public void onClick(int position) {

        Recipe recipe = recipes.get(position);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.RECIPE_ID_KEY, position);
        intent.putExtra(Constants.RECIPE_NAME, recipe.getName());
        intent.setAction(DetailsActivity.SHOW_DETAILS_ACTION);
        startActivity(intent);

    }

    @Override
    public android.support.v4.content.Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        return new RecipesLoader(this);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList> loader, ArrayList data) {

        if (data != null)
            recipes = data;

        MainScreenAdapter mAdapter = new MainScreenAdapter(this, data, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        Log.i("RecipeActivity", "onLoadFinished: ");

        if (getResources().getBoolean(R.bool.isTablet)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

            }
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList> loader) {

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
