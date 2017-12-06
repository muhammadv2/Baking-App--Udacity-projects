package com.open_source.worldwide.baking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MainViewHolder> {

    //fields to be set to the passed data coming from associated activity
    private final Context mContext;
    private final OnItemClickListener mItemClickListener;
    private final ArrayList<Recipe> mRecipes;

    /**
     * Constructor that takes arguments helps set up the adapter
     *
     * @param context             to be used with Picasso library
     * @param recipes             list of recipes will be shown
     * @param onItemClickListener interface with onclick method to listen on adapter item clicks
     */
    public MainScreenAdapter(Context context, ArrayList<Recipe> recipes, OnItemClickListener onItemClickListener) {
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

        //extract the recipe associated with the returned position
        Recipe recipe = mRecipes.get(position);

        if (recipe == null) return;


        if (!recipe.getmImageUrl().equals("")) {
            Picasso.with(mContext)
                    .load(recipe.getmImageUrl())
                    .fit()
                    .into(holder.recipeImage);

        } else {
            //use already added images to populate the image view of the main screen
            setImageOnRecipes(position, holder.recipeImage);
        }
        String servings = " Servings " + recipe.getServings();

        //extract and set all necessary data on views
        holder.recipeName.setText(recipe.getName());
        holder.recipeServing.setText(servings);

    }


    private void setImageOnRecipes(int position, ImageView recipeImage) {

        //switch between positions to set the write image using the help of Picasso library
        switch (position) {
            case 0:
                Picasso.with(mContext).load(R.drawable.nutella_1).into(recipeImage);
                break;
            case 1:
                Picasso.with(mContext).load(R.drawable.choco_2).into(recipeImage);
                break;
            case 2:
                Picasso.with(mContext).load(R.drawable.yellow_3).into(recipeImage);
                break;
            case 3:
                Picasso.with(mContext).load(R.drawable.straw_4).into(recipeImage);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //find all the views
        @BindView(R.id.main_recipe_iv)
        ImageView recipeImage;
        @BindView(R.id.main_recipe_tv)
        TextView recipeName;
        @BindView(R.id.recipe_serving_tv)
        TextView recipeServing;


        MainViewHolder(View itemView) {
            super(itemView);

            //setting onClickListener on the view passed to the view holder
            itemView.setOnClickListener(this);
            //bind ButterKnife
            ButterKnife.bind(this, itemView);

        }

        /**
         * onClick method only returning the current position of the adapter
         */
        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(getAdapterPosition());
        }
    }
}
