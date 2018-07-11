package com.example.immortal.clock_seller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.adapter.CartAdapter;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.model.Clock;
import com.example.immortal.clock_seller.model.User;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tbCart;
    private ListView lvCart;
    private Button btnPay, btnContinue;
    private TextView txtTotal, txtAnnouce;
    private CartAdapter cartAdapter;
    private Calendar calendar;
    public ArrayList<Clock> clocks;

    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        inits();
        controls();
    }

    /**
     * Ánh xạ các view và khỏi tạo giá trị
     */
    private void inits() {
        tbCart = findViewById(R.id.tb_Cart);
        lvCart = findViewById(R.id.lv_CCart);
        btnPay = findViewById(R.id.btn_CPay);
        btnContinue = findViewById(R.id.btn_CContinue);
        txtTotal = findViewById(R.id.txt_CTotal);
        txtAnnouce = findViewById(R.id.txt_CAnnouce);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        clocks = new ArrayList<>();
        calendar = Calendar.getInstance();
        setSupportActionBar(tbCart);
        setTitle("Giỏ hàng");
        loadingActionBar();

        if (cartAdapter == null){
            Log.d("dddđd","Create1");
            cartAdapter = new CartAdapter(CartActivity.this, R.layout.layout_cart_item, MainPageActivity.carts, txtTotal);
            lvCart.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }else {
            Log.d("dddđd","Create2");
            cartAdapter.notifyDataSetChanged();
        }
        getCartTotal();
//        Toast.makeText(this,MainPageActivity.carts.size()+"", Toast.LENGTH_LONG).show();
    }

    /**
     *Tạo đối tượng Navigation button trên ActionBar
     */
    private void loadingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbCart.setNavigationIcon(R.drawable.arrow_back_24dp);
    }

    /**
     * Thêm các sự kiện lắng nghe, điều khiển
     */
    private void controls() {
        annouce();
        getCartTotal();
        btnContinue.setOnClickListener(this);
        btnPay.setOnClickListener(this);

        tbCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * Tính giá trị tổng cho giỏ hàng
     */
    private void getCartTotal() {
        if (MainPageActivity.carts.size() > 0) {
            long total = 0; //giá trị tổng
            for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                total += MainPageActivity.carts.get(i).getTotal();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTotal.setText(decimalFormat.format(total));

        }
    }

    /**
     * Kiểm tra giỏ hàng và thông báo giỏ hàng rỗng
     */
    private void annouce() {
        if (MainPageActivity.carts.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            txtAnnouce.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txtAnnouce.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_CContinue:
                Intent i_ToMainPage = new Intent(CartActivity.this, MainPageActivity.class);
                startActivity(i_ToMainPage);
                finish();
                break;
            case R.id.btn_CPay:
                pay(SignInActivity.user);
                break;
        }
    }

    /**
     * Thanh toán giỏ hàng và đưa vào lịch sử mua hàng của một khách hàng
     *
     * @param user đối tượng khách hàng
     */
    private void pay(User user) {
        if (MainPageActivity.carts != null) {
            if (MainPageActivity.carts.size() > 0) {
                String time = String.valueOf(calendar.getTimeInMillis()); //thời gian thanh toán
                String email = user.getEmail();
                email = email.replace("@", "");
                email = email.replace(".", "");

                //tạo đối tượng đồng hồ cụ thể tương ứng với mỗi mẫu trong giỏ hàng
                for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                    Cart cart = MainPageActivity.carts.get(i);
                    Clock clock = new Clock(cart.getName(), String.valueOf(time),
                            cart.getImage(), cart.getPrice(),
                            cart.getQuantity(), cart.getTotal()
                    );
                    clocks.add(clock);
                }

                //Giảm số lượng của mẫu ở database tương ứng trong giỏ hàng
                for (int k = 0; k < MainPageActivity.carts.size(); k++) {
                    Cart cart1 = MainPageActivity.carts.get(k);
                    mDataBase.child("Model").child(cart1.getName())
                            .child("quantity").setValue(cart1.getMaxQuantity() - cart1.getQuantity());
                }

                //Tăng số lượng đã bán của mẫu ở database tương ứng trong giỏ hàng
                for (int k = 0; k < MainPageActivity.carts.size(); k++) {
                    Cart cart1 = MainPageActivity.carts.get(k);
                    mDataBase.child("Model").child(cart1.getName())
                            .child("sold").setValue(cart1.getSold() + cart1.getQuantity());
                }

                //Thêm sản phẩm đã thanh toán vào lịch sử
                for (int j = 0; j < clocks.size(); j++) {
                    mDataBase.child("History").child(email).push().setValue(clocks.get(j));

                }

                //Clear giỏ hàng ở local và database
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setMessage(String.format(getString(R.string.payment_complete), txtTotal.getText().toString()));
                builder.setCancelable(true);
                builder.setPositiveButton(
                        R.string.back,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        }
                );
                final android.app.AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.my_secondary));
                    }
                });
                alertDialog.show();
                MainPageActivity.carts.clear();
                mDataBase.child("Cart").child(email).removeValue();
                clocks.clear();

                cartAdapter.notifyDataSetChanged();
                txtTotal.setText(String.valueOf(0));
            } else {
                //Thông báo khi người dùng thanh toán trong lúc giỏ hàng rỗng
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//                builder.setMessage("Giỏ hàng đang trống, vui lòng đặt hàng vào trước khi thanh toán!");
                builder.setMessage(getString(R.string.empty_cart_toast));
                builder.setCancelable(true);
                builder.setPositiveButton(
                        R.string.back,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        }
                );

                final android.app.AlertDialog alertDialog1 = builder.create();
                alertDialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.my_secondary));
                    }
                });
                alertDialog1.show();
            }
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
            case R.id.mn_History:
                Intent i_History = new Intent(CartActivity.this, HistoryActivity.class);
                startActivity(i_History);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Đưa các mẫu trong giỏ hàng mà người dùng chưa thanh toán lên database để sử dụng lại
     *
     * @param user1 người dùng
     */
    private void pushCart(User user1) {
        if (MainPageActivity.carts.size() > 0) {
            String mail = "";
            mail += user1.getEmail();
            mail = mail.replace("@", "");
            mail = mail.replace(".", "");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(mail);
            databaseReference.removeValue();
            for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                mDataBase.child("Cart").child(mail).push().setValue(MainPageActivity.carts.get(i));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        pushCart(SignInActivity.user);
        MainPageActivity.carts.clear();
        super.onDestroy();
    }
}
