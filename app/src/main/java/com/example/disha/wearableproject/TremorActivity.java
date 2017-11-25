package com.example.disha.wearableproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TremorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tremor_activity);

        // Find the View that shows the Tremor category
        TextView tremor = (TextView) findViewById(R.id.tvRest);

        // Set a click listener on that View
        tremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TremorActivity.this, TremorRestActivity.class));
            }
        });
    }
}
