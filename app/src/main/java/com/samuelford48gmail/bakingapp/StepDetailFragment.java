package com.samuelford48gmail.bakingapp;

import android.content.Context;
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
        // Show the dummy content as text in a TextView.
        recyclerView = rootView.findViewById(R.id.step_detailRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle = getArguments();


        recipe = bundle.getParcelable("Recipe");

        recyclerView.setAdapter(new StepDetailAdapter(this, recipe));
        return rootView;
    }

    public static class StepDetailAdapter
            extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static int TYPE_VIDEO = 0;
        private static int TYPE_STEP_DESCRIPTION = 1;
        private final StepDetailFragment mParentActivity;
        public Recipe recipe;

        /*
                private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(StepDetailFragment.ARG_ITEM_ID, item.id);
                            StepDetailFragment fragment = new StepDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.step_detail_container, fragment)
                                    .commit();
                        } else {

                            Context context = view.getContext();
                            Intent intent = new Intent(context, StepDetailActivity.class);
                            intent.putExtra(StepDetailFragment.ARG_ITEM_ID, item.id);

                            context.startActivity(intent);
                        }
                    }
                };
        */
        StepDetailAdapter(StepDetailFragment parent,
                          Recipe recipe) {
            this.recipe = recipe;
            mParentActivity = parent;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layoutId;

            if (viewType == TYPE_VIDEO) {
                layoutId = R.layout.video_list_item;
                View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

                view.setFocusable(true);

                return new VideoViewHolder(view);
            } else {
                layoutId = R.layout.step_list_content;
                View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                view.setFocusable(true);
                return new StepViewHolder(view);
            }


            // View view = LayoutInflater.from(parent.getContext())
            //      .inflate(R.layout.step_list_content, parent, false);

        }

       // @Override
        //public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       //     ((StepViewHolder) holder).description.setText("sdf");
       // }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final Context context = holder.itemView.getContext();
            if (getItemViewType(position) == TYPE_VIDEO) {
                SimpleExoPlayer player = new SimpleExoPlayer.Builder(holder.itemView.getContext()).build();
                Uri uri = null;

                try {
                    uri = RecipeUtils.getVideoURI(recipe.getSteps(),position);

                    if (uri==null){
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((VideoViewHolder) holder).playerView.setPlayer(player);
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                        Util.getUserAgent(context, context.getApplicationInfo().loadLabel(context.getPackageManager()).toString()));
// This is the MediaSource representing the media to be played.
                MediaSource videoSource =
                        new ProgressiveMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(uri);
// Prepare the player with the source.
                player.prepare(videoSource);

            } else {
                try {
                    ((StepViewHolder) holder).description.setText(RecipeUtils.getStepsDescription(recipe.getSteps(), position));;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    ((StepViewHolder) holder).title.setText(RecipeUtils.getStepsTitle(recipe.getSteps(), position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }/*if (mTwoPane) {
                if (position>=1){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle argument = new Bundle();
                            // Intent intent = new Intent(context,StepDetailActivity.class);
                            argument.putParcelable("Recipe",recipe);
                            argument.putInt("Position", position);
                            StepDetailFragment fragment = new StepDetailFragment();
                            fragment.setArguments(argument);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.step_detail_container, fragment)
                                    .commit();
                        }

                    });
                    //   StepDetailFragment fragment = new StepDetailFragment();
                    //fragment.setArguments(arguments);
                    // mParentActivity.getSupportFragmentManager().beginTransaction()
                    //       .replace(R.id.step_detail_container, fragment)
                    //       .commit();
                }
                // Bundle arguments = new Bundle();
                // arguments.putString(StepDetailFragment.ARG_ITEM_ID, item.id);

            } else {

                if (position>=1){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, StepDetailActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("Recipe",recipe);

                            context.startActivity(intent);
                        }
                    });
                    }
                    }
                    */


        }

        @Override
        public int getItemViewType(int position) {
//      COMPLETED (10) Within getItemViewtype, if mUseTodayLayout is true and position is 0, return the ID for today viewType
            if (position == 0) {
                return TYPE_VIDEO;
//      COMPLETED (11) Otherwise, return the ID for future day viewType
            } else {
                return TYPE_VIDEO;
            }
        }


        @Override
        public int getItemCount() {

            return 1;
        }

        class StepViewHolder extends RecyclerView.ViewHolder {
            final TextView description, title;


            StepViewHolder(View view) {
                super(view);

                description = view.findViewById(R.id.content);
                title = view.findViewById(R.id.title);
            }
        }

        class VideoViewHolder extends RecyclerView.ViewHolder {

          public   PlayerView playerView;

            VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                playerView = itemView.findViewById(R.id.exoPlayer);
            }
        }

    }
}
