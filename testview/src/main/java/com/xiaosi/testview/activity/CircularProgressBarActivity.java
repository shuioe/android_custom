package com.xiaosi.testview.activity;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaosi.customtools.views.CircularProgressBar;
import com.xiaosi.testview.R;

public class CircularProgressBarActivity extends AppCompatActivity {

    private CircularProgressBar circularProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_progress_bar);
        circularProgressBar = findViewById(R.id.progressView);

        new CountDownTimer(10000, 100) { // 10秒倒计时
            int progress = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                progress = 100 - (int) (millisUntilFinished / 100);
                circularProgressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                circularProgressBar.setProgress(100);
            }
        }.start();
    }
}
