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
import com.example.immortal.clock_seller.Activity.MainPageActivity;
import com.example.immortal.clock_seller.Activity.ProducstActivity;
import com.example.immortal.clock_seller.Activity.ProductDetailActivity;
import com.example.immortal.clock_seller.Interface.HotProductItemClickListner;
import com.example.immortal.clock_seller.Model.Cart;
import com.example.immortal.clock_seller.Model.Clock;
import com.example.immortal.clock_seller.Model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HotProductAdapter extends RecyclerView.Adapter<HotProductAdapter.ItemHolder> {
    Context context;
    int resource;
    ArrayList<Model> models;

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
        holder.txt_Name.setMaxLines(1);
        holder.txt_Name.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_Name.setText(model.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_Price.setText("Giá: " + decimalFormat.format(model.getPrice()) + " Đ");
        holder.txt_Detail.setMaxLines(1);
        holder.txt_Detail.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_Detail.setText(model.getDetail());
        Glide.with(context).load(model.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .override(100,100)
                .into(holder.img_Image);
//        holder.img_Image.setImageResource(R.drawable.menu);
        final ItemHolder holder1 = holder;
        final Model model1 = model;
        holder.setHotProductItemClickListner(new HotProductItemClickListner() {
            @Override
            public void addToCartClick(View view, int position, boolean isLongClick) {
                if (MainPageActivity.carts.size() > 0) {
                    int quantity1 = Integer.parseInt(holder1.btn_Quantity.getText().toString());
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
                        int quantity = Integer.parseInt(holder1.btn_Quantity.getText().toString());
                        long total = quantity * model1.getPrice();
                        MainPageActivity.carts.add(new Cart(model1.getName(),model1.getPrice() ,(int) total, model1.getImage(), quantity));
                    }
                } else {
                    int quantity = Integer.parseInt(holder1.btn_Quantity.getText().toString());
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
                if (Integer.valueOf(holder1.btn_Quantity.getText().toString()) >= 10) {
                    holder1.btn_Quantity.setText(String.valueOf(10));
                } else {
                    holder1.btn_Quantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btn_Quantity.getText().toString()) + 1));
                }
            }

            @Override
            public void decreaseClick(View view, int position, boolean isLongClick) {
                if (Integer.valueOf(holder1.btn_Quantity.getText().toString()) <= 0) {
                    holder1.btn_Quantity.setText(String.valueOf(0));
                } else {
                    holder1.btn_Quantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btn_Quantity.getText().toString()) - 1));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img_Image;
        public TextView txt_Name, txt_Price, txt_Detail;
        public ImageButton btn_AddToCart, btn_Decrease, btn_Increase;
        public Button btn_Quantity;
        public HotProductItemClickListner hotProductItemClickListner;

        public ItemHolder(View itemView) {
            super(itemView);
            this.img_Image = itemView.findViewById(R.id.img_HPImage);
            this.txt_Name = itemView.findViewById(R.id.txt_HPName);
            this.txt_Price = itemView.findViewById(R.id.txt_HPPrice);
            this.txt_Detail = itemView.findViewById(R.id.txt_HPDetail);
            this.btn_AddToCart = itemView.findViewById(R.id.btn_HPAddToCart);
            this.btn_Decrease = itemView.findViewById(R.id.btn_HPDecrease);
            this.btn_Quantity = itemView.findViewById(R.id.btn_HPQuantity);
            this.btn_Increase = itemView.findViewById(R.id.btn_HPIncrease);

            itemView.setOnClickListener(this);
            btn_Decrease.setOnClickListener(this);
            btn_Increase.setOnClickListener(this);
            btn_AddToCart.setOnClickListener(this);
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
