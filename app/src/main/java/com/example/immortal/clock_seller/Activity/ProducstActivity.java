package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.immortal.clock_seller.Adapter.ClockAdapter;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.R;

import java.util.ArrayList;
import java.util.List;

public class ProducstActivity extends AppCompatActivity {
    public static final String intent_product_key = "product";
    Toolbar tb_Products;
    ListView lv_Products;
    ClockAdapter clockAdapter;
    ArrayList<Clock> clocks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        inits();
        controls();
    }

    private void inits() {
        tb_Products = findViewById(R.id.tb_Products);
        lv_Products = findViewById(R.id.lv_PsProducts);
        setSupportActionBar(tb_Products);
        loadActionBar();
        setTitle("Sản phẩm");
    }

    private void loadActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                Intent i_ToCart = new Intent(ProducstActivity.this,CartActivity.class);
                startActivity(i_ToCart);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void controls() {
        loadProducts();
        lv_Products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_ToProductDetail = new Intent(ProducstActivity.this,ProductDetailActivity.class);
                i_ToProductDetail.putExtra(intent_product_key,clocks.get(i));
                startActivity(i_ToProductDetail);
            }
        });
    }

    private void loadProducts() {
        Clock clock = new Clock();
        clock.setImg(R.drawable.democlock);
        clock.setName("Casio");
        clock.setPrice(1000000);
        clock.setDate("31/05/2018");
        clock.setDes("Đồng hồ casio");
        clocks = new ArrayList<Clock>();
        clocks.add(clock);
        clockAdapter = new ClockAdapter(this,R.layout.layout_products_item,clocks);
        lv_Products.setAdapter(clockAdapter);
        clockAdapter.notifyDataSetChanged();
    }
}
