package com.example.immortal.clock_seller.activity;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.immortal.clock_seller.model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar tbProfile;
    private Button btnUpdate;
    private TextView txtName, txtPhone, txtEmail, txtAddress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inits();
        controls();
    }

    private void controls() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
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

                            txtName.setText(name);
                            txtAddress.setText(address);
                            txtPhone.setText(phone);

                            Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );

        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.my_primary));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.my_primary));
            }
        });
        alertDialog.show();



    }

    private void inits() {
        tbProfile = findViewById(R.id.tb_Profile);
        txtName = findViewById(R.id.txt_PfName);
        txtPhone = findViewById(R.id.txt_PfPhone);
        txtEmail = findViewById(R.id.txt_PfEmail);
        txtAddress = findViewById(R.id.txt_PfAdrress);
        btnUpdate = findViewById(R.id.btn_PfUpdate);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtName.setText(SignInActivity.user.getName());
        txtPhone.setText(SignInActivity.user.getPhone());
        txtEmail.setText(SignInActivity.user.getEmail());
        txtAddress.setText(SignInActivity.user.getAddress());
        setSupportActionBar(tbProfile);
        loadingActionBar();
        setTitle("Tài khoản của bạn");
    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
