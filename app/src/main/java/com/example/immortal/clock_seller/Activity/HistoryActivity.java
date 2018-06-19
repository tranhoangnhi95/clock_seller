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
import android.widget.ListView;

import com.example.immortal.clock_seller.Adapter.HistoryAdapter;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(tb_History);
        setTitle("Lịch sử");
        loadingActionBar();
        clocks = new ArrayList<>();
//        clocks.add(new Clock("CASIO MTP_VS02L","1529317240955",
//                "https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/Casio_MTP_VS02L.jpg?alt=media&token=7da25292-eb8c-49f6-b4c2-25f5aee6c768",
//                966000,4,3864000
//                ));
        historyAdapter = new HistoryAdapter(this,R.layout.layout_history_item,clocks);
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
        loadHistory(SignInActivity.user);
    }

    private void loadHistory(User sUser) {
        String email = sUser.getEmail();
        email = email.replace("@","");
        email = email.replace(".","");
        mDatabase.child("History").child(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                clocks.add(0,dataSnapshot.getValue(Clock.class));
                historyAdapter.notifyDataSetChanged();
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
