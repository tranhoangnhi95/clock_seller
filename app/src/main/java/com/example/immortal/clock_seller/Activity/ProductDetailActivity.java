package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar tb_Detail;
    ImageView img_Image;
    TextView txt_Name, txt_Price, txt_Detail;
    //    Button btn_AddToCart;
//    Spinner sp_Quantity;
    Model model;
    String name = "";
    int price = 0;
    String detail = "";
    String img = "";
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        inits();
        controls();
    }

    private void inits() {
        img_Image = findViewById(R.id.img_DtImage);
        txt_Name = findViewById(R.id.txt_DtName);
        txt_Price = findViewById(R.id.txt_DtPrice);
        txt_Detail = findViewById(R.id.txt_DtDetail);
//        btn_AddToCart = findViewById(R.id.btn_DtAddToCart);
//        sp_Quantity = findViewById(R.id.sp_DtQuantity);
        tb_Detail = findViewById(R.id.tb_Detail);

        setSupportActionBar(tb_Detail);
        loadActionBar();
        setTitle("Chi tiết sản phẩm");
    }

    private void loadActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tb_Detail.setNavigationOnClickListener(new View.OnClickListener() {
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
                Intent i_ToCart = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(i_ToCart);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void controls() {
        getInformation();

    }


    private void getInformation() {
        model = (Model) getIntent().getSerializableExtra(ProducstActivity.intent_product_key);
        name = model.getName();
        price = model.getPrice();
        detail = model.getDetail();
        img = model.getImage();
        txt_Name.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_Price.setText("Giá : " + decimalFormat.format(price) + " Đ");
        txt_Detail.setText(detail);
        Glide.with(getApplicationContext())
                .load(img)
                .apply(
                        RequestOptions
                                .overrideOf(150, 150)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(img_Image);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
