package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.Model.SoldClock;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Clock> objects;

    public HistoryAdapter(Context context, int resource, ArrayList<Clock> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
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
    public class ViewHolder{
        public ImageView img_Image;
        public TextView txt_Name, txt_Total, txt_Price, txt_Quantity, txt_DateOfPay;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_history_item,null);
            viewHolder.img_Image = view.findViewById(R.id.img_HIImage);
            viewHolder.txt_Name = view.findViewById(R.id.txt_HIProductName);
            viewHolder.txt_Total = view.findViewById(R.id.txt_HITotal);
            viewHolder.txt_DateOfPay = view.findViewById(R.id.txt_HIDateOfPay);
            viewHolder.txt_Price = view.findViewById(R.id.txt_HIPrice);
            viewHolder.txt_Quantity = view.findViewById(R.id.txt_HIQuantity);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Clock clock = (Clock) getItem(i);

        viewHolder.txt_Name.setText(clock.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_Price.setText("Giá: " +decimalFormat.format(clock.getPrice())+" Đ");
        viewHolder.txt_Total.setText("Thành tiền: "+decimalFormat.format(clock.getTotal())+" Đ");
        viewHolder.txt_Quantity.setText(String.valueOf(clock.getQuantity()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        viewHolder.txt_DateOfPay.setText("Thanh toán: "+ dateFormat.format(Long.parseLong(clock.getDate())));

        Glide.with(context)
                .load(clock.getImage())
                .apply(
                        RequestOptions
                                .overrideOf(100, 100)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(viewHolder.img_Image);
        return view;
    }
}
