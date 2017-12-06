package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open_source.worldwide.baking.Adapters.IngredientAdapter;
import com.open_source.worldwide.baking.Adapters.StepsAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.loaders.IngredientsLoader;
import com.open_source.worldwide.baking.loaders.StepsLoader;
import com.open_source.worldwide.baking.models.Ingredient;
import com.open_source.worldwide.baking.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.OnItemClickListener {

    @BindView(R.id.recipe_details_rv)
    RecyclerView recipeDetailsRv;

    private int mRecipeId;

    ArrayList<Step> mSteps;


    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    private LoaderManager.LoaderCallbacks<ArrayList> ingredientLoader =
            new LoaderManager.LoaderCallbacks<ArrayList>() {
                @NonNull
                @Override
                public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
                    return new IngredientsLoader(getContext(), mRecipeId);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {

                    if (data != null) {
                        handleIngredientsView(data);
                    }

                }

                @Override
                public void onLoaderReset(Loader<ArrayList> loader) {

                }
            };

    private void handleIngredientsView(ArrayList<Ingredient> ingredients) {

        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        recipeDetailsRv.setAdapter(ingredientAdapter);
    }


    private LoaderManager.LoaderCallbacks<ArrayList> stepsLoader =
            new LoaderManager.LoaderCallbacks<ArrayList>() {
                @NonNull
                @Override
                public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
                    return new StepsLoader(getContext(), mRecipeId);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
                    if (data != null) {
                        handleStepsView(data);
                    }

                }

                @Override
                public void onLoaderReset(Loader<ArrayList> loader) {
                    handleStepsView(null);
                }
            };

    private void handleStepsView(ArrayList<Step> steps) {
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), steps, this);
        recipeDetailsRv.setAdapter(stepsAdapter);
        mSteps = steps;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        if (getArguments() != null) {
            page = getArguments().getInt(Constants.DETAILS_FRAGMENT_PAGE_ID);
            mRecipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);
        }

        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (page == 0) {

            getActivity().getSupportLoaderManager().initLoader(202, null, ingredientLoader);
        } else {

            getActivity().getSupportLoaderManager().initLoader(303, null, stepsLoader);
        }

        recipeDetailsRv.setHasFixedSize(true);
        recipeDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onClick(int position) {

        //checks if its tablet and in landscape if not just send a normal intent to open the details
        //activity again with the step position and if its replace the fragments shows the content
        //to be be populated with the new content dynamically
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(Constants.RECIPE_ID_KEY, mRecipeId);
            intent.putExtra(Constants.STEP_ID_KEY, position);
            intent.putExtra(Constants.STEP_Key, mSteps);
            Log.i("RecipeDetailsFragment", "onClick: " + mSteps);

            startActivity(intent);
        } else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.STEP_ID_KEY, position);
            bundle.putInt(Constants.RECIPE_ID_KEY, mRecipeId);
            bundle.putParcelableArrayList(Constants.STEP_Key, mSteps);
            Log.i("RecipeDetailsFragment", "onClick: " + mSteps);

            stepDetailsFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.step_details_container, stepDetailsFragment).
                    commit();
        }
    }


}