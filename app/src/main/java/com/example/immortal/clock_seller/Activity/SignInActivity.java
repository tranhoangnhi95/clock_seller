package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.MyFragmentAdapter;
import com.example.immortal.clock_seller.AnimViewPager.ZoomOutPageTransfomer;
import com.example.immortal.clock_seller.R;

public class SignInActivity extends AppCompatActivity {
    TabLayout tl_Sliding;
    ViewPager vp_ViewPager;
    MyFragmentAdapter myFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inits();
    }

    private void inits() {
        tl_Sliding = findViewById(R.id.tl_SISliding);
        vp_ViewPager = findViewById(R.id.vp_SIViewPager);

        myFragmentAdapter = new MyFragmentAdapter(SignInActivity.this, getSupportFragmentManager());
        vp_ViewPager.setAdapter(myFragmentAdapter);
        vp_ViewPager.setPageTransformer(true, new ZoomOutPageTransfomer());
        tl_Sliding.setupWithViewPager(vp_ViewPager);

    }


}
