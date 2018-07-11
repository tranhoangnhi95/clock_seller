package com.example.immortal.clock_seller.adapter;

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
import com.example.immortal.clock_seller.activity.MainPageActivity;
import com.example.immortal.clock_seller.activity.ProducstActivity;
import com.example.immortal.clock_seller.activity.ProductDetailActivity;
import com.example.immortal.clock_seller.myinterface.HotProductItemClickListner;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HotProductAdapter extends RecyclerView.Adapter<HotProductAdapter.ItemHolder> {
    public Context context;
    public int resource;
    public ArrayList<Model> models;

    /**
     * Hàm khởi tạo adapter sản phẩm hot
     *
     * @param context  context context (ngữ cảnh của ứng dụng)
     * @param resource layout
     * @param models   danh sách các mẫu
     */
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

        //format giá thành sang định dạng 000.000.000
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText(String.format(context.getString(R.string.price), decimalFormat.format(model.getPrice())));
        holder.txtDetail.setMaxLines(1);
        holder.txtDetail.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtDetail.setText(model.getDetail());

        //Load ảnh từ internet vào ImageView
        Glide.with(context)
                .load(model.getImage())
                .apply(
                        RequestOptions
                                .overrideOf(100, 100)
                                .placeholder(R.drawable.noimage)
                                .error(R.drawable.noimage)
                                .formatOf(DecodeFormat.PREFER_RGB_565)
                                .timeout(3000)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(holder.imgImage);


        final ItemHolder holder1 = holder;
        final Model model1 = model;


        holder.setHotProductItemClickListner(new HotProductItemClickListner() {

            /**
             * Sự kiện click button thêm sản phẩm vào giỏ hàng của điều khiển view tại vị trí position
             * @param view các điều khiển
             * @param position vị trí được click
             * @param isLongClick true nếu view được longclick, false đối với các trường hợp khác
             */
            @Override
            public void addToCartClick(View view, int position, boolean isLongClick) {
                /*
                Nếu giỏ hàng khác rỗng, kiểm tra tồn tại sản của mẫu tương ứng trong giỏ hàng
                    nếu không tồn tại thêm sản phẩm vào giỏ hàng, ngược lại tăng số lượng tương ứng
                 */
                if (MainPageActivity.carts.size() > 0) {
                    int quantity1 = Integer.parseInt(holder1.btnQuantity.getText().toString());
                    boolean exist = false; //biến xác định sản phẩm đã tồn tại trong giỏ hàng hay chưa
                    for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                        //kiểm tra sản phẩm có tồn tại trong giỏ hàng
                        if (MainPageActivity.carts.get(i).getName().equals(model1.getName())) {
                            MainPageActivity.carts.get(i).setQuantity(MainPageActivity.carts.get(i).getQuantity() + quantity1);
                            //kiểm tra số lượng đã đạt tối đa chưa, nếu tối đa giữ nguyên số lượng, ngược lại tăng số lượng tương ứng
                            if (MainPageActivity.carts.get(i).getQuantity() >= model.getQuantity()) {
                                MainPageActivity.carts.get(i).setQuantity(model.getQuantity());
                            }
                            //tính lại thành tiền cho sản phẩm vừa tăng số lượng
                            MainPageActivity.carts.get(i).setTotal(model1.getPrice() * MainPageActivity.carts.get(i).getQuantity());
                            exist = true;
                        }
                    }
                    //nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm vào giỏ hàng
                    if (!exist) {
                        int quantity = Integer.parseInt(holder1.btnQuantity.getText().toString());
                        long total = quantity * model1.getPrice();
                        MainPageActivity.carts.add(new Cart(model1.getName(), model1.getPrice(), (int) total, model1.getImage(), quantity, model1.getQuantity(), model.getSold()));
                    }
                } else { //nếu giỏ hàng rỗng thêm mẫu sản phẩm vào giỏ hàng
                    int quantity = Integer.parseInt(holder1.btnQuantity.getText().toString());
                    long total = quantity * model1.getPrice();
                    MainPageActivity.carts.add(new Cart(model1.getName(), model1.getPrice(), (int) total, model1.getImage(), quantity, model1.getQuantity(), model.getSold()));
                }
                Toast.makeText(context, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                holder1.btnQuantity.setText("1");
            }

            /**
             * Sự kiện click vào item  tại vị trí position của điều khiển view để xem chị tiết sản phẩm
             * @param view điều khiển
             * @param position vị trí
             * @param isLongClick true nếu view được longclick, false đối với các trường hợp khác
             */
            @Override
            public void itemClick(View view, int position, boolean isLongClick) {
                Intent i_ToProduct = new Intent(context, ProductDetailActivity.class);
                Model model2 = models.get(position);
                i_ToProduct.putExtra(ProducstActivity.intent_product_key, model2);
                view.getContext().startActivity(i_ToProduct);
            }

            /**
             * Sự kiện click button tăng số lượng một sản phẩm
             * @param view điều khiển
             * @param position vị trí được click
             * @param isLongClick true nếu view được longclick, false đối với các trường hợp khác
             */
            @Override
            public void increaseClick(View view, int position, boolean isLongClick) {
                //nếu số lượng hiện tại là tối đa, giữ nguyên số lượng
                if (Integer.valueOf(holder1.btnQuantity.getText().toString()) >= model.getQuantity()) {
                    holder1.btnQuantity.setText(String.valueOf(model.getQuantity()));
                } else {
                    //ngược lại tăng số lượng sản phẩm lên 1
                    holder1.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btnQuantity.getText().toString()) + 1));
                }
            }

            /**
             * Sự kiện click button giảm số lượng một sản phẩm
             * @param view điều khiển
             * @param position vị trí được click
             * @param isLongClick true nếu view được longclick, false đối với các trường hợp khác
             */
            @Override
            public void decreaseClick(View view, int position, boolean isLongClick) {
                //Nếu số lượng là nhỏ hơn hoặc bằng 1, giữ nguyên số lượng
                if (Integer.valueOf(holder1.btnQuantity.getText().toString()) <= 1) {
                    holder1.btnQuantity.setText(String.valueOf(1));
                } else {
                    //ngược lại, giảm số lượng sản phẩm đi 1
                    holder1.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            holder1.btnQuantity.getText().toString()) - 1));
                }
            }
        });


    }

    /**
     * Lấy số lượng item
     *
     * @return số lượng
     */
    @Override
    public int getItemCount() {
        return models.size();
    }

    /**
     * Lớp hỗ trợ khởi tạo item và giúp hoạt động nhanh hơn
     */
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
