package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open_source.worldwide.baking.Adapters.IngredientAdapter;
import com.open_source.worldwide.baking.Adapters.StepsAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;
import com.open_source.worldwide.baking.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.OnItemClickListener {

    private static final String TAG = RecipeDetailsFragment.class.toString();

    @BindView(R.id.recipe_details_rv)
    RecyclerView recipeDetailsRv;


    private StepsAdapter stepsAdapter;
    private IngredientAdapter ingredientAdapter;

    private int mRecipeId;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    // Store instance variables
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static RecipeDetailsFragment newInstance(int page, int recipeId) {
        RecipeDetailsFragment fragmentFirst = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.DETAILS_FRAGMENT_PAGE_ID, page);
        args.putInt(Constants.RECIPE_ID_KEY, recipeId);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        page = getArguments().getInt(Constants.DETAILS_FRAGMENT_PAGE_ID);
        mRecipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);

        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated: " + mRecipeId);



        if (page == 0) {

            handleIngredientsView(mRecipeId);
        } else {

            handleStepsView(mRecipeId);
        }

        recipeDetailsRv.setHasFixedSize(true);
        recipeDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void handleIngredientsView(int recipeId) {
        ArrayList<Ingredient> ingredients = JsonUtils.getRecipeIngredients(getActivity(), recipeId);

        ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        recipeDetailsRv.setAdapter(ingredientAdapter);
    }

    private void handleStepsView(int recipeId) {
        ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), recipeId);

        stepsAdapter = new StepsAdapter(getActivity(), steps, this);
        recipeDetailsRv.setAdapter(stepsAdapter);
    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        startActivity(intent);
    }

}
