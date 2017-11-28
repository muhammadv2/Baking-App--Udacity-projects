package com.open_source.worldwide.baking.recipe_details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.ingredient_rv)
    RecyclerView ingredientRv;
    @BindView(R.id.steps_rv)
    RecyclerView stepsRv;

    private StepsAdapter stepsAdapter;
    private IngredientAdapter ingredientAdapter;

    private int mRecipeId;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);

        handleStepsView(mRecipeId);

        handleIngredientsView(mRecipeId);
    }

    private void handleIngredientsView(int recipeId) {

        ArrayList<Ingredient> ingredients = JsonUtils.getRecipeIngredients(getActivity(), recipeId);

        ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        ingredientRv.setAdapter(ingredientAdapter);
        ingredientRv.setHasFixedSize(true);
        ingredientRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void handleStepsView(int recipeId) {

        ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), recipeId);

        stepsAdapter = new StepsAdapter(getActivity(), steps, this);
        stepsRv.setAdapter(stepsAdapter);
        stepsRv.setHasFixedSize(true);
        stepsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(int position) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
