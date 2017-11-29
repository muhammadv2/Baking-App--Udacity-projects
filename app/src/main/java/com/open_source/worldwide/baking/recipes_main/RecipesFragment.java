package com.open_source.worldwide.baking.recipes_main;

import android.content.Context;
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
import com.open_source.worldwide.baking.recipe_details.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements MainScreenAdapter.OnItemClickListener {

    private static final String TAG = RecipesFragment.class.toString();

    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    MainScreenAdapter mAdapter;

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



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

        mAdapter = new MainScreenAdapter(getActivity(),
                JsonUtils.getRecipesFromJson(getActivity()), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        if (isTablet(getActivity())) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.RECIPE_ID_KEY, position);
        intent.setAction(DetailsActivity.SHOW_DETAILS_ACTION);
        startActivity(intent);

    }



}
