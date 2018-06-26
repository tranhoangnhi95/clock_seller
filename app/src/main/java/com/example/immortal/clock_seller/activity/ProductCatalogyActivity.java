package com.example.immortal.clock_seller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.immortal.clock_seller.R;
import com.example.immortal.clock_seller.adapter.MyMenuItemAdapter;
import com.example.immortal.clock_seller.model.MyMenuItem;
import com.example.immortal.clock_seller.utils.DataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductCatalogyActivity extends AppCompatActivity {
    private ListView lvCatalogy;
    private Toolbar tbCatalogy;
    private MyMenuItemAdapter myMenuItemAdapter;
    private ArrayList<MyMenuItem> myMenuItems;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalogy);
        inits();
        controls();
    }

    private void controls() {
        lvItemClick();
    }

    private void lvItemClick() {
        lvCatalogy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyMenuItem item = (MyMenuItem) adapterView.getItemAtPosition(i);
                Intent iToProDuct = new Intent(ProductCatalogyActivity.this, ProducstActivity.class);
                iToProDuct.putExtra(MainPageActivity.manufaturer_name, item.getName());
                startActivity(iToProDuct);
            }
        });
    }

    private void inits() {
        lvCatalogy = findViewById(R.id.lv_Catalogy);
        tbCatalogy = findViewById(R.id.tb_Calatogy);

        setSupportActionBar(tbCatalogy);
        setTitle("Danh mục sản phẩm");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadingActionbar();
        if (myMenuItems ==  null){
            myMenuItems = new ArrayList<>();
        }
        myMenuItemAdapter = new MyMenuItemAdapter(getApplicationContext(),R.layout.layout_menu_item,myMenuItems);
        lvCatalogy.setAdapter(myMenuItemAdapter);
        myMenuItemAdapter.notifyDataSetChanged();
        loadCatalogy();

    }

    private void loadCatalogy() {
        if (myMenuItems.isEmpty()){
            mDatabase.child("Brand").addChildEventListener(new DataBase() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    myMenuItems.add(0, dataSnapshot.getValue(MyMenuItem.class));
                    myMenuItemAdapter.notifyDataSetChanged();
                }
            });
        }
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
                Intent i_ToCart = new Intent(ProductCatalogyActivity.this, CartActivity.class);
                startActivity(i_ToCart);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadingActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbCatalogy.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
