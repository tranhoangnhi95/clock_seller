package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Cart> carts;

    public CartAdapter(Context context, int resource, ArrayList<Cart> carts) {
        this.context = context;
        this.resource = resource;
        this.carts = carts;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int i) {
        return carts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txt_Name, txt_Total;
        public ImageView img_Image;
        public Button btn_Increase, btn_Decrease, btn_Quantity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_cart_item,null);
            viewHolder.txt_Name = view.findViewById(R.id.txt_CIProductName);
            viewHolder.txt_Total = view.findViewById(R.id.txt_CIProductTotal);
            viewHolder.img_Image = view.findViewById(R.id.img_CIImage);
            viewHolder.btn_Increase = view.findViewById(R.id.btn_CIIncrease);
            viewHolder.btn_Decrease = view.findViewById(R.id.btn_CIDecrease);
            viewHolder.btn_Quantity = view.findViewById(R.id.btn_CIQuantity);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHolder.txt_Name.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_Total.setText("Tổng: "+ decimalFormat.format(cart.getTotal())+" Đ");
        viewHolder.img_Image.setImageResource(cart.getImage());
        viewHolder.btn_Quantity.setText(String.valueOf(cart.getQuantity()));
        return view;
    }
}
