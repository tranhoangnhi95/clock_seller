package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;

public class ProfileActivity extends AppCompatActivity {
    Toolbar tb_Profile;
    TextView txt_Name,txt_Phone, txt_Email, txt_Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inits();
        controls();
    }

    private void controls() {

    }

    private void inits() {
        tb_Profile = findViewById(R.id.tb_Profile);
        txt_Name = findViewById(R.id.txt_PfName);
        txt_Phone = findViewById(R.id.txt_PfPhone);
        txt_Email = findViewById(R.id.txt_PfEmail);
        txt_Address = findViewById(R.id.txt_PfAdrress);
        txt_Name.setText(SignInActivity.user.getName());
        txt_Phone.setText(SignInActivity.user.getPhone());
        txt_Email.setText(SignInActivity.user.getEmail());
        txt_Address.setText(SignInActivity.user.getAddress());
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
