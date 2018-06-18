package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.Model.SoldClock;
import com.example.immortal.clock_seller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<SoldClock> objects;

    public HistoryAdapter(Context context, int resource, ArrayList<SoldClock> objects) {
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
        Model clock = (Model) getItem(i);

//        viewHolder.txt_Name.setText(clock.getName());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        viewHolder.txt_Total.setText("Tổng: "+ decimalFormat.format(soldClock.getPrice())+" Đ");
//        viewHolder.txt_DateOfPay.setText("Thanh toán: "+soldClock.getDateOfPay());
//        viewHolder.txt_Quantity.setText(String.valueOf(soldClock.getQuantity()));
        return view;
    }
}
