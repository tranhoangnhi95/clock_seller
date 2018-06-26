package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    public Context context;
    public int resource;
    public ArrayList<Cart> carts;
    public TextView txtCTotal;

    public CartAdapter(Context context, int resource, ArrayList<Cart> carts, TextView txtCTotal) {
        this.context = context;
        this.resource = resource;
        this.carts = carts;
        this.txtCTotal = txtCTotal;
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
        public TextView txtName, txtPrice, txtTotal;
        public ImageView imgImage;
        public Button btnIncrease, btnDecrease, btnQuantity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_cart_item, null);
            viewHolder.txtName = view.findViewById(R.id.txt_CIProductName);
            viewHolder.txtPrice = view.findViewById(R.id.txt_CIProductPrice);
            viewHolder.txtTotal = view.findViewById(R.id.txt_CIProductTotal);
            viewHolder.imgImage = view.findViewById(R.id.img_CIImage);
            viewHolder.btnIncrease = view.findViewById(R.id.btn_CIIncrease);
            viewHolder.btnDecrease = view.findViewById(R.id.btn_CIDecrease);
            viewHolder.btnQuantity = view.findViewById(R.id.btn_CIQuantity);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHolder.txtName.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText("Giá: " + decimalFormat.format(cart.getPrice()) + " Đ");
        viewHolder.txtTotal.setText("Tổng: " + decimalFormat.format(cart.getTotal()) + " Đ");
//        Glide.with(context).load(cart.getImage())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.noimage)
//                .into(viewHolder.imgImage);
        Glide.with(context)
                .load(cart.getImage())
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
//        viewHolder.imgImage.setImageResource(cart.getImage());
        viewHolder.btnQuantity.setText(String.valueOf(cart.getQuantity()));
        final Cart cart1 = cart;
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) <= 1) {
                    carts.remove(carts.indexOf(cart1));
                    notifyDataSetChanged();
                } else {
                    int quantity = Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) - 1;
                    long c_total = cart1.getPrice() * quantity;
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantity));
                    carts.get(carts.indexOf(cart1)).setQuantity(quantity);
                    carts.get(carts.indexOf(cart1)).setTotal((int) c_total);
                    notifyDataSetChanged();

                }
                long total = 0;
                for (int i = 0; i < carts.size(); i++) {
                    total += carts.get(i).getTotal();
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtCTotal.setText(decimalFormat.format(total));
            }
        });

        viewHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) >= 10) {
                    finalViewHolder.btnQuantity.setText(String.valueOf(10));
                    carts.get(carts.indexOf(cart1)).setQuantity(10);
                    notifyDataSetChanged();
                } else {
                    int quantity = Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) + 1;
                    long c_total = cart1.getPrice() * quantity;
                    finalViewHolder.btnQuantity.setText(String.valueOf(quantity));
                    carts.get(carts.indexOf(cart1)).setQuantity(quantity);
                    carts.get(carts.indexOf(cart1)).setTotal((int) c_total);
                    notifyDataSetChanged();
                }
                long total = 0;
                for (int i = 0; i < carts.size(); i++) {
                    total += carts.get(i).getTotal();
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtCTotal.setText(decimalFormat.format(total));
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

                                long total = 0;
                                for (int k = 0; k < carts.size(); k++) {
                                    total += carts.get(k).getTotal();
                                }
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                txtCTotal.setText(decimalFormat.format(total));
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
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent
//            }
//        });
        return view;
    }
}
