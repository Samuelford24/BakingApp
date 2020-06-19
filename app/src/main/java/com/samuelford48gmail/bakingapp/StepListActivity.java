package com.samuelford48gmail.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Property;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import org.json.JSONException;

import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);


 Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("Recipe");

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null && recipe!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

       RecyclerView recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,recipe,mTwoPane));

    }

  /*  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, r, mTwoPane));
    }*/
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()){
          case android.R.id.home:
              onBackPressed();
              return true;
      }
      return super.onOptionsItemSelected(item);
  }
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        public final Recipe recipe;
        private final boolean mTwoPane;
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
        SimpleItemRecyclerViewAdapter(StepListActivity parent,
                                      Recipe recipe,
                                      boolean twoPane) {
           this.recipe=recipe;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Context context = holder.itemView.getContext();
            if (position==0){
                try {
                    holder.description.setText(RecipeUtils.getIngredients(recipe.getIngredients()));
                    holder.title.setText(holder.itemView.getContext().getString(R.string.IngredientsTitle));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    holder.description.setText(RecipeUtils.getStepsDescription(recipe.getSteps(),position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    holder.title.setText(RecipeUtils.getStepsTitle(recipe.getSteps(),position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }if (mTwoPane) {
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
                            intent.putExtra("Position", position);
                            intent.putExtra("Recipe",recipe);

                            context.startActivity(intent);
                        }
                    });
                }

            }

        }

        @Override
        public int getItemCount() {
            try {

               return RecipeUtils.getStepsSize(recipe.getSteps());
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView description,title;


            ViewHolder(View view) {
                super(view);

                description = view.findViewById(R.id.content);
                title = view.findViewById(R.id.title);
            }
        }
    }
}
