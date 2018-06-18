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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.CartAdapter;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.Model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar tb_Cart;
    ListView lv_Cart;
    Button btn_Pay, btn_Continue;
    TextView txt_Total, txt_Annouce;
    CartAdapter cartAdapter;

    Calendar calendar;
    ArrayList<Clock> clocks;

    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        inits();
        controls();
    }

    private void inits() {
        tb_Cart = findViewById(R.id.tb_Cart);
        lv_Cart = findViewById(R.id.lv_CCart);
        btn_Pay = findViewById(R.id.btn_CPay);
        btn_Continue = findViewById(R.id.btn_CContinue);
        txt_Total = findViewById(R.id.txt_CTotal);
        txt_Annouce = findViewById(R.id.txt_CAnnouce);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        clocks = new ArrayList<>();
        calendar = Calendar.getInstance();
        setSupportActionBar(tb_Cart);
        setTitle("Giỏ hàng");
        loadingActionBar();

        cartAdapter = new CartAdapter(CartActivity.this, R.layout.layout_cart_item, MainPageActivity.carts, txt_Total);
        lv_Cart.setAdapter(cartAdapter);
        getCartTotal();
    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void controls() {
        annouce();
        getCartTotal();
        btn_Continue.setOnClickListener(this);
        btn_Pay.setOnClickListener(this);

    }

    private void getCartTotal() {
        if (MainPageActivity.carts.size() > 0) {
            long total = 0;
            for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                total += MainPageActivity.carts.get(i).getTotal();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txt_Total.setText(decimalFormat.format(total));

        }
    }

    private void annouce() {
        if (MainPageActivity.carts.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            txt_Annouce.setVisibility(View.VISIBLE);
            lv_Cart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txt_Annouce.setVisibility(View.INVISIBLE);
            lv_Cart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_CContinue:
                Intent i_ToMainPage = new Intent(CartActivity.this, MainPageActivity.class);
                startActivity(i_ToMainPage);
                break;
            case R.id.btn_CPay:
                pay(SignInActivity.user);
                break;
        }
    }

    private void pay(User user) {
        if (MainPageActivity.carts != null) {
            String time = String.valueOf(calendar.getTimeInMillis());
            String email = user.getEmail();
            email = email.replace("@","");
            email = email.replace(".","");
//            Toast.makeText(this,"Email" +email,Toast.LENGTH_LONG).show();

            for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                Cart cart = MainPageActivity.carts.get(i);
                Clock clock = new Clock(cart.getName(), String.valueOf(time),
                        cart.getImage(), cart.getPrice(),
                        cart.getQuantity(), cart.getTotal()
                );
                clocks.add(clock);
            }
            for (int i = 0; i < clocks.size(); i++) {
                mDataBase.child("History").child(email).push().setValue(clocks.get(i));

            }
            MainPageActivity.carts.clear();
            clocks.clear();
            cartAdapter.notifyDataSetChanged();
            txt_Total.setText(String.valueOf(0));
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_history:
                Intent i_History = new Intent(CartActivity.this, HistoryActivity.class);
                startActivity(i_History);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
