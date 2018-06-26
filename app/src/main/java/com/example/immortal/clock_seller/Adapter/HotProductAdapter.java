package com.example.immortal.clock_seller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.Activity.MainPageActivity;
import com.example.immortal.clock_seller.Activity.ProducstActivity;
import com.example.immortal.clock_seller.Activity.ProductDetailActivity;
import com.example.immortal.clock_seller.Interface.HotProductItemClickListner;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HotProductAdapter extends RecyclerView.Adapter<HotProductAdapter.ItemHolder> {
    public Context context;
    public int resource;
    public ArrayList<Model> models;

    public HotProductAdapter(Context context, int resource, ArrayList<Model> models) {
        this.context = context;
        this.resource = resource;
        this.models = models;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_hot_product_item, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Model model = models.get(position);
        holder.txtName.setMaxLines(1);
        holder.txtName.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtName.setText(model.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Giá: " + decimalFormat.format(model.getPrice()) + " Đ");
        holder.txtDetail.setMaxLines(1);
        holder.txtDetail.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtDetail.setText(model.getDetail());
//        Glide.with(context).load(model.getImage())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.noimage)
//                .override(100,100)
//                .into(holder.imgImage);
        Glide.with(context)
                .load(model.getImage())
                .apply(
                        RequestOptions
                                .overrideOf(100,100)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(holder.imgImage);
//        holder.imgImage.setImageResource(R.drawable.menu);
        final ItemHolder holder1 = holder;
        final Model model1 = model;
        holder.setHotProductItemClickListner(new HotProductItemClickListner() {
            @Override
            public void addToCartClick(View view, int position, boolean isLongClick) {
                if (MainPageActivity.carts.size() > 0) {
                    int quantity1 = Integer.parseInt(holder1.btnQuantity.getText().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                        if (MainPageActivity.carts.get(i).getName().equals(model1.getName())) {
                            MainPageActivity.carts.get(i).setQuantity(MainPageActivity.carts.get(i).getQuantity() + quantity1);
                            if (MainPageActivity.carts.get(i).getQuantity() >= 10) {
                                MainPageActivity.carts.get(i).setQuantity(10);
                            }
                            MainPageActivity.carts.get(i).setTotal(model1.getPrice() * MainPageActivity.carts.get(i).getQuantity());
                            exist = true;
                        }
                    }
                    if (!exist) {
                        int quantity = Integer.parseInt(holder1.btnQuantity.getText().toString());
                        long total = quantity * model1.getPrice();
                        MainPageActivity.carts.add(new Cart(model1.getName(),model1.getPrice() ,(int) total, model1.getImage(), quantity));
                    }
                } else {
                    int quantity = Integer.parseInt(holder1.btnQuantity.getText().toString());
                    long total = quantity * model1.getPrice();
                    MainPageActivity.carts.add(new Cart(model1.getName(),model1.getPrice() ,(int) total, model1.getImage(), quantity));
                }
                Toast.makeText(context,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemClick(View view, int position, boolean isLongClick) {
                Intent i_ToProduct = new Intent(context,ProductDetailActivity.class);
                Model model2 = models.get(position);
                i_ToProduct.putExtra(ProducstActivity.intent_product_key,model2);
                view.getContext().startActivity(i_ToProduct);
            }

            @Override
            public void increaseClick(View view, int position, boolean isLongClick) {
                if (Integer.valueOf(holder1.btnQuantity.getText().toString()) >= 10) {
                    holder1.btnQuantity.setText(String.valueOf(10));
                } else {
                    holder1.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btnQuantity.getText().toString()) + 1));
                }
            }

            @Override
            public void decreaseClick(View view, int position, boolean isLongClick) {
                if (Integer.valueOf(holder1.btnQuantity.getText().toString()) <= 0) {
                    holder1.btnQuantity.setText(String.valueOf(0));
                } else {
                    holder1.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btnQuantity.getText().toString()) - 1));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgImage;
        public TextView txtName, txtPrice, txtDetail;
        public ImageButton btnAddToCart, btnDecrease, btnIncrease;
        public Button btnQuantity;
        public HotProductItemClickListner hotProductItemClickListner;

        public ItemHolder(View itemView) {
            super(itemView);
            this.imgImage = itemView.findViewById(R.id.img_HPImage);
            this.txtName = itemView.findViewById(R.id.txt_HPName);
            this.txtPrice = itemView.findViewById(R.id.txt_HPPrice);
            this.txtDetail = itemView.findViewById(R.id.txt_HPDetail);
            this.btnAddToCart = itemView.findViewById(R.id.btn_HPAddToCart);
            this.btnDecrease = itemView.findViewById(R.id.btn_HPDecrease);
            this.btnQuantity = itemView.findViewById(R.id.btn_HPQuantity);
            this.btnIncrease = itemView.findViewById(R.id.btn_HPIncrease);

            itemView.setOnClickListener(this);
            btnDecrease.setOnClickListener(this);
            btnIncrease.setOnClickListener(this);
            btnAddToCart.setOnClickListener(this);
        }

        public void setHotProductItemClickListner(HotProductItemClickListner hotProductItemClickListner) {
            this.hotProductItemClickListner = hotProductItemClickListner;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_HPAddToCart:
                    hotProductItemClickListner.addToCartClick(view, getAdapterPosition(), false);
                    break;
                case R.id.btn_HPDecrease:
                    hotProductItemClickListner.decreaseClick(view, getAdapterPosition(), false);
                    break;
                case R.id.btn_HPIncrease:
                    hotProductItemClickListner.increaseClick(view, getAdapterPosition(), false);
                    break;
                default:
                    hotProductItemClickListner.itemClick(view, getAdapterPosition(), false);
                    break;
            }
        }

    }
}
