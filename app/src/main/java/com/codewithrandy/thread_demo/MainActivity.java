package com.codewithrandy.thread_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithrandy.thread_demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button btnStart, btnStop, btnLaunch;
    private TextView tvCounter;
    private boolean countdownComplete, countdownCancelled, isCounting;
    private int counter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        
        btnStart = binding.btnStart;
        btnStop = binding.btnStop;
        btnLaunch = binding.btnLaunch;
        tvCounter = binding.tvCounter;
        
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCounting) {
                    Countdown countdown = new Countdown(10);
                    new Thread(countdown).start();
                }
            }
        });
        
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countdownCancelled = true;
            }
        });
        
        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countdownComplete) {
                    Toast.makeText(MainActivity.this, "LIFT OFF !!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    class Countdown implements Runnable {
        int seconds;
        public Countdown(int seconds) {
            this.seconds = seconds;
        }
        @Override
        public void run() {
            countdownCancelled = false;
            countdownComplete = false;
            isCounting = true;
            for (int i = seconds; i > 0; i--) {
                if(countdownCancelled) {
                    isCounting = false;
                    return;
                }
                try {
                    Thread.sleep(1000);
                    counter = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCounter.setText(String.valueOf(counter));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countdownComplete = true;
            isCounting = false;
        }
    }
}