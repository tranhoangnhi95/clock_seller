package com.example.immortal.clock_seller.Activity;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.immortal.clock_seller.Adapter.HistoryAdapter;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.Model.SoldClock;
import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    Toolbar tb_History;
    TextView txt_Annouce;
    ListView lv_History;
    ArrayList<Clock> clocks;
    HistoryAdapter historyAdapter;
    DatabaseReference mDatabase;

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
        txt_Annouce = findViewById(R.id.txt_HAnnouce);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(tb_History);
        setTitle("Lịch sử");
        loadingActionBar();
        clocks = new ArrayList<>();

        historyAdapter = new HistoryAdapter(this, R.layout.layout_history_item, clocks);
        lv_History.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();


    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tb_History.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mn_cart:
//                Intent i_ToCart = new Intent(HistoryActivity.this, CartActivity.class);
//                startActivity(i_ToCart);
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void controls() {
        loadHistory(SignInActivity.user);
        annouce();
    }

    private void annouce() {
    }

    private void loadHistory(User sUser) {
        String email = sUser.getEmail();
        email = email.replace("@", "");
        email = email.replace(".", "");
        mDatabase.child("History").child(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Clock clock = dataSnapshot.getValue(Clock.class);
                if ((!clock.getName().equals("Default")) && clock.getPrice() != 0) {
                    clocks.add(0, dataSnapshot.getValue(Clock.class));
                    historyAdapter.notifyDataSetChanged();
                    txt_Annouce.setVisibility(View.INVISIBLE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
