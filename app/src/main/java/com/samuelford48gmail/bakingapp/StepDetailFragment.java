package com.samuelford48gmail.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import org.json.JSONException;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    RecyclerView recyclerView;
    Recipe recipe;
    PlayerView playerView;
    int position, size;
    Button previous, next;
    SimpleExoPlayer player;
    TextView textView;

    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);


        playerView = rootView.findViewById(R.id.exoPlayer);
        textView = rootView.findViewById(R.id.stepDescription);
        Bundle bundle = getArguments();
        previous = rootView.findViewById(R.id.previous);
        next = rootView.findViewById(R.id.next);
        player = new SimpleExoPlayer.Builder(getContext()).build();
        if (savedInstanceState != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                textView.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                previous.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                previous.setVisibility(View.VISIBLE);
            }
            checkbuttonVisibility();
            recipe = savedInstanceState.getParcelable("Recipe");
            size = savedInstanceState.getInt("Size");
            position = savedInstanceState.getInt("Position");
            loadVideo();
            try {
                loadStepDescription();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recipe = bundle.getParcelable("Recipe");
        position = bundle.getInt("Position") - 1;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            textView.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
        }
        try {
            size = RecipeUtils.getStepsSize(recipe.getSteps());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkbuttonVisibility();
        try {
            loadStepDescription();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadVideo();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;

                checkbuttonVisibility();
                loadVideo();
                try {
                    loadStepDescription();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                checkbuttonVisibility();
                loadVideo();
                try {
                    loadStepDescription();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }


    private void loadStepDescription() throws JSONException {
        textView.setText(RecipeUtils.getStepsDescription(recipe.getSteps(), position));
    }

    private void loadVideo() {
        playerView.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getContext().getApplicationInfo().loadLabel(getContext().getPackageManager()).toString()));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                null;
        try {
            videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(RecipeUtils.getVideoURI(recipe.getSteps(), position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
// Prepare the player with the source.
        player.prepare(videoSource);
    }

    private void checkbuttonVisibility() {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
            if (position == 0) {
                previous.setVisibility(View.INVISIBLE);
            } else {
                previous.setVisibility(View.VISIBLE);
            }
            if (position == (size - 1)) {
                next.setVisibility(View.INVISIBLE);
            } else {
                next.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SIZE", size);
        savedInstanceState.putInt("POSITION", position);
        savedInstanceState.putParcelable("Recipe", recipe);
    }
}
