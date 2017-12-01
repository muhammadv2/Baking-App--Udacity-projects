package com.open_source.worldwide.baking.recipe_details;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.JsonUtils;
import com.open_source.worldwide.baking.R;
import com.open_source.worldwide.baking.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.simple_exoplayer_view)
    SimpleExoPlayerView exoPlayerView;

    private SimpleExoPlayer simpleExoPlayer;

    @BindView(R.id.step_details_description_tv)
    TextView detailsDescription;

    @BindView(R.id.step_previous_button)
    TextView previousStep;

    @BindView(R.id.step_next_step)
    TextView nextStep;



    private int mStepId = 0;
    private int mRecipeId = 0;

    public StepDetailsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //update the value of mRecipeId and mStepId with the passed values from the constructor
        if (getArguments() != null) {
            mRecipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);
            mStepId = getArguments().getInt(Constants.STEP_ID_KEY);
        }

        getStepDetailsAndSetTheContents();

    }

    private void getStepDetailsAndSetTheContents() {
        //get all the steps objects from the json associated with it
        final ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), mRecipeId);
        Step step = steps.get(mStepId); // get the correct step with the help of passed mStepId

        //extract the video url that could be saved in different keys
        String stepVideo = step.getVideoURL();
        String stepThumbnail = step.getThumbnailURL();

        //because that url maybe saved in different keys check each string and use the one populated
        //to set the correct video using the help of ExoPlayer library
        if (!stepVideo.equals("")) {
            initializePlayer(Uri.parse(stepVideo));
        } else if (!stepThumbnail.equals("")) {
            initializePlayer(Uri.parse(stepThumbnail));
        } else {
            setDefaultImageIfNoVideo();
        }

        //setting the previous button
        detailsDescription.setText(step.getDescription());

        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStepId > 0)
                    --mStepId;

                getStepDetailsAndSetTheContents();

            }
        });

        //setting the next button
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStepId < steps.size() - 1)
                    ++mStepId;

                getStepDetailsAndSetTheContents();

            }
        });
    }

    /**
     * method that initialize exoPlayer and and set it on ExoPlayerView nad use media source built
     *
     * @param uri from the passed uri to prepare ExoPlayer
     */
    private void initializePlayer(Uri uri) {

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                getActivity(),
                new DefaultTrackSelector(), new DefaultLoadControl());

        exoPlayerView.setPlayer(simpleExoPlayer);

        simpleExoPlayer.setPlayWhenReady(true);

        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, true, false);
    }

    /**
     * use the default of the needed classes to construct MediaSource
     */
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("Baking"),
                new DefaultExtractorsFactory(), null, null);
    }

    /**
     * method help set a default art work if there's no video at all associated with the step
     */
    private void setDefaultImageIfNoVideo() {
        exoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.stew));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                getActivity(),
                new DefaultTrackSelector(), new DefaultLoadControl());

        exoPlayerView.hideController();
        exoPlayerView.setUseController(false);
        exoPlayerView.setPlayer(simpleExoPlayer);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


}
