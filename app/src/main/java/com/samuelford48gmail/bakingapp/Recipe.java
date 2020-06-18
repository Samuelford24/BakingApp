package com.samuelford48gmail.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Property;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class Recipe implements Parcelable {
    String name;
    int id;
    String ingredients;
    String steps;
int servings;


    public Recipe(String name, int id, String ingredients, String steps, int servings) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }




    public void writeToParcel(Parcel dest, int flags){
        //write all properties to the parcle
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(ingredients);
        dest.writeString(steps);
        dest.writeInt(servings);
    }

    //constructor used for parcel
    public Recipe(Parcel parcel){
        //read and set saved values from parcel
        name =parcel.readString();
        id = parcel.readInt();
        ingredients= parcel.readString();
        steps=parcel.readString();
        servings=parcel.readInt();


    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public static Creator<Recipe> getCREATOR() {
        return CREATOR;
    }
}
