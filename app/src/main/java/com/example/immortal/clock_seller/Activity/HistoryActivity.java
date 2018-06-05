package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.immortal.clock_seller.Adapter.HistoryAdapter;
import com.example.immortal.clock_seller.Model.SoldClock;
import com.example.immortal.clock_seller.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    Toolbar tb_History;
    ListView lv_History;
    ArrayList<SoldClock> soldClocks;
    HistoryAdapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        inits();
        controls();
    }

    private void inits() {
        tb_History = findViewById(R.id.tb_History);
        lv_History = findViewById(R.id.lv_HHistory);
        setSupportActionBar(tb_History);
        loadingActionBar();
        setTitle("Lịch sử mua hàng");

        soldClocks = new ArrayList<>();
        SoldClock soldClock = new SoldClock();
        soldClock.setUser("user test");
        soldClock.setImg(R.drawable.democlock);
        soldClock.setName("Casio xxyy");
        soldClock.setPrice(100000);
        soldClock.setDate("27/5/2018");
        soldClock.setDateOfPay("01/06/2018");
        soldClock.setQuantity(1);
        soldClock.setDetail("Đồng hồ Casio");
        soldClocks.add(soldClock);
        historyAdapter = new HistoryAdapter(this,R.layout.layout_history_item,soldClocks);
        lv_History.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();


    }

    private void loadingActionBar() {
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
                Intent i_ToCart = new Intent(HistoryActivity.this,CartActivity.class);
                startActivity(i_ToCart);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void controls() {

    }
}
