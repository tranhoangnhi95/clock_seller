package com.example.immortal.clock_seller.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Activity.MainPageActivity;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Cart> carts;
    TextView txt_CTotal;

    public CartAdapter(Context context, int resource, ArrayList<Cart> carts, TextView txt_CTotal) {
        this.context = context;
        this.resource = resource;
        this.carts = carts;
        this.txt_CTotal = txt_CTotal;
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

    public class ViewHolder {
        public TextView txt_Name, txt_Price, txt_Total;
        public ImageView img_Image;
        public Button btn_Increase, btn_Decrease, btn_Quantity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_cart_item, null);
            viewHolder.txt_Name = view.findViewById(R.id.txt_CIProductName);
            viewHolder.txt_Price = view.findViewById(R.id.txt_CIProductPrice);
            viewHolder.txt_Total = view.findViewById(R.id.txt_CIProductTotal);
            viewHolder.img_Image = view.findViewById(R.id.img_CIImage);
            viewHolder.btn_Increase = view.findViewById(R.id.btn_CIIncrease);
            viewHolder.btn_Decrease = view.findViewById(R.id.btn_CIDecrease);
            viewHolder.btn_Quantity = view.findViewById(R.id.btn_CIQuantity);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Cart cart = (Cart) getItem(i);
        viewHolder.txt_Name.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_Price.setText("Giá: " + decimalFormat.format(cart.getPrice()) + " Đ");
        viewHolder.txt_Total.setText("Tổng: " + decimalFormat.format(cart.getTotal()) + " Đ");
//        Glide.with(context).load(cart.getImage())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.noimage)
//                .into(viewHolder.img_Image);
        Glide.with(context)
                .load(cart.getImage())
                .apply(
                        RequestOptions
                                .overrideOf(100,100)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(viewHolder.img_Image);
//        viewHolder.img_Image.setImageResource(cart.getImage());
        viewHolder.btn_Quantity.setText(String.valueOf(cart.getQuantity()));
        final Cart cart1 = cart;
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btn_Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) <= 1) {
                    carts.remove(carts.indexOf(cart1));
                    notifyDataSetChanged();
                } else {
                    int quantity = Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) - 1;
                    long c_total = cart1.getPrice() * quantity;
                    finalViewHolder.btn_Quantity.setText(String.valueOf(quantity));
                    carts.get(carts.indexOf(cart1)).setQuantity(quantity);
                    carts.get(carts.indexOf(cart1)).setTotal((int) c_total);
                    notifyDataSetChanged();

                }
                long total = 0;
                for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                    total += MainPageActivity.carts.get(i).getTotal();
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txt_CTotal.setText(decimalFormat.format(total));
            }
        });

        viewHolder.btn_Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) >= 10) {
                    finalViewHolder.btn_Quantity.setText(String.valueOf(10));
                    carts.get(carts.indexOf(cart1)).setQuantity(10);
                    notifyDataSetChanged();
                } else {
                    int quantity = Integer.valueOf(finalViewHolder.btn_Quantity.getText().toString()) + 1;
                    long c_total = cart1.getPrice() * quantity;
                    finalViewHolder.btn_Quantity.setText(String.valueOf(quantity));
                    carts.get(carts.indexOf(cart1)).setQuantity(quantity);
                    carts.get(carts.indexOf(cart1)).setTotal((int) c_total);
                    notifyDataSetChanged();
                }
                long total = 0;
                for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                    total += MainPageActivity.carts.get(i).getTotal();
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txt_CTotal.setText(decimalFormat.format(total));
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setMessage("Bạn muốn xóa sản phẩm?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                carts.remove(cart1);
                                dialogInterface.cancel();
                                notifyDataSetChanged();
                            }
                        }
                );

                builder.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );
                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
        return view;
    }
}
