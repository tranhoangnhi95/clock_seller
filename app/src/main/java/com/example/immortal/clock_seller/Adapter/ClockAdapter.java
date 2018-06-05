package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.MainPageActivity;
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
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Clock product = (Clock) getItem(i);
        viewHolder.txt_Name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_Price.setText("Giá : " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHolder.txt_Detail.setMaxLines(2);
        viewHolder.txt_Detail.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txt_Detail.setText(product.getDes());
        viewHolder.img_Image.setImageResource(product.getImg());
        final ViewHolder finalViewHolder = viewHolder;
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
        return view;
    }

}
