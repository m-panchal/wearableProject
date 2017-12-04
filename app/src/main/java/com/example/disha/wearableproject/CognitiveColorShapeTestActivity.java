package com.example.disha.wearableproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class CognitiveColorShapeTestActivity extends AppCompatActivity {

    private Integer[] shapeList = {R.drawable.triangle,
                                   R.drawable.right_arrow,
                                   R.drawable.circle,
                                   R.drawable.diamond,
                                   R.drawable.pentagon,
                                   R.drawable.star};
    private int shapeListSize;
    private Random rand;
    private Integer expected;
    private int count = 0;
    private ImageView centerShape;

    public static final int BLUE = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int YELLOW = 3;
    public static final int ORANGE = 4;
    public static final int PURPLE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cognitive_color_shape_test_activity);

        centerShape = (ImageView) findViewById(R.id.centerShape);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 20000);

        rand = new Random();
        shapeListSize = shapeList.length;
        expected = rand.nextInt(shapeListSize-1);
        centerShape.setImageResource(shapeList[expected]);

        ((ImageView) findViewById(R.id.ivBlue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(BLUE);
            }
        });

        ((ImageView) findViewById(R.id.ivRed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(RED);
            }
        });

        ((ImageView) findViewById(R.id.ivGreen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(GREEN);
            }
        });

        ((ImageView) findViewById(R.id.ivYellow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(YELLOW);
            }
        });

        ((ImageView) findViewById(R.id.ivOrange)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(ORANGE);
            }
        });

        ((ImageView) findViewById(R.id.ivPurple)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(PURPLE);
            }
        });
    }

    public void handleClick(int received) {
        if (expected.equals(received))
            count++;

        received = expected;
        while (expected.equals(received))
            expected = rand.nextInt(shapeListSize-1);
        centerShape.setImageResource(shapeList[expected]);;
    }
}
