package com.samuelford48gmail.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class StepDetailFragmentTest {
    @Rule
    public IntentsTestRule<StepListActivity> mainActivityActivityTestRule = new IntentsTestRule<StepListActivity>(StepListActivity.class);

    @Test
    public void clickButtons() {

//the next button should not be clickable if the last step is clicked on and the previous button should not be clickable if the first step is clicked on
        onView(withId(R.id.next)).check(matches(isClickable()));

        onView(withId(R.id.previous)).check(matches(isClickable()));
    }
}
