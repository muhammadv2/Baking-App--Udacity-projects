package com.open_source.worldwide.baking.recipe_details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Bitmap> {

    @BindView(R.id.simple_exoplayer_view)
    SimpleExoPlayerView exoPlayerView;

    private SimpleExoPlayer simpleExoPlayer;

    @BindView(R.id.step_details_description_tv)
    TextView detailsDescription;

    @BindView(R.id.step_previous_button)
    TextView previousStep;

    @BindView(R.id.step_next_step)
    TextView nextStep;

    @BindView(R.id.thumbnail_step_details)
    ImageView thumbnail;

    @BindView(R.id.progress_loading)
    ProgressBar progressBar;


    private int mStepId;
    private int mRecipeId;

    private long playerCurrentPosition;
    private boolean isPlayWhenReady;

    private String mUrl;

    public StepDetailsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (simpleExoPlayer != null) {
            playerCurrentPosition = simpleExoPlayer.getCurrentPosition();
            isPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
        }

        outState.putBoolean(Constants.PLAYER_CURRENT_STATE, isPlayWhenReady);
        outState.putLong(Constants.PLAYER_CURRENT_POSITION, playerCurrentPosition);

        outState.putInt(Constants.RECIPE_ID_KEY, mRecipeId);
        outState.putInt(Constants.STEP_ID_KEY, mStepId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
            getActivity().getActionBar().setHomeAsUpIndicator(null);
        }

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
        if (getArguments() != null && savedInstanceState == null) {

            mRecipeId = getArguments().getInt(Constants.RECIPE_ID_KEY);
            mStepId = getArguments().getInt(Constants.STEP_ID_KEY);
        }

        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(Constants.RECIPE_ID_KEY);
            mStepId = savedInstanceState.getInt(Constants.STEP_ID_KEY);
            isPlayWhenReady = savedInstanceState.getBoolean(Constants.PLAYER_CURRENT_STATE);
            playerCurrentPosition = savedInstanceState.getLong(Constants.PLAYER_CURRENT_POSITION);
        }

        getStepDetailsAndSetTheContents();

    }

    private void getStepDetailsAndSetTheContents() {
        //get all the steps objects from the json associated with it
        final ArrayList<Step> steps = JsonUtils.getStepsFromJson(getActivity(), mRecipeId);
        Step step = steps.get(mStepId); // get the correct step with the help of passed stepId

        //extract the video url that could be saved in different keys
        String stepVideo = step.getVideoURL();
        final String stepThumbnail = step.getThumbnailURL();

        //because that url maybe saved in different keys check each string and use the one populated
        //to set the correct video using the help of ExoPlayer library
        if (!stepVideo.equals("")) {
            exoPlayerView.setVisibility(View.VISIBLE);
            thumbnail.setVisibility(View.GONE);
            initializePlayer(Uri.parse(stepVideo));
        } else if (!stepThumbnail.equals("")) {
            exoPlayerView.setVisibility(View.GONE);
            thumbnail.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            mUrl = stepThumbnail;
            getActivity().getSupportLoaderManager().initLoader(22, null, this);
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

                playerCurrentPosition = 0;
                isPlayWhenReady = false;
                getStepDetailsAndSetTheContents();

            }
        });

        //setting the next button
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStepId < steps.size() - 1)
                    ++mStepId;

                playerCurrentPosition = 0;
                isPlayWhenReady = false;
                getStepDetailsAndSetTheContents();

            }
        });
    }


    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new ImageAsyncTask(getActivity(), mUrl);

    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {

        progressBar.setVisibility(View.GONE);
        if (data != null)
            thumbnail.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }


    private static class ImageAsyncTask extends AsyncTaskLoader<Bitmap> {

        String url;

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        ImageAsyncTask(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        public Bitmap loadInBackground() {
            Bitmap bitmap = null;
            MediaMetadataRetriever mediaMetadataRetriever = null;
            try {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
                bitmap = mediaMetadataRetriever.getFrameAtTime();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
            }
            return bitmap;
        }
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

        exoPlayerView.showController();
        exoPlayerView.setUseController(true);

        simpleExoPlayer.seekTo(playerCurrentPosition);
        simpleExoPlayer.setPlayWhenReady(isPlayWhenReady);

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
    public void onDestroy() {
        super.onDestroy();
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
