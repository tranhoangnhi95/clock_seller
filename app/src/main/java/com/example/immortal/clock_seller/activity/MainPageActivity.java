package com.example.immortal.clock_seller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    public static final String manufaturer_name = "manufacture"; //chuỗi khóa khi gửi intent
    private Toolbar tbMainPage;
    private RecyclerView rvNewProducts;
    private ViewFlipper viewFlipper;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView txtProduct;
    private ProgressBar pbLoading;

//    private ArrayList<MyMenuItem> myMenuItems;

    private ArrayList<Model> models;
    private HotProductAdapter hotProductAdapter;

    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static ArrayList<Cart> carts; //giỏ hàng của khách hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        controls();
    }

    /**
     * Thêm các sự kiện lắng nghe, điều khiển
     */
    private void controls() {
        actionBarClick();
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Sự kiên click Navigation Button
     */
    private void actionBarClick() {
        tbMainPage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * Ánh xạ các view và khỏi tạo giá trị
     */
    private void inits() {
        rvNewProducts = findViewById(R.id.rv_NewProducts);
        navigationView = findViewById(R.id.nv_Navigation);
        tbMainPage = findViewById(R.id.tb_MainPage);
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        txtProduct = findViewById(R.id.txt_NewProduct);
        viewFlipper = findViewById(R.id.vf_Advertisement);
        pbLoading = findViewById(R.id.pb_Loading);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
//        myMenuItems = new ArrayList<>();
        setSupportActionBar(tbMainPage);
        setTitle("Trang chủ");
        //Khởi tạo giỏ hàng khi giỏ hàng null
        if (carts == null) {
            carts = new ArrayList<>();
        }

        //load giỏ hàng về khi đối tượng giỏ hàng rỗng
        if (carts.size() <= 0) {
            loadCart(SignInActivity.user);
        }

        //khởi tạo mảng models khi đối tượng rỗng
        if (models == null) {
            models = new ArrayList<>();
        }

        hotProductAdapter = new HotProductAdapter(getApplicationContext(), R.layout.layout_hot_product_item, models);
        rvNewProducts.setHasFixedSize(true);
        rvNewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvNewProducts.setAdapter(hotProductAdapter);


        loadViewFlipper();
        loadingActionBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHotModel();
    }

    /**
     * Đỗ dữ liệu local vào viewflipper
     */
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

    /**
     * Load các mẫu sản phẩm hot từ database
     */
    private void loadHotModel() {

        models.clear();
        mDatabase.child("Model").orderByChild("sold").limitToLast(8).addChildEventListener(new DataBase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Model model = dataSnapshot.getValue(Model.class);
                if (model.getQuantity() > 0) {
                    models.add(0, model);
                    hotProductAdapter.notifyDataSetChanged();
                    pbLoading.setVisibility(View.INVISIBLE);
                }
                pbLoading.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     * Tạo đối tượng Navigation button trên ActionBar
     */
    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbMainPage.setNavigationIcon(R.drawable.menu_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_Cart:
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

    /**
     * Load giỏ hàng của khách hàng về từ database
     */
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

    /**
     * Đưa các mẫu sản phẩm trong giỏ hàng mà người dùng chưa thanh toán lên database
     * @param user1 khách hàng
     */
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

    /**
     *  custom sự kiên click button back
     */
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
            carts.clear();
            SignInActivity.user = null;
            Intent iToSignIn = new Intent(getApplicationContext(), SignInActivity.class);
            iToSignIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iToSignIn);

        }
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
