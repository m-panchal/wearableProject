package com.example.disha.wearableproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class CognitiveActivity extends AppCompatActivity {

    private static WebView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cognitive_activity);
        ImageView play = (ImageView) findViewById(R.id.playButton);


        instructions = (WebView) findViewById(R.id.instruct);

        String htmlText = " %s ";
        String myData = "<html><body  style=\"text-align:justify;\">";
        myData += "1. Posture: Place the smartphone on a flat table, while sitting.<br /> ";
        myData += "2. You will see paired colors and shapes which appear at the top of the screen and serve as a ‘‘legend’’ for the task.<br /> ";
        myData += "3. At the bottom of the screen are colored ‘‘pads’’ which correspond to colors in the legend.<br /> ";
        myData += "4. When the task is initiated, random shapes from the legend appear one at a time in the center of the screen.<br /> ";
        myData += "5. Choose the appropriate color from the bottom.<br /> ";
        myData += "</body></html>";

        instructions.loadData(String.format(htmlText, myData), "text/html", "utf-8");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CognitiveActivity.this, CognitiveColorShapeTestActivity.class));
                finish();
            }
        });
    }
}
