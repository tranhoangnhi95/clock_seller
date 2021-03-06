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
import com.example.immortal.clock_seller.model.MyMenuItem;
import com.example.immortal.clock_seller.R;

import java.util.ArrayList;

public class MyMenuItemAdapter extends BaseAdapter {
    public Context context;
    public int resources;
    public ArrayList<MyMenuItem> myMenuItems;

    public MyMenuItemAdapter(Context context, int resources, ArrayList<MyMenuItem> myMenuItems) {
        this.context = context;
        this.resources = resources;
        this.myMenuItems = myMenuItems;
    }

    /**
     * Lớp hỗ trợ khởi tạo item và giúp hoạt động nhanh hơn
     */
    public class ViewHolder{
        public ImageView imgImage;
        public TextView txtName;

    }

    /**
     * Lấy số lượng item adapter
     * @return số lượng
     */
    @Override
    public int getCount() {
        return myMenuItems.size();
    }

    /**
     * Lấy item tại vị trí i
     *
     * @param i vị trí
     * @return đối tượng MyMenuItem
     */
    @Override
    public Object getItem(int i) {
        return myMenuItems.get(i);
    }

    /**
     * Lấy id của item tại vị trí i
     *
     * @param i vị trị
     * @return id
     */
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
            viewHolder.imgImage = view.findViewById(R.id.img_MIImage);
            viewHolder.txtName = view.findViewById(R.id.txt_MIName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MyMenuItem item = (MyMenuItem) getItem(i);
        //Load ảnh từ internet vào ImageView
        Glide.with(context)
                .load(item.getImage())
                .apply(
                        RequestOptions
                                .overrideOf(70,70)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(viewHolder.imgImage);
        viewHolder.txtName.setText(item.getName());
        return view;
    }
}
