package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.CartActivity;
import com.example.immortal.clock_seller.Activity.MainPageActivity;
import com.example.immortal.clock_seller.Activity.ProducstActivity;
import com.example.immortal.clock_seller.Activity.ProductDetailActivity;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClockAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Clock> objects;

    public ClockAdapter(Context context, int resource, ArrayList<Clock> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    public class ViewHolder {
        public ImageView img_Image;
        public TextView txt_Name, txt_Price, txt_Detail;
        public Button btn_Decrease, btn_Quantity, btn_Increase;
        public ImageButton btn_AddToCart;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_products_item, null);
            viewHolder.img_Image = view.findViewById(R.id.img_ItemClock);
            viewHolder.txt_Name = view.findViewById(R.id.txt_ItemProductName);
            viewHolder.txt_Price = view.findViewById(R.id.txt_ItemProductPrice);
            viewHolder.txt_Detail = view.findViewById(R.id.txt_ItemProductDetail);
            viewHolder.btn_Decrease = view.findViewById(R.id.btn_ItemProductDecrease);
            viewHolder.btn_Quantity = view.findViewById(R.id.btn_ItemProductQuantity);
            viewHolder.btn_Increase = view.findViewById(R.id.btn_ItemProductIncrease);
            viewHolder.btn_AddToCart = view.findViewById(R.id.btn_ItemProductAddToCard);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Clock product = (Clock) getItem(i);
        viewHolder.txt_Name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_Price.setText("Giá : " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHolder.txt_Detail.setMaxLines(2);
        viewHolder.txt_Detail.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txt_Detail.setText(product.getDes());
        viewHolder.img_Image.setImageResource(product.getImg());
        final ViewHolder finalViewHolder = viewHolder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ToProductDetail = new Intent(context,ProductDetailActivity.class);
                i_ToProductDetail.putExtra(ProducstActivity.intent_product_key,product);
                context.startActivity(i_ToProductDetail);
            }
        });
        viewHolder.btn_Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) <= 0) {
                    finalViewHolder.btn_Quantity.setText(String.valueOf(0));
                } else {
                    finalViewHolder.btn_Quantity.setText(String.valueOf(Integer.valueOf(
                            finalViewHolder.btn_Quantity.getText().toString()) - 1));
                }
            }
        });

        viewHolder.btn_Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) >= 10) {
                    finalViewHolder.btn_Quantity.setText(String.valueOf(10));
                } else {
                    finalViewHolder.btn_Quantity.setText(String.valueOf(Integer.valueOf(
                            finalViewHolder.btn_Quantity.getText().toString()) + 1));
                }
            }
        });

        viewHolder.btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainPageActivity.carts.size() > 0) {
                    int quantity1 = Integer.parseInt(finalViewHolder.btn_Quantity.getText().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                        if (MainPageActivity.carts.get(i).getName().equals(product.getName())) {
                            MainPageActivity.carts.get(i).setQuantity(MainPageActivity.carts.get(i).getQuantity() + quantity1);
                            if (MainPageActivity.carts.get(i).getQuantity() >= 10) {
                                MainPageActivity.carts.get(i).setQuantity(10);
                            }
                            MainPageActivity.carts.get(i).setTotal(product.getPrice() * MainPageActivity.carts.get(i).getQuantity());
                            exist = true;
                        }
                    }
                    if (!exist) {
                        int quantity = Integer.parseInt(finalViewHolder.btn_Quantity.getText().toString());
                        long total = quantity * product.getPrice();
                        MainPageActivity.carts.add(new Cart(product.getName(),product.getPrice(),(int) total,
                                "https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/Rolex_AutomaticR140.jpg?alt=media&token=460b21df-ef45-4c93-96b9-2208ae77348d",
                                quantity
                                ));
                    }
                } else {
                    int quantity = Integer.parseInt(finalViewHolder.btn_Quantity.getText().toString());
                    long total = quantity * product.getPrice();
                    MainPageActivity.carts.add(new Cart(product.getName(),product.getPrice(),(int) total,
                            "https://firebasestorage.googleapis.com/v0/b/clockseller-5de25.appspot.com/o/Rolex_AutomaticR140.jpg?alt=media&token=460b21df-ef45-4c93-96b9-2208ae77348d",
                            quantity
                    ));
                }

//                Intent i_ToCart = new Intent(ProductDetailActivity.this,CartActivity.class);
//                startActivity(i_ToCart);
                notifyDataSetChanged();
                Toast.makeText(context,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
