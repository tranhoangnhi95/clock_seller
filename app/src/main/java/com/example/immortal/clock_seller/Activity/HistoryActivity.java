package com.example.immortal.clock_seller.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.immortal.clock_seller.Adapter.HistoryAdapter;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar tbHistory;
    private TextView txtAnnouce;
    private ListView lvHistory;
    private ArrayList<Clock> clocks;
    private HistoryAdapter historyAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        inits();
        controls();
    }

    private void inits() {
        tbHistory = findViewById(R.id.tb_History);
        lvHistory = findViewById(R.id.lv_HHistory);
        txtAnnouce = findViewById(R.id.txt_HAnnouce);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(tbHistory);
        setTitle("Lịch sử");
        loadingActionBar();
        clocks = new ArrayList<>();

        historyAdapter = new HistoryAdapter(this, R.layout.layout_history_item, clocks);
        lvHistory.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();


    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbHistory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

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
                    txtAnnouce.setVisibility(View.INVISIBLE);
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
