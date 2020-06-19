package com.samuelford48gmail.bakingapp;

import android.net.Uri;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class RecipeUtils {
    public static ArrayList<String> getRecipeNames(String response) throws JSONException {
        ArrayList<String> recipeNames = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            recipeNames.add(jsonObject.getString("name"));
        }
        return recipeNames;
    }

    public static ArrayList<Recipe> getRecipes(String response) throws JSONException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            recipes.add(new Recipe(jsonObject.getString("name"), jsonObject.getInt("id"), jsonObject.getJSONArray("ingredients").toString().trim(), jsonObject.getJSONArray("steps").toString().trim(), jsonObject.getInt("servings")));
        }
        return recipes;
    }

    public static String getIngredients(String ingredientsArray) throws JSONException {
        String ingredients="";
        JSONArray jsonArray =new JSONArray(ingredientsArray);
        for (int j = 1; j<jsonArray.length()+1; j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j-1);
            ingredients += j + ". " + jsonObject.getString("quantity") + jsonObject.getString("measure") + " " + jsonObject.getString("ingredient") + "\n";
        }
        return ingredients;
    }

    public static String getIngredientsForWidget(String ingredientsArray) throws JSONException {
        String ingredients = "";
        JSONArray jsonArray = new JSONArray(ingredientsArray);
        for (int j = 1; j < jsonArray.length() + 1; j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j - 1);
            ingredients += j + ". " + jsonObject.getString("quantity") + jsonObject.getString("measure") + " " + jsonObject.getString("ingredient") + ",";
        }
        System.out.println("ingredients" + ingredients);
        return ingredients;
    }
    public static int getStepsSize(String stepsArray) throws JSONException{


        JSONArray jsonArray = new JSONArray(stepsArray);


        return jsonArray.length();
    }
    public static String getStepsDescription(String stepsArray, int postition) throws JSONException{
        String steps=null;
        JSONArray jsonArray = new JSONArray(stepsArray);
            JSONObject jsonObject = jsonArray.getJSONObject(postition);
            steps=jsonObject.getString("description");

        return steps;
    }
    public static String getStepsTitle(String stepsArray, int position) throws JSONException{
        String title=null;
        JSONArray jsonArray = new JSONArray(stepsArray);

            JSONObject jsonObject = jsonArray.getJSONObject(position);
            title=jsonObject.getString("shortDescription");

        return title;
    }
    public static Uri getVideoURI(String stepsArray,int position) throws JSONException{
        JSONArray jsonArray = new JSONArray(stepsArray);

        JSONObject jsonObject = jsonArray.getJSONObject(position);
       // URI.p
        Uri uri= Uri.parse(jsonObject.getString("videoURL"));
return uri;
    }
}
