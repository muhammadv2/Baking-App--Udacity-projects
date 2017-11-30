package com.open_source.worldwide.baking;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.open_source.worldwide.baking.recipe_details.DetailsActivity;
import com.open_source.worldwide.baking.recipes_main.RecipesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityIntentTest {

    @Rule
    public IntentsTestRule<RecipesActivity> intentsTestRule =
            new IntentsTestRule<>(RecipesActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).
                respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickSendEmailButton_SendsEmail() {

        onView(withId(R.id.recipes_rv))
                .perform(actionOnItemAtPosition(1, click()));


        intended(allOf(
                hasAction(DetailsActivity.SHOW_DETAILS_ACTION)));

    }
}
