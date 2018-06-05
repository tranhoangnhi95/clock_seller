package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.immortal.clock_seller.R;

public class ProfileActivity extends AppCompatActivity {
    Toolbar tb_Profile;
    TextView txt_Name, txt_Phone, txt_Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inits();
    }

    private void inits() {
        tb_Profile = findViewById(R.id.tb_Profile);
        txt_Name = findViewById(R.id.txt_PfName);
        txt_Phone = findViewById(R.id.txt_PfPhone);
        txt_Address = findViewById(R.id.txt_PfAdrress);
        setSupportActionBar(tb_Profile);
        loadingActionBar();
        setTitle("Tài khoản của bạn");
    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_history:
                Intent i_ToHistory = new Intent(ProfileActivity.this,HistoryActivity.class);
                startActivity(i_ToHistory);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
