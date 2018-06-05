package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.MyMenuItemAdapter;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.MyMenuItem;
import com.example.immortal.clock_seller.R;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar tb_MainPage;
    RecyclerView rv_NewProducts;
    ListView lv_Navigation;
    DrawerLayout drawerLayout;
    TextView txt_Product;
    ArrayList<MyMenuItem> myMenuItems;
    MyMenuItemAdapter myMenuItemAdapter;

    public static ArrayList<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        controls();
    }

    private void controls() {
        actionBarClick();
        txt_Product.setOnClickListener(this);
        lv_Navigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyMenuItem item = (MyMenuItem) adapterView.getItemAtPosition(i);
                String name = item.getName();
                if (name.equals("Trang chính")){
                    Intent i_ToMainPage = new Intent(MainPageActivity.this,MainPageActivity.class);
                    startActivity(i_ToMainPage);
                }else if (name.equals("Thông tin tài khoản")){
                    Intent i_ToProfile = new Intent(MainPageActivity.this,ProfileActivity.class);
                    startActivity(i_ToProfile);
                }
            }
        });

    }


    private void actionBarClick() {
        tb_MainPage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                MyMenuItem mi_MainPage = new MyMenuItem();
                mi_MainPage.setImage(R.drawable.home);
                mi_MainPage.setName("Trang chính");
                MyMenuItem mi_Profile = new MyMenuItem();
                mi_Profile.setImage(R.drawable.profile);
                mi_Profile.setName("Thông tin tài khoản");
                myMenuItems.add(mi_MainPage);
                myMenuItems.add(mi_Profile);
                myMenuItemAdapter = new MyMenuItemAdapter(MainPageActivity.this,R.layout.layout_menu_item,myMenuItems);
                lv_Navigation.setAdapter(myMenuItemAdapter);
                myMenuItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void inits() {
        rv_NewProducts = findViewById(R.id.rv_NewProducts);
        lv_Navigation = findViewById(R.id.lv_Menu);
        tb_MainPage = findViewById(R.id.tb_MainPage);
        drawerLayout = findViewById(R.id.dl_MainPageLayout);
        txt_Product = findViewById(R.id.txt_NewProduct);
        myMenuItems = new ArrayList<>();
        if (carts == null){
            carts = new ArrayList<>();
//            Toast.makeText(this,"Da tao carts",Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(this,"Da co roi",Toast.LENGTH_SHORT).show();
        }

        setSupportActionBar(tb_MainPage);
        setTitle("Trang chủ");
        loadingActionBar();


    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tb_MainPage.setNavigationIcon(R.drawable.menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_cart:
                Intent i_ToCart = new Intent(MainPageActivity.this,CartActivity.class);
                startActivity(i_ToCart);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_NewProduct:
                Intent i_ToProduct = new Intent(MainPageActivity.this,ProducstActivity.class);
                startActivity(i_ToProduct);
        }
    }
}
