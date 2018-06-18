package com.example.immortal.clock_seller.Activity;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.ModelAdapter;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProducstActivity extends AppCompatActivity {
    public static final String intent_product_key = "product";
    Toolbar tb_Products;
    ListView lv_Products;

    ModelAdapter modelAdapter;
    ArrayList<Model> models;
//    Button btn_Qt;

    DatabaseReference mDatabase;
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
        models = new ArrayList<>();
        modelAdapter = new ModelAdapter(ProducstActivity.this,R.layout.layout_products_item,models);
        lv_Products.setAdapter(modelAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
//        lv_Products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent i_ToProductDetail = new Intent(ProducstActivity.this,ProductDetailActivity.class);
//                i_ToProductDetail.putExtra(intent_product_key,clocks.get(i));
//                startActivity(i_ToProductDetail);
//            }
//        });
//        lv_Products.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Clock clock = (Clock) adapterView.getItemAtPosition(i);
//                Intent i_ToProductDetail = new Intent(ProducstActivity.this,ProductDetailActivity.class);
//                i_ToProductDetail.putExtra(intent_product_key,clocks.get(i));
//                startActivity(i_ToProductDetail);
//                return false;
//            }
//        });
    }

    private void loadProducts() {
        final Intent i_reciver = getIntent();
        if (i_reciver.getStringExtra(MainPageActivity.manufaturer_name) != null){
                mDatabase.child("Model").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Model model = dataSnapshot.getValue(Model.class);
                        if (model.getB_name().equals(i_reciver.getStringExtra(MainPageActivity.manufaturer_name))){
                            models.add(model);
                            modelAdapter.notifyDataSetChanged();
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
        }else {
            Model model_recive = (Model) i_reciver.getSerializableExtra(ProducstActivity.intent_product_key);
            models.add(model_recive);
            modelAdapter.notifyDataSetChanged();

        }
    }
}
