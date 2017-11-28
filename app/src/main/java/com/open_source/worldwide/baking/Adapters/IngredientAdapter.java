package com.open_source.worldwide.baking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Ingredient;

import java.util.ArrayList;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    Context mContext;
    ArrayList<Ingredient> mIngredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {

        mContext = context;
        mIngredients = ingredients;
    }


    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_card_view, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public IngredientViewHolder(View itemView) {
            super(itemView);
        }
    }
}
