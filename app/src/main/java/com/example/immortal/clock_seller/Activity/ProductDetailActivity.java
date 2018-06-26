package com.example.immortal.clock_seller.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    private Toolbar tbDetail;
    private ImageView imgImage;
    private TextView txtName, txtPrice, txtDetail;
    private Model model;
    private String name = "";
    private int price = 0;
    private String detail = "";
    private String img = "";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        inits();
        controls();
    }

    private void inits() {
        imgImage = findViewById(R.id.img_DtImage);
        txtName = findViewById(R.id.txt_DtName);
        txtPrice = findViewById(R.id.txt_DtPrice);
        txtDetail = findViewById(R.id.txt_DtDetail);
        tbDetail = findViewById(R.id.tb_Detail);

        setSupportActionBar(tbDetail);
        loadActionBar();
        setTitle("Chi tiết sản phẩm");
    }

    private void loadActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbDetail.setNavigationOnClickListener(new View.OnClickListener() {
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
        txtName.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText("Giá : " + decimalFormat.format(price) + " Đ");
        txtDetail.setText(detail);
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
                .into(imgImage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
