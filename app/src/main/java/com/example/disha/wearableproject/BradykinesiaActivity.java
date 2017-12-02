package com.example.disha.wearableproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.disha.wearableproject.R.string.posturalTremor;
import static com.example.disha.wearableproject.R.string.restTremor;

public class BradykinesiaActivity extends AppCompatActivity {

    private TextView rapidAltMove, fingerTapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bradykinesia_activity);

        rapidAltMove = (TextView) findViewById(R.id.tvRapidAltMove);
        fingerTapping = (TextView) findViewById(R.id.tvFingerTapping);

        // Set a click listener
        rapidAltMove.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            public void onClick(View v) {
                /*Intent i = new Intent(getApplicationContext(), TremorRestActivity.class);
                startActivity(i);
                finish();*/
            }
        });

        // Set a click listener
        fingerTapping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BradykinesiaFingerTapActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
