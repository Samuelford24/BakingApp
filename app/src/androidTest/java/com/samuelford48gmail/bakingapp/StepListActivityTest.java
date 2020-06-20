package com.samuelford48gmail.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class StepListActivityTest {
    @Rule
    public IntentsTestRule<StepListActivity> mainActivityActivityTestRule = new IntentsTestRule<StepListActivity>(StepListActivity.class);

    @Test
    public void clickGridItem_OpenRecipeViewActivity() {


        onView(withId(R.id.step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(StepDetailFragment.class.getName()));
    }
}
