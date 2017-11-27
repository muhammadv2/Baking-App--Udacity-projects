package com.open_source.worldwide.baking.recipes_main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.open_source.worldwide.baking.MainScreenAdapter;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements MainScreenAdapter.OnItemClickListener {

    private static final String TAG = RecipesFragment.class.toString();

    @BindView(R.id.main_recipe_container)
    RecyclerView mRecyclerView;

    MainScreenAdapter mAdapter;

    public static final String ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_SERVING = "servings";
    public static final String RECIPE_IMAGE = "image";

    private OnFragmentInteractionListener mListener;

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new MainScreenAdapter(getActivity(), getRecipesFromJson(getActivity()), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onClick(int position) {
    }

    public static ArrayList<Recipe> getRecipesFromJson(Context context) {

        Integer id;
        String recipeName;
        Integer recipeServing;
        String recipeImage;

        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset(context));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                id = jsonObject.optInt(ID);
                recipeName = jsonObject.optString(RECIPE_NAME);
                recipeServing = jsonObject.optInt(RECIPE_SERVING);
                recipeImage = jsonObject.getString(RECIPE_IMAGE);

                Log.i(TAG, "getRecipesFromJson: " + recipeName + "" + id);

                recipes.add(new Recipe(id, recipeName, recipeServing, recipeImage));

            }

        } catch (JSONException e) {
            Toast.makeText(context, "error loading json", Toast.LENGTH_SHORT).show();
        }

        return recipes;

    }

    @Nullable
    private static String loadJSONFromAsset(Context context) {

        String json;
        try {
            InputStream is = context.getAssets().open("Recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return json;

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
