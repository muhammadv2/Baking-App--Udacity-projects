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

    SimpleExoPlayer simpleExoPlayer;

    @BindView(R.id.step_details_description_tv)
    TextView detailsDescription;

    @BindView(R.id.step_previous_button)
    TextView previousStep;

    @BindView(R.id.step_next_step)
    TextView nextStep;

    private int stepId = 0;
    private int recipeId = 0;


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

        if (getArguments() != null) {
            recipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);
            stepId = getArguments().getInt(Constants.STEP_ID_KEY);
        }

        getStepDetailsAndSetTheContents();

    }

    private void getStepDetailsAndSetTheContents() {
        ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), recipeId);
        Step step = steps.get(stepId);

        String stepVideo = step.getVideoURL();
        String stepThumbnail = step.getThumbnailURL();

        if (!stepVideo.equals("")) {
            initializePlayer(Uri.parse(stepVideo));
        } else if (!stepThumbnail.equals("")) {
            initializePlayer(Uri.parse(stepThumbnail));
        } else {
            setDefaultImageIfNoVideo();
        }

        detailsDescription.setText(step.getDescription());

        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initializePlayer(Uri uri) {

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                getActivity(),
                new DefaultTrackSelector(), new DefaultLoadControl());

        exoPlayerView.setPlayer(simpleExoPlayer);

        simpleExoPlayer.setPlayWhenReady(true);

        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("Baking"),
                new DefaultExtractorsFactory(), null, null);
    }

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
        if (simpleExoPlayer != null)
            simpleExoPlayer.stop();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
