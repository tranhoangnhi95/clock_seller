package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.immortal.clock_seller.R;

public class LoadingActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        inits();
        controls();
    }

    private void controls() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iSignIn = new Intent(LoadingActivity.this,SignInActivity.class);
                startActivity(iSignIn);
                finish();
            }
        },2000);
    }

    private void inits() {
        handler = new Handler();
    }
}
