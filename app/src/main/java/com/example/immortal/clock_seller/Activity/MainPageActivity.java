package com.example.immortal.clock_seller.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.immortal.clock_seller.Adapter.HotProductAdapter;
import com.example.immortal.clock_seller.Adapter.MyMenuItemAdapter;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.Model.MyMenuItem;
import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String manufaturer_name = "manufacture";
    private Toolbar tbMainPage;
    private RecyclerView rvNewProducts;
    private ListView lvNavigation;
    private DrawerLayout drawerLayout;
    private TextView txtProduct;

    private ArrayList<MyMenuItem> myMenuItems;
    private MyMenuItemAdapter myMenuItemAdapter;
    private MyMenuItem miProfile, miSignOut, miHistory;

    private ArrayList<Model> models;
    private HotProductAdapter hotProductAdapter;

    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static ArrayList<Cart> carts;
//    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        controls();
    }


    private void controls() {
        actionBarClick();
        navigationItemClick();

    }

    private void navigationItemClick() {
        lvNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyMenuItem item = (MyMenuItem) adapterView.getItemAtPosition(i);
                String name = item.getName();
//                if (name.equals("Trang chính")) {
//                    Intent i_ToMainPage = new Intent(MainPageActivity.this, MainPageActivity.class);
//                    startActivity(i_ToMainPage);
//                } else
                if (name.equals("Thông tin tài khoản")) {
                    Intent i_ToProfile = new Intent(MainPageActivity.this, ProfileActivity.class);
                    startActivity(i_ToProfile);
                } else if (name.equals("Đăng xuất")) {
                    mAuth.signOut();
                    Intent i_ToSignIn = new Intent(MainPageActivity.this, SignInActivity.class);
                    startActivity(i_ToSignIn);
                } else if (name.equals("Lịch sử")) {
                    Intent i_ToHistory = new Intent(MainPageActivity.this, HistoryActivity.class);
                    startActivity(i_ToHistory);
                } else {
                    Intent i_ToProDuct = new Intent(MainPageActivity.this, ProducstActivity.class);
                    i_ToProDuct.putExtra(manufaturer_name, item.getName());
                    startActivity(i_ToProDuct);
                }

            }
        });
    }


    private void actionBarClick() {
        tbMainPage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void inits() {
        rvNewProducts = findViewById(R.id.rv_NewProducts);
        lvNavigation = findViewById(R.id.lv_Menu);
        tbMainPage = findViewById(R.id.tb_MainPage);
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        txtProduct = findViewById(R.id.txt_NewProduct);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myMenuItems = new ArrayList<>();
        setSupportActionBar(tbMainPage);
        setTitle("Trang chủ");
        if (carts == null) {
            carts = new ArrayList<>();
        }

        if (carts.size() <= 0) {
            loadCart(SignInActivity.user);
        }

        if (models == null) {
            models = new ArrayList<>();
        }

        hotProductAdapter = new HotProductAdapter(getApplicationContext(), R.layout.layout_hot_product_item, models);
        rvNewProducts.setHasFixedSize(true);
        rvNewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvNewProducts.setAdapter(hotProductAdapter);

        if (myMenuItems == null) {
            myMenuItems = new ArrayList<>();
        }

        myMenuItemAdapter = new MyMenuItemAdapter(MainPageActivity.this, R.layout.layout_menu_item, myMenuItems);
        lvNavigation.setAdapter(myMenuItemAdapter);
        myMenuItemAdapter.notifyDataSetChanged();

        loadHotModel();
        loadingActionBar();
        loadMenu();
    }

    private void loadMenu() {

        if (myMenuItems.size() <= 0) {

            mDatabase.child("Brand").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    myMenuItems.add(0, dataSnapshot.getValue(MyMenuItem.class));
                    myMenuItemAdapter.notifyDataSetChanged();
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
            if (miProfile == null) {
                miProfile = new MyMenuItem();
                miProfile.setImage("https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/profile.png?alt=media&token=1fda1b40-9620-4f1c-9266-b7d707df5256");
                miProfile.setName("Thông tin tài khoản");
                myMenuItems.add(miProfile);
                myMenuItemAdapter.notifyDataSetChanged();
            }
            if (miHistory == null) {
                miHistory = new MyMenuItem();
                miHistory.setImage("https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/history.png?alt=media&token=b03f4e74-4882-491f-95bf-945b1eb8c0db");
                miHistory.setName("Lịch sử");
                myMenuItems.add(miHistory);
                myMenuItemAdapter.notifyDataSetChanged();
            }

            if (miSignOut == null) {
                miSignOut = new MyMenuItem();
                miSignOut.setImage("https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/signout.png?alt=media&token=e10fe8fe-89b9-4c05-a3a5-3e4140531036");
                miSignOut.setName("Đăng xuất");
                myMenuItems.add(miSignOut);
                myMenuItemAdapter.notifyDataSetChanged();
            }


        }

//        myMenuItemAdapter.notifyDataSetChanged();
    }


    private void loadHotModel() {
        if (models.size() <= 0) {
            mDatabase.child("Model").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    Toast.makeText(MainPageActivity.this, models.size() + "", Toast.LENGTH_LONG).show();
                    Model model = dataSnapshot.getValue(Model.class);
                    models.add(model);
                    hotProductAdapter.notifyDataSetChanged();

//                    Toast.makeText(MainPageActivity.this, models.size() + "aaaaaaaaaaaaa", Toast.LENGTH_LONG).show();
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

    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbMainPage.setNavigationIcon(R.drawable.menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_cart:
                Intent i_ToCart = new Intent(MainPageActivity.this, CartActivity.class);
                startActivity(i_ToCart);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.txt_NewProduct:
//                Intent i_ToProduct = new Intent(MainPageActivity.this, ProducstActivity.class);
//                startActivity(i_ToProduct);
        }
    }

    private void loadCart(User user1) {
        String mail = user1.getEmail();
        mail = mail.replace("@", "");
        mail = mail.replace(".", "");


        mDatabase.child("Cart").child(mail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cart cart = dataSnapshot.getValue(Cart.class);
                if ((!cart.getName().equals("Default")) && cart.getPrice() != 0) {
                    carts.add(cart);
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

    private void pushCart(User user1) {
        if (carts.size() > 0) {
            String mail = "";
            mail += user1.getEmail();
            mail = mail.replace("@", "");
            mail = mail.replace(".", "");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(mail);
            databaseReference.removeValue();
            for (int i = 0; i < carts.size(); i++) {
                mDatabase.child("Cart").child(mail).push().setValue(carts.get(i));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Bạn thực sự muốn thoát ứng dụng");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Có",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                            System.exit(0);
                        }
                    }
            );
            builder.setNegativeButton(
                    "Không",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                        }
                    }
            );


            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        pushCart(SignInActivity.user);
        carts.clear();
        super.onDestroy();
    }

}
