package com.open_source.worldwide.baking.recipe_details;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.open_source.worldwide.baking.Adapters.IngredientAdapter;
import com.open_source.worldwide.baking.Adapters.StepsAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;
import com.open_source.worldwide.baking.models.Step;
import com.open_source.worldwide.baking.widget.RecipeWidget;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.OnItemClickListener {

    @BindView(R.id.recipe_details_rv)
    RecyclerView recipeDetailsRv;

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

            handleIngredientsView(mRecipeId);
        } else {

            handleStepsView(mRecipeId);
        }

        recipeDetailsRv.setHasFixedSize(true);
        recipeDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void handleIngredientsView(int recipeId) {
        ArrayList<Ingredient> ingredients = JsonUtils.getRecipeIngredients(getActivity(), recipeId);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        recipeDetailsRv.setAdapter(ingredientAdapter);
    }

    private void handleStepsView(int recipeId) {
        ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), recipeId);

        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), steps, this);
        recipeDetailsRv.setAdapter(stepsAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_as_widget_button) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Constants.RECIPE_ID_KEY, mRecipeId);
            editor.apply();

            Intent intent = new Intent(getActivity(), RecipeWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            int[] ids = AppWidgetManager.getInstance(getActivity())
                    .getAppWidgetIds(new ComponentName(getActivity(), RecipeWidget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getActivity().sendBroadcast(intent);
        }
        return super.onOptionsItemSelected(item);
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
            startActivity(intent);
        } else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.STEP_ID_KEY, position);
            bundle.putInt(Constants.RECIPE_ID_KEY, mRecipeId);

            stepDetailsFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.step_details_container, stepDetailsFragment).
                    commit();
        }
    }


}