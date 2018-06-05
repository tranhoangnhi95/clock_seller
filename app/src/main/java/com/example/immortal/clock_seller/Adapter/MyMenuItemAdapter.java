package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.immortal.clock_seller.Model.MyMenuItem;
import com.example.immortal.clock_seller.R;

import java.util.ArrayList;

public class MyMenuItemAdapter extends BaseAdapter {
    Context context;
    int resources;
    ArrayList<MyMenuItem> myMenuItems;

    public MyMenuItemAdapter(Context context, int resources, ArrayList<MyMenuItem> myMenuItems) {
        this.context = context;
        this.resources = resources;
        this.myMenuItems = myMenuItems;
    }

    public class ViewHolder{
        public ImageView img_Image;
        public TextView txt_Name;

    }
    @Override
    public int getCount() {
        return myMenuItems.size();
    }

    @Override
    public Object getItem(int i) {
        return myMenuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_menu_item,null);
            viewHolder.img_Image = view.findViewById(R.id.img_MIImage);
            viewHolder.txt_Name = view.findViewById(R.id.txt_MIName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MyMenuItem item = (MyMenuItem) getItem(i);
        viewHolder.img_Image.setImageResource(item.getImage());
        viewHolder.txt_Name.setText(item.getName());
        return view;
    }
}
