package com.samuelford48gmail.bakingapp;

import androidx.test.espresso.IdlingResource;

public class RecipesRecyclerViewIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return RecipesRecyclerViewIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = MainActivity.backgroudWorkDone;
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}