package com.example.immortal.clock_seller.activity;

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

    /**
     * Thêm các sự kiện lắng nghe, điều khiển
     */
    private void controls() {
        //Dừng ở màn hình splash 2 giây sau đó chuyển sang màn hình đăng nhập
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iSignIn = new Intent(LoadingActivity.this, SignInActivity.class);
                startActivity(iSignIn);
                finish();
            }
        }, 2000);
    }

    /**
     * Ánh xạ các view và khỏi tạo giá trị
     */
    private void inits() {
        handler = new Handler();
    }
}
