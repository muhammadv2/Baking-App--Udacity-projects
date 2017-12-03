package com.open_source.worldwide.baking.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.open_source.worldwide.baking.R;

public class AppWidgetConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Appwidget", "onCreate: ");
    }
}
