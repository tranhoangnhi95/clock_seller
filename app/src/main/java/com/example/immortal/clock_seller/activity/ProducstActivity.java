package com.example.immortal.clock_seller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.immortal.clock_seller.adapter.ModelAdapter;
import com.example.immortal.clock_seller.utils.DataBase;
import com.example.immortal.clock_seller.model.Model;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProducstActivity extends AppCompatActivity {
    public static final String intent_product_key = "product";
    private Toolbar tbProducts;
    private ListView lvProducts;
    private ModelAdapter modelAdapter;
    private ArrayList<Model> models;
    //    Button btn_Qt;
    private String manufature_name = "";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        inits();
        controls();
    }

    private void inits() {
        tbProducts = findViewById(R.id.tb_Products);
        lvProducts = findViewById(R.id.lv_PsProducts);
        setSupportActionBar(tbProducts);
        models = new ArrayList<>();
        modelAdapter = new ModelAdapter(ProducstActivity.this, R.layout.layout_products_item, models);
        lvProducts.setAdapter(modelAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        manufature_name = getIntent().getStringExtra(MainPageActivity.manufaturer_name);
        loadActionBar();
        setTitle("Sản phẩm");
    }

    private void loadActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbProducts.setNavigationIcon(R.drawable.arrow_back_24dp);
        tbProducts.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                Intent i_ToCart = new Intent(ProducstActivity.this, CartActivity.class);
                startActivity(i_ToCart);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void controls() {
//        loadProducts();

    }

    @Override
    protected void onResume() {
        super.onResume();
        models.clear();
        loadProducts();
    }

    private void loadProducts() {
        if (!manufature_name.equals("")) {
            if (manufature_name.equals("Tất cả sản phẩm")) {
                mDatabase.child("Model").addChildEventListener(new DataBase() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Model model = dataSnapshot.getValue(Model.class);
                        models.add(model);
                        modelAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                mDatabase.child("Model").addChildEventListener(new DataBase() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Model model = dataSnapshot.getValue(Model.class);
                        if (model.getB_name().equals(manufature_name)) {
                            models.add(model);
                            modelAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
