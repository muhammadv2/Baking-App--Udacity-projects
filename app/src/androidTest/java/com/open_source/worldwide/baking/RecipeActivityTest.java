package com.open_source.worldwide.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.open_source.worldwide.baking.recipes_main.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> recipesActivityActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);


    @Test
    public void testClickingOnListViews() {

        onView(withId(R.id.recipes_rv))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.pager_header)).perform(swipeLeft());

        onView(allOf(isDisplayed(), withId(R.id.recipe_details_rv)))
                .perform(actionOnItemAtPosition(0, click()));

    }

}
