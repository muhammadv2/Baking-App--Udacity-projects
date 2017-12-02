package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.open_source.worldwide.baking.Adapters.RecipeDetailsPagerAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {
//        implements FragmentManager.OnBackStackChangedListener {

    //Action to distinguish between intents coming from RecipesFragment and RecipesDetailsFragment
    public static final String SHOW_DETAILS_ACTION = "details";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //save a reference of the coming intent
        final Intent receivedIntent = getIntent();

        //binding butterKnife
        ButterKnife.bind(this);

        //getting the recipe name from the intent to set it on the ActionBar
        String title = receivedIntent.getStringExtra(Constants.RECIPE_NAME);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        //get the recipeId key
        int recipeId = receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1);

        //get a reference of the RecipeDetailsPagerAdapter passing the recipe id to help the adapter
        //set the right content over the views
        final RecipeDetailsPagerAdapter adapter =
                new RecipeDetailsPagerAdapter(getSupportFragmentManager(), recipeId);


        //getting a reference of FrameLayout that will hold the fragment
        final FrameLayout frameLayout = findViewById(R.id.step_details_container);

        //check if the device is a tablet and in landscape mode
        if (getResources().getBoolean(R.bool.isTablet)) {
            //set the adapter on the view pager
            mViewPager.setAdapter(adapter);

            //listen to the page selected on view pager if steps page show the content of the step
            //if not hide it
            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {

                    if (position == 1) {
                        if (getResources().getBoolean(R.bool.isTablet)) {
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        frameLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {

            //check if the intent has the action of show details action
            if (receivedIntent.getAction() == SHOW_DETAILS_ACTION) {
                //set the adapter on the view pager
                mViewPager.setAdapter(adapter);

            } else {
                //if not set the visibility view pager to gone and show the details fragment
                mViewPager.setVisibility(View.GONE);
                Log.i("DetailsActivity", "onCreate: ");
                showStepDetailsFragment(savedInstanceState, receivedIntent);
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //using the help of this answer to figure how to fire onUpdate method in Widget class
        //https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
//        if (item.getItemId() == R.id.add_as_widget_button) {
//            Log.i(DetailsActivity.class.toString(), "onOptionsItemSelected: ");

//            Intent intent = new Intent(this, RecipeWidget.class);
//            intent.putExtra(Constants.RECIPE_ID_KEY, 1);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
//            // since it seems the onUpdate() is only fired on that:
//            int[] ids = AppWidgetManager.getInstance(getApplication())
//                    .getAppWidgetIds(new ComponentName(getApplication(), WidgetServices.class));
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//            sendBroadcast(intent);

//        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Method using the intent coming from RecipeDetailsFragment to populate the details fragment
     * with its data and using the SupportFragmentManager to set the fragment
     */
    private void showStepDetailsFragment(Bundle savedInstanceState, Intent receivedIntent) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.STEP_ID_KEY,
                receivedIntent.getIntExtra(Constants.STEP_ID_KEY, 0));
        bundle.putInt(Constants.RECIPE_ID_KEY,
                receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, 0));

        stepDetailsFragment.setArguments(bundle);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            if (getSupportActionBar() != null && !getResources().getBoolean(R.bool.isTablet))
                getSupportActionBar().hide();
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }

}
