package com.open_source.worldwide.baking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    Context mContext;
    ArrayList<Ingredient> mIngredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {

        mContext = context;
        mIngredients = ingredients;
    }


    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the layout with ingredient card view and return it
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_card_view, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        //get the associated ingredient with the position passed to the adapter
        Ingredient ingredient = mIngredients.get(position);

        //extract and set all necessary data on views
        String quantity_measure = ingredient.getQuantity() + " " + ingredient.getMeasure();
        holder.ingredientMeasure.setText(quantity_measure);
        String ingredientDetails = "of " + ingredient.getIngredient();
        holder.ingredientMaterial.setText(ingredientDetails);

    }

    /**
     * return the list size to help set the adapter views
     */
    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        //find all the views needed
        @BindView(R.id.ingredient_measure_iv)
        ImageView ingredientView;
        @BindView(R.id.ingredient_measure_tv)
        TextView ingredientMeasure;
        @BindView(R.id.ingredient_material_tv)
        TextView ingredientMaterial;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            //binding butter knife to the view holder class to be able to find the views
            ButterKnife.bind(this, itemView);
        }
    }
}
