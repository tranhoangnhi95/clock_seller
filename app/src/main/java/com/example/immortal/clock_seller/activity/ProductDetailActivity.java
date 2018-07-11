package com.example.immortal.clock_seller.activity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.model.Model;
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

    /**
     * Ánh xạ các view và khỏi tạo giá trị
     */
    private void inits() {
        imgImage = findViewById(R.id.img_DtImage);
        txtName = findViewById(R.id.txt_DtName);
        txtPrice = findViewById(R.id.txt_DtPrice);
        txtDetail = findViewById(R.id.txt_DtDetail);
        tbDetail = findViewById(R.id.tb_Detail);

        setSupportActionBar(tbDetail);
        loadActionBar();
        setTitle("Chi tiết sản phẩm");
        getInformation();
    }

    /**
     * Tạo đối tượng Navigation button trên ActionBar
     */
    private void loadActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tbDetail.setNavigationIcon(R.drawable.arrow_back_24dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_add_to_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_Check:
                addToCart();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToCart() {
        if (MainPageActivity.carts.size() > 0) {
            boolean exist = false; //biến xác định sản phẩm đã tồn tại trong giỏ hàng hay chưa
            for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                //kiểm tra sản phẩm có tồn tại trong giỏ hàng
                if (MainPageActivity.carts.get(i).getName().equals(model.getName())) {
                    MainPageActivity.carts.get(i).setQuantity(MainPageActivity.carts.get(i).getQuantity() + 1);
                    //kiểm tra số lượng đã đạt tối đa chưa, nếu tối đa giữ nguyên số lượng, ngược lại tăng số lượng tương ứng
                    if (MainPageActivity.carts.get(i).getQuantity() >= model.getQuantity()) {
                        MainPageActivity.carts.get(i).setQuantity(model.getQuantity());
                    }
                    //tính lại thành tiền cho sản phẩm vừa tăng số lượng
                    MainPageActivity.carts.get(i).setTotal(model.getPrice() * MainPageActivity.carts.get(i).getQuantity());
                    exist = true;
                }
            }
            //nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm vào giỏ hàng
            if (!exist) {
                MainPageActivity.carts.add(new Cart(model.getName(),model.getPrice(),model.getPrice(),model.getImage(),1,model.getQuantity(), model.getSold()));
            }
        } else { //nếu giỏ hàng rỗng thêm mẫu sản phẩm vào giỏ hàng
            MainPageActivity.carts.add(new Cart(model.getName(),model.getPrice(),model.getPrice(),model.getImage(),1,model.getQuantity(), model.getSold()));
        }
        Toast.makeText(ProductDetailActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
    }

    /**
     * Thêm các sự kiện lắng nghe, điều khiển
     */
    private void controls() {

        tbDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    /**
     * Nhận dữ liệu sản phẩm từ Acrivity Product và đổ vào layout
     */
    @SuppressLint("StringFormatMatches")
    private void getInformation() {
        model = (Model) getIntent().getSerializableExtra(ProducstActivity.intent_product_key);
        name = model.getName();
        price = model.getPrice();
        detail = model.getDetail();
        img = model.getImage();
        txtName.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        txtPrice.setText("Giá : " + decimalFormat.format(price) + " Đ");
        txtPrice.setText(String.format(getString(R.string.price),decimalFormat.format(price)));
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
