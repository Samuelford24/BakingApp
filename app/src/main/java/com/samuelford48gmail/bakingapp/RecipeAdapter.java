package com.samuelford48gmail.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {



    ArrayList<Recipe> recipes;

    @NonNull
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    public static void updateWidget(Context context, String recipename) {
        System.out.println("updateWidget" + recipename);
        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra("Recipe", recipename);
        context.sendBroadcast(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeAdapter.RecipeAdapterViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        holder.title.setText(recipes.get(position).getName());

        holder.servings.setText(holder.itemView.getContext().getString(R.string.servings) + recipes.get(position).getServings());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final SharedPreferences mySharedPreferences = context.getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();


        try {
            editor.putString("Ingredients", RecipeUtils.getIngredientsForWidget(recipes.get(position).getIngredients())).apply();
            updateWidget(context, recipes.get(position).getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context,StepListActivity.class);
        intent.putExtra("Recipe",recipes.get(position));
       context.startActivity(intent);
    }
});
    }
    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title,servings;
        public RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.recipeTitle);
     servings=itemView.findViewById(R.id.amount);

        }
    }
    public void setRecipes(ArrayList<Recipe> recipes) {
        if (recipes != null) {
            this.recipes = recipes;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }

        return recipes.size();

    }
}
