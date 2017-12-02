package com.open_source.worldwide.baking.recipes_main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.open_source.worldwide.baking.Adapters.MainScreenAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;
import com.open_source.worldwide.baking.recipe_details.DetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity
        implements MainScreenAdapter.OnItemClickListener {

    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    private ArrayList<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        ButterKnife.bind(this);

        recipes = JsonUtils.getRecipesFromJson(this);
        MainScreenAdapter mAdapter = new MainScreenAdapter(this, recipes, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

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
    public void onClick(int position) {

        Recipe recipe = recipes.get(position);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.RECIPE_ID_KEY, position);
        intent.putExtra(Constants.RECIPE_NAME, recipe.getName());
        intent.setAction(DetailsActivity.SHOW_DETAILS_ACTION);
        startActivity(intent);

    }
}
