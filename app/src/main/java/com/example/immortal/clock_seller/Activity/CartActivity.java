package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Adapter.CartAdapter;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar tb_Cart;
    ListView lv_Cart;
    Button btn_Pay, btn_Continue;
    TextView txt_Total,txt_Annouce;
    CartAdapter cartAdapter;
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
        setSupportActionBar(tb_Cart);
        setTitle("Giỏ hàng");
        loadingActionBar();

        cartAdapter = new CartAdapter(CartActivity.this,R.layout.layout_cart_item,MainPageActivity.carts);
        lv_Cart.setAdapter(cartAdapter);
    }

    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void controls() {
        annouce();
        getCartTotal();
        btn_Continue.setOnClickListener(this);

    }

    private void getCartTotal() {
        if (MainPageActivity.carts.size() > 0){
            long total = 0;
            for (int i = 0; i<MainPageActivity.carts.size(); i++){
                total += MainPageActivity.carts.get(i).getTotal();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txt_Total.setText(decimalFormat.format(total) + " Đ");

        }
    }

    private void annouce() {
        if (MainPageActivity.carts.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            txt_Annouce.setVisibility(View.VISIBLE);
            lv_Cart.setVisibility(View.INVISIBLE);
        }else {
            cartAdapter.notifyDataSetChanged();
            txt_Annouce.setVisibility(View.INVISIBLE);
            lv_Cart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_CContinue:
                Intent i_ToMainPage = new Intent(CartActivity.this, MainPageActivity.class);
                startActivity(i_ToMainPage);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_history:
                Intent i_History = new Intent(CartActivity.this,HistoryActivity.class);
                startActivity(i_History);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
