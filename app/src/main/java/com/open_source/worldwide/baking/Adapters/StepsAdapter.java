package com.open_source.worldwide.baking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private final ArrayList<Step> mSteps;
    private final OnItemClickListener mItemclickListener;

    public StepsAdapter(Context context, ArrayList<Step> steps, OnItemClickListener itemClickListener) {

        mSteps = steps;
        mItemclickListener = itemClickListener;
    }

    /**
     * interface that will define our listener
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_card_view, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {

        Step step = mSteps.get(position);

        if (step == null) return;

        holder.stepDescription.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_tv)
        TextView stepDescription;
        @BindView(R.id.step_iv)
        ImageView stepView;


        public StepsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemclickListener.onClick(getAdapterPosition());

        }
    }
}
