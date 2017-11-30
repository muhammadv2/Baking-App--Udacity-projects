package com.open_source.worldwide.baking.recipes_main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open_source.worldwide.baking.Adapters.MainScreenAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;
import com.open_source.worldwide.baking.recipe_details.DetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements MainScreenAdapter.OnItemClickListener {

    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    MainScreenAdapter mAdapter;

    private  ArrayList<Recipe> recipes;


    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipes = JsonUtils.getRecipesFromJson(getActivity());
        mAdapter = new MainScreenAdapter(getActivity(), recipes, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        if (getResources().getBoolean(R.bool.isTablet)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

            }
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }

    @Override
    public void onClick(int position) {

        Recipe recipe = recipes.get(position);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.RECIPE_ID_KEY, position);
        intent.putExtra(Constants.RECIPE_NAME, recipe.getName());
        intent.setAction(DetailsActivity.SHOW_DETAILS_ACTION);
        startActivity(intent);

    }

}
