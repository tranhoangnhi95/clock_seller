package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.MyFragmentAdapter;
import com.example.immortal.clock_seller.AnimViewPager.ZoomOutPageTransfomer;
import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    public static final String email_key = "email";
    TabLayout tl_Sliding;
    ViewPager vp_ViewPager;
    MyFragmentAdapter myFragmentAdapter;
    String SU_Name, SU_Phone, SU_Email, SU_Address,  SU_Pass;
    String SI_Email, SI_Pass;
    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static User user;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }


    public void signUpFragment(String Name,String Phone, String Email, String Address, String Pass){
        this.SU_Name = Name;
        this.SU_Phone = Phone;
        this.SU_Email = Email;
        this.SU_Address = Address;
        this.SU_Pass = Pass;

        mAuth.createUserWithEmailAndPassword(SU_Email, SU_Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            User user1 = new User(SU_Name,SU_Phone, SU_Email,SU_Address);
                            mDatabase.child("User").push().setValue(user1, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        Toast.makeText(SignInActivity.this,"Đăng ký thành công, vui lòng chuyển sang đăng nhập",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(SignInActivity.this,"Đăng ký người dùng không thành công",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                            Intent i_ToMainPage = new Intent(SignInActivity.this,MainPageActivity.class);
//                            i_ToMainPage.putExtra(email_key,SU_Email);
//                            startActivity(i_ToMainPage);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this,"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void signInFragment(String Email, String Pass){
        this.SI_Email = Email;
        this.SI_Pass = Pass;

        mAuth.signInWithEmailAndPassword(SI_Email, SI_Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child("User").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    User userFB = dataSnapshot.getValue(User.class);
                                    if (userFB.getEmail().equals(SI_Email)) {
                                        user = userFB;
                                    }
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(SignInActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Intent i_ToMainPage = new Intent(SignInActivity.this,MainPageActivity.class);
//                            i_ToMainPage.putExtra(email_key,SI_Email);
                            startActivity(i_ToMainPage);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this,"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            mDatabase.child("User").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    User userFB = dataSnapshot.getValue(User.class);
                    if (userFB.getEmail().equals(currentUser.getEmail())) {
                        user = userFB;
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i_ToMainPage = new Intent(SignInActivity.this, MainPageActivity.class);
//            i_ToMainPage.putExtra(SignInActivity.email_key,user.getEmail());
            startActivity(i_ToMainPage);

        } else {

        }
    }

}
