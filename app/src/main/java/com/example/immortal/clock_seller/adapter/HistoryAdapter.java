package com.example.immortal.clock_seller.adapter;

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
import com.example.immortal.clock_seller.model.Clock;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    public Context context;
    public int resource;
    public ArrayList<Clock> objects;

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
        public ImageView imgImage;
        public TextView txtName, txtTotal, txtPrice, txtQuantity, txtDateOfPay;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_history_item,null);
            viewHolder.imgImage = view.findViewById(R.id.img_HIImage);
            viewHolder.txtName = view.findViewById(R.id.txt_HIProductName);
            viewHolder.txtTotal = view.findViewById(R.id.txt_HITotal);
            viewHolder.txtDateOfPay = view.findViewById(R.id.txt_HIDateOfPay);
            viewHolder.txtPrice = view.findViewById(R.id.txt_HIPrice);
            viewHolder.txtQuantity = view.findViewById(R.id.txt_HIQuantity);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Clock clock = (Clock) getItem(i);

        viewHolder.txtName.setText(clock.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        viewHolder.txtPrice.setText("Giá: " +decimalFormat.format(clock.getPrice())+" Đ");
//        viewHolder.txtTotal.setText("Thành tiền: "+decimalFormat.format(clock.getTotal())+" Đ");
        viewHolder.txtPrice.setText(String.format(context.getString(R.string.price),decimalFormat.format(clock.getPrice())));
        viewHolder.txtTotal.setText(String.format(context.getString(R.string.total),decimalFormat.format(clock.getTotal())));
        viewHolder.txtQuantity.setText(String.valueOf(clock.getQuantity()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        viewHolder.txtDateOfPay.setText(String.format(context.getString(R.string.date),dateFormat.format(Long.parseLong(clock.getDate()))));

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
                .into(viewHolder.imgImage);
        return view;
    }
}
