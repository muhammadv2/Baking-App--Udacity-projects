package com.open_source.worldwide.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.open_source.worldwide.baking.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MainViewHolder> {

    private static final String TAG = MainScreenAdapter.class.toString();

    private Context mContext;
    private final OnItemClickListener mItemClickListener;
    private ArrayList<Recipe> mRecipes;

    public MainScreenAdapter(Context context, ArrayList<Recipe> recipes, OnItemClickListener onItemClickListener) {

        Log.i(TAG, "MainScreenAdapter: " + recipes);
        mContext = context;
        mRecipes = recipes;
        mItemClickListener = onItemClickListener;
    }

    /**
     * interface that will define our listener
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }

    /**
     * To create our ViewHolder by inflating our XML and returning a new MovieVieHolder
     */
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipes_card_view, parent, false);

        return new MainViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        Recipe recipe = mRecipes.get(position);

        if (recipe == null) return;
        Picasso.with(mContext).load(R.drawable.hellobaking).into(holder.recipeImage);

        holder.recipeName.setText(recipe.getName());

        Log.i(TAG, "onBindViewHolder: " + recipe.getImage());


    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.main_recipe_iv)
        ImageView recipeImage;

        @BindView(R.id.main_recipe_tv)
        TextView recipeName;

        @BindView(R.id.main_recipe_like_btn)
        Button recipeLikeButton;

        MainViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(getAdapterPosition());
        }
    }
}
