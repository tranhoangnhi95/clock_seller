package com.example.immortal.clock_seller.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {
    Toolbar tb_Profile;
    Button btn_Update;
    TextView txt_Name, txt_Phone, txt_Email, txt_Address;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inits();
        controls();
    }

    private void controls() {
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(SignInActivity.user);
            }
        });
    }

    private void updateProfile(final User user1) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View viewDial = inflater.inflate(R.layout.layout_update_profile, null);
        builder.setView(viewDial);

        final EditText txt_DName, txt_DPhone, txt_DAddress;
        Button btn_DCancel, btn_DCommit;
        txt_DName = viewDial.findViewById(R.id.txt_UdName);
        txt_DPhone = viewDial.findViewById(R.id.txt_UdPhone);
        txt_DAddress = viewDial.findViewById(R.id.txt_UdAddress);
//        btn_DCancel = viewDial.findViewById(R.id.btn_UdCancel);
//        btn_DCommit = findViewById(R.id.btn_UdCommit);

        txt_DName.setText(user1.getName());
        txt_DPhone.setText(user1.getPhone());
        txt_DAddress.setText(user1.getAddress());
        builder.setNegativeButton(
                "Hủy",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );
        builder.setPositiveButton(
                "Cập nhật",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String name, phone, address, mail;
                            mail = user1.getEmail();
                            mail = mail.replace("@","");
                            mail = mail.replace(".","");

                            name = txt_DName.getText().toString();
                            phone = txt_DPhone.getText().toString();
                            address = txt_DAddress.getText().toString();

                            user1.setName(name);
                            user1.setAddress(address);
                            user1.setPhone(phone);

                            mDatabase.child("User").child(mail).setValue(user1);

                            txt_Name.setText(name);
                            txt_Address.setText(address);
                            txt_Phone.setText(phone);

                            Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    private void inits() {
        tb_Profile = findViewById(R.id.tb_Profile);
        txt_Name = findViewById(R.id.txt_PfName);
        txt_Phone = findViewById(R.id.txt_PfPhone);
        txt_Email = findViewById(R.id.txt_PfEmail);
        txt_Address = findViewById(R.id.txt_PfAdrress);
        btn_Update = findViewById(R.id.btn_PfUpdate);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        getMenuInflater().inflate(R.menu.option_menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_history:
                Intent i_ToHistory = new Intent(ProfileActivity.this, HistoryActivity.class);
                startActivity(i_ToHistory);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
