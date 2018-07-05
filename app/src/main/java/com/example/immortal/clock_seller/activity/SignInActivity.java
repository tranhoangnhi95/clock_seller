package com.example.immortal.clock_seller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.immortal.clock_seller.adapter.MyFragmentAdapter;
import com.example.immortal.clock_seller.animviewpager.ZoomOutPageTransfomer;
import com.example.immortal.clock_seller.fragments.SignInFragment;
import com.example.immortal.clock_seller.fragments.SignUpFragment;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.model.Clock;
import com.example.immortal.clock_seller.utils.DataBase;
import com.example.immortal.clock_seller.model.User;
import com.example.immortal.clock_seller.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity implements SignUpFragment.FragmentCommuniCation {
    public static final String email_key = "email";
    public TabLayout tlSliding;
    private ViewPager vpViewPager;
    private MyFragmentAdapter myFragmentAdapter;
    private String suName, suPhone, suEmail, suAddress, suPass;
    private String siEmail, siPass;
    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inits();

    }

    /**
     * Ánh xạ các view và khỏi tạo giá trị
     */
    private void inits() {
        tlSliding = findViewById(R.id.tl_SISliding);
        vpViewPager = findViewById(R.id.vp_SIViewPager);
        myFragmentAdapter = new MyFragmentAdapter(SignInActivity.this, getSupportFragmentManager());
        vpViewPager.setAdapter(myFragmentAdapter);
        vpViewPager.setPageTransformer(true, new ZoomOutPageTransfomer());
        tlSliding.setupWithViewPager(vpViewPager);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //khởi tạo user nếu đối tượng null
        if (user == null) {
            user = new User();
        }
    }

    /**
     * Đăng kí người dùng bằng Email
     * @param Name Tên người dùng
     * @param Phone Số điện thoại
     * @param Email Email
     * @param Address Địa chỉ
     * @param Pass Mật khẩu
     */
    public void signUpFragment(String Name, String Phone, String Email, String Address, String Pass) {
        this.suName = Name;
        this.suPhone = Phone;
        this.suEmail = Email;
        this.suAddress = Address;
        this.suPass = Pass;
        //Đăng kí toàn khoản bằng email password với firebase authencation
        mAuth.createUserWithEmailAndPassword(suEmail, suPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user1 = new User(suName, suPhone, suEmail, suAddress);
                            String mail = suEmail.replace("@", "");
                            mail = mail.replace(".", "");
                            mDatabase.child("User").child(mail).setValue(user1, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        SignUpFragment signUpFragment = (SignUpFragment) myFragmentAdapter.getItem(1);
                                        signUpFragment.fragmentCommuniCation.PassingEmail(suEmail);
                                        tlSliding.getTabAt(0).select();
                                        Toast.makeText(SignInActivity.this, "Đăng ký thành công, vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Đăng ký người dùng không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignInActivity.this, "Email đã trùng, vui lòng nhập email khác", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    /**
     * Đăng nhập vào ứng dụng bằng Email và mật khẩu
     * @param Email email
     * @param Pass Mật khẩu
     */
    public void signInFragment(String Email, String Pass) {
        this.siEmail = Email;
        this.siPass = Pass;
        //Đăng nhập bằng email và mật khẩu với firebase authencation
        mAuth.signInWithEmailAndPassword(siEmail, siPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setEmail(siEmail);
                            String mail = siEmail.replace("@", "");
                            mail = mail.replace(".", "");
                            mDatabase.child("User").addChildEventListener(new DataBase() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    User userFB = dataSnapshot.getValue(User.class);
                                    if (userFB.getEmail().equals(siEmail)) {
                                        user = userFB;
                                    }
                                }
                            });
                            Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent i_ToMainPage = new Intent(SignInActivity.this, MainPageActivity.class);
                            mDatabase.child("Cart").child(mail).child("Default").setValue(new Cart(
                                    "default", 0, 0, "default", 0, 0, 0
                            ));
                            mDatabase.child("History").child(mail).child("Default").setValue(new Clock(
                                    "default", "default", "default", 0, 0, 0
                            ));
                            startActivity(i_ToMainPage);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        //kiểm tra nếu người dùng đã đăng nhập ở lần trước đó sẽ bỏ qua bước đăng nhập
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser(); //người dùng trước đó
        if (currentUser != null) {
            mDatabase.child("User").addChildEventListener(new DataBase() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    User userFB = dataSnapshot.getValue(User.class);
                    if (userFB.getEmail().equals(currentUser.getEmail())) {
                        user = userFB;
                    }
                }
            });
        }
        updateUI(currentUser);
    }

    /**
     * Thực hiện các khởi tạo cơ bản và chuyển màn hình khi người dùng trước đó tồn tại
     * @param user1
     */
    private void updateUI(FirebaseUser user1) {
        if (user1 != null) {
            Intent i_ToMainPage = new Intent(SignInActivity.this, MainPageActivity.class);
            String mail = user1.getEmail();
            user.setEmail(mail);
            mail = mail.replace("@", "");
            mail = mail.replace(".", "");
            mDatabase.child("Cart").child(mail).child("Default").setValue(new Cart(
                    "default", 0, 0, "default", 0, 0, 0
            ));
            mDatabase.child("History").child(mail).child("Default").setValue(new Clock(
                    "default", "default", "default", 0, 0, 0
            ));
            startActivity(i_ToMainPage);
            finish();
        } else {

        }
    }

    @Override
    public void PassingEmail(String Email) {
        //truyền dữ liệu sang cho frament đăng nhập
        SignInFragment signInFragment = (SignInFragment) myFragmentAdapter.getItem(0);
        signInFragment.DisplayEmail(Email);
    }
}
