package com.example.disha.wearableproject;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.disha.wearableproject.R.id.playButton;

public class TremorRestActivity extends AppCompatActivity {

    private static ImageButton playButton;
    private static TextView counter;
    private static CountDownTimer countDownTimer;
    private static Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tremor_rest);

        counter = (TextView) findViewById(R.id.tvCounter);
        playButton =(ImageButton)findViewById(R.id.playButton);
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter.setText(String.valueOf((20000-millisUntilFinished)/1000 + 1));
            }

            @Override
            public void onFinish() {
                //counter.setVisibility(View.GONE);
                thread.interrupt();
                counter.setText("Done!");
                //playButton.setVisibility(View.VISIBLE);
            }
        };

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(View.GONE);
                counter.setVisibility(View.VISIBLE);
                countDownTimer.start();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!Thread.interrupted());
                    }
                });
                thread.start();
            }
        });
    }
}
