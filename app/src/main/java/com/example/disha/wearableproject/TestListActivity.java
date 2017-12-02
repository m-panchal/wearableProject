package com.example.disha.wearableproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        // Find the View that shows the Tremor category
        TextView tremor = (TextView) findViewById(R.id.tvTremor);

        // Set a click listener on that View
        tremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, TremorActivity.class));
            }
        });

        // Find the View that shows the Bradykinesia category
        TextView bradykinesia = (TextView) findViewById(R.id.tvBradykinesia);

        // Set a click listener on that View
        bradykinesia.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, BradykinesiaActivity.class));
            }
        });

        // Find the View that shows the balance category
        TextView balance = (TextView) findViewById(R.id.tvBalance);

        // Set a click listener on that View
        balance.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(TestListActivity.this, BalanceActivity.class));
            }
        });

        // Find the View that shows the gait category
        TextView gait = (TextView) findViewById(R.id.tvGait);

        // Set a click listener on that View
        gait.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(TestListActivity.this, GaitActivity.class));
            }
        });

        // Find the View that shows the cognitive category
        TextView cognitive = (TextView) findViewById(R.id.tvCognitive);

        // Set a click listener on that View
        cognitive.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, CognitiveActivity.class));
            }
        });
    }
}
