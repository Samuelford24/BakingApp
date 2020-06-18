package com.samuelford48gmail.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(@NonNull final RecipeAdapter.RecipeAdapterViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        holder.title.setText(recipes.get(position).getName());

        holder.servings.setText( holder.itemView.getContext().getString(R.string.servings) + String.valueOf(recipes.get(position).getServings()));
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context,StepListActivity.class);
        intent.putExtra("Recipe",recipes.get(position));
       context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        if (recipes==null){
            return 0;
        }
        System.out.println(recipes.size());
            return recipes.size();

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
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
