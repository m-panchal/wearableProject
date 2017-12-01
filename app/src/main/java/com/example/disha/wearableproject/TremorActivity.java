package com.example.disha.wearableproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TremorActivity extends AppCompatActivity {


    private TextView restTremor, posturalTremor, intensionTremor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tremor_activity);


        posturalTremor = (TextView) findViewById(R.id.tvPostural);
        restTremor = (TextView) findViewById(R.id.tvRest);
        intensionTremor = (TextView) findViewById(R.id.tvIntention);

        // Set a click listener
        restTremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TremorRestActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Set a click listener
        posturalTremor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TremorPosturalActivity.class);
                startActivity(i);
                finish();
            }
        });


        // Set a click listener on that View
        intensionTremor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TremorIntentionActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Find the View that shows the Tremor category
        TextView kineticTremor = (TextView) findViewById(R.id.tvKinetic);
        // Set a click listener on that View
        kineticTremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TremorActivity.this, TremorKineticActivity.class));
            }
        });
    }
}
