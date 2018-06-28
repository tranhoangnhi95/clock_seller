package com.example.immortal.clock_seller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.immortal.clock_seller.adapter.HotProductAdapter;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.utils.DataBase;
import com.example.immortal.clock_seller.model.Model;
import com.example.immortal.clock_seller.model.MyMenuItem;
import com.example.immortal.clock_seller.model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String manufaturer_name = "manufacture";
    private Toolbar tbMainPage;
    private RecyclerView rvNewProducts;
    private ViewFlipper viewFlipper;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView txtProduct;

    private ArrayList<MyMenuItem> myMenuItems;


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
        navigationView.setNavigationItemSelectedListener(this);

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
//        lvNavigation = findViewById(R.id.lv_Menu);
        navigationView = findViewById(R.id.nv_Navigation);
        tbMainPage = findViewById(R.id.tb_MainPage);
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        txtProduct = findViewById(R.id.txt_NewProduct);
        viewFlipper = findViewById(R.id.vf_Advertisement);

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

//        myMenuItemAdapter = new MyMenuItemAdapter(MainPageActivity.this, R.layout.layout_menu_item, myMenuItems);
//        lvNavigation.setAdapter(myMenuItemAdapter);
//        myMenuItemAdapter.notifyDataSetChanged();
        loadViewFlipper();
        loadHotModel();
        loadingActionBar();
    }

    private void loadViewFlipper() {
        ArrayList<Integer> adv = new ArrayList<>();
        adv.add(R.raw.casio);
        adv.add(R.raw.citizen);
        adv.add(R.raw.orient);
        adv.add(R.raw.rolex);
        adv.add(R.raw.seiko);

        for (int i = 0; i < adv.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(adv.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_silde_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_silde_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_silde_in);
        viewFlipper.setOutAnimation(animation_silde_out);
    }


    private void loadHotModel() {
        if (models.size() <= 0) {
            mDatabase.child("Model").addChildEventListener(new DataBase() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Model model = dataSnapshot.getValue(Model.class);
                    if (model.getQuantity() > 0) {
                        models.add(model);
                        hotProductAdapter.notifyDataSetChanged();
                    }

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


        mDatabase.child("Cart").child(mail).addChildEventListener(new DataBase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cart cart = dataSnapshot.getValue(Cart.class);
                if ((!cart.getName().equals("Default")) && cart.getPrice() != 0) {
                    carts.add(cart);
                }

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


            final android.app.AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.my_secondary));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.my_secondary));
                }
            });
            alertDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        pushCart(SignInActivity.user);
        carts.clear();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_Model) {
            Intent iToCatalogy = new Intent(MainPageActivity.this, ProductCatalogyActivity.class);
            startActivity(iToCatalogy);
        } else if (id == R.id.nav_Profile) {
            Intent iToProfile = new Intent(MainPageActivity.this, ProfileActivity.class);
            startActivity(iToProfile);
        } else if (id == R.id.nav_History) {
            Intent iToHistory = new Intent(MainPageActivity.this, HistoryActivity.class);
            startActivity(iToHistory);
        } else if (id == R.id.nav_SignOut) {
            mAuth.signOut();
            Intent iToSignIn = new Intent(MainPageActivity.this, SignInActivity.class);
            startActivity(iToSignIn);
        }
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

//                    Intent i_ToProDuct = new Intent(MainPageActivity.this, ProducstActivity.class);
//                    i_ToProDuct.putExtra(manufaturer_name, item.getName());
//                    startActivity(i_ToProDuct);
    }

}
