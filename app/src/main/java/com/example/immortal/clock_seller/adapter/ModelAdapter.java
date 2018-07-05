package com.example.immortal.clock_seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.model.Model;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ModelAdapter extends BaseAdapter implements Filterable {
    public Context context;
    public int resource;
    public ArrayList<Model> models;
    public ArrayList<Model> tModels; //danh sách phụ hỗ trợ tìm kiếm
    public ValueFilter valueFilter;

    /**
     * Hàm khởi tạo adapter mẫu sản phẩm
     *
     * @param context  context(ngữ cảnh)
     * @param resource layout
     * @param models   danh sách các mẫu
     */
    public ModelAdapter(Context context, int resource, ArrayList<Model> models) {
        this.context = context;
        this.resource = resource;
        this.models = models;

        tModels = new ArrayList<Model>();
    }

    /**
     * Trả về bộ lọc của adapter
     *
     * @return Filter
     */
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    /**
     * Lớp hỗ trợ khởi tạo item và giúp hoạt động nhanh hơn
     */
    public class ViewHolder {
        public ImageView imgImage;
        public TextView txtName, txtPrice, txtDetail;
        public Button btnDecrease, btnQuantity, btnIncrease;
        public ImageButton btnAddToCart;

    }

    /**
     * Lấy số lượng item
     *
     * @return số lượng
     */
    @Override
    public int getCount() {
        return models.size();
    }

    /**
     * Lấy item tại vị trí i
     *
     * @param i
     * @return đối tượng Model
     */
    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    /**
     * Lấy id của item tại vị trí i
     *
     * @param i vị trí
     * @return id
     */
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
            viewHolder.imgImage = view.findViewById(R.id.img_ItemClock);
            viewHolder.txtName = view.findViewById(R.id.txt_ItemProductName);
            viewHolder.txtPrice = view.findViewById(R.id.txt_ItemProductPrice);
            viewHolder.txtDetail = view.findViewById(R.id.txt_ItemProductDetail);
            viewHolder.btnDecrease = view.findViewById(R.id.btn_ItemProductDecrease);
            viewHolder.btnQuantity = view.findViewById(R.id.btn_ItemProductQuantity);
            viewHolder.btnIncrease = view.findViewById(R.id.btn_ItemProductIncrease);
            viewHolder.btnAddToCart = view.findViewById(R.id.btn_ItemProductAddToCard);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Model model = (Model) getItem(i);
        viewHolder.txtName.setText(model.getName());
        //format giá trị của giá sản phẩm thành dạng 000.000.000
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(String.format(context.getString(R.string.price), decimalFormat.format(model.getPrice())));
        viewHolder.txtDetail.setMaxLines(2);
        viewHolder.txtDetail.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDetail.setText(model.getDetail());
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
                .into(viewHolder.imgImage);
        final ModelAdapter.ViewHolder finalViewHolder = viewHolder;
        //sự kiện itemclick để xem thông tin chi tiết của một sản phẩm
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ToProductDetail = new Intent(context, ProductDetailActivity.class);
                i_ToProductDetail.putExtra(ProducstActivity.intent_product_key, model);
                context.startActivity(i_ToProductDetail);
            }
        });

        //sự kiện click button giảm số lượng sản phẩm
        viewHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nếu số lượng là nhỏ hơn hoặc bằng 1, giữ nguyên số lượng
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) <= 1) {
                    finalViewHolder.btnQuantity.setText(String.valueOf(1));
                } else {
                    //ngược lại, giảm số lượng sản phẩm đi 1
                    finalViewHolder.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            finalViewHolder.btnQuantity.getText().toString()) - 1));
                }
            }
        });
        //sự kiện click button tăng số lượng sản phẩm
        viewHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nếu số lượng hiện tại là tối đa, giữ nguyên số lượng
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) >= model.getQuantity()) {
                    finalViewHolder.btnQuantity.setText(String.valueOf(model.getQuantity()));
                } else {
                    //ngược lại tăng số lượng sản phẩm lên 1
                    finalViewHolder.btnQuantity.setText(String.valueOf(Integer.valueOf(
                            finalViewHolder.btnQuantity.getText().toString()) + 1));
                }
            }
        });

        //sự kiện click button thêm sản phẩm vào giỏ hàng
        viewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Nếu giỏ hàng khác rỗng, kiểm tra tồn tại sản của mẫu tương ứng trong giỏ hàng
                    nếu không tồn tại thêm sản phẩm vào giỏ hàng, ngược lại tăng số lượng tương ứng
                 */
                if (MainPageActivity.carts.size() > 0) {
                    int quantity1 = Integer.parseInt(finalViewHolder.btnQuantity.getText().toString());
                    boolean exist = false; //biến xác định sản phẩm đã tồn tại trong giỏ hàng hay chưa
                    for (int i = 0; i < MainPageActivity.carts.size(); i++) {
                        //kiểm tra sản phẩm có tồn tại trong giỏ hàng
                        if (MainPageActivity.carts.get(i).getName().equals(model.getName())) {
                            MainPageActivity.carts.get(i).setQuantity(MainPageActivity.carts.get(i).getQuantity() + quantity1);
                            //kiểm tra số lượng đã đạt tối đa chưa, nếu tối đa giữ nguyên số lượng, ngược lại tăng số lượng tương ứng
                            if (MainPageActivity.carts.get(i).getQuantity() >= model.getQuantity()) {
                                MainPageActivity.carts.get(i).setQuantity(model.getQuantity());
                            }
                            //tính lại thành tiền cho sản phẩm vừa tăng số lượng
                            MainPageActivity.carts.get(i).setTotal(model.getPrice() * MainPageActivity.carts.get(i).getQuantity());
                            exist = true;
                        }
                    }
                    //nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm vào giỏ hàng
                    if (!exist) {
                        int quantity = Integer.parseInt(finalViewHolder.btnQuantity.getText().toString());
                        long total = quantity * model.getPrice();
                        MainPageActivity.carts.add(new Cart(model.getName(), model.getPrice(), (int) total,
                                model.getImage(),
                                quantity, model.getQuantity(), model.getSold()
                        ));
                    }
                } else {//nếu giỏ hàng rỗng thêm mẫu sản phẩm vào giỏ hàng
                    int quantity = Integer.parseInt(finalViewHolder.btnQuantity.getText().toString());
                    long total = quantity * model.getPrice();
                    MainPageActivity.carts.add(new Cart(model.getName(), model.getPrice(), (int) total,
                            model.getImage(),
                            quantity, model.getQuantity(), model.getSold()
                    ));
                }
                notifyDataSetChanged();
                Toast.makeText(context, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    //lớp hỗ trợ lọc sản phẩm trong adapter
    private class ValueFilter extends Filter {
        /**
         * Lọc trong danh sách với chuỗi lọc constraint
         * @param constraint chuỗi dùng để lọc
         * @return các kết quả tính toán và số lượng các giá trị
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            //nếu đối tượng rỗng, thêm tất cả các phần tử vào từ danh sách chính
            if (tModels.isEmpty()) {
                tModels.addAll(models);
            }
            //nếu chuỗi lọc không rỗng hoặc khác null sử dụng chuỗi để lọc
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Model> filterList = new ArrayList<Model>();

                for (int i = 0; i < tModels.size(); i++) {
                    if ((tModels.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) //kiểm tra tên của sản phẩm chứa chuỗi lọc
                            || (tModels.get(i).getDetail().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) //kiểm tra mô tả của sản phẩm chứa chuỗi lọc
                            || (String.valueOf(tModels.get(i).getPrice()).toUpperCase()) //kiểm tra giá của sản phẩm chứa chuỗi lọc
                            .contains(constraint.toString().toUpperCase())) {

                        Model model = new Model(tModels.get(i).getName(),
                                tModels.get(i).getB_name(), tModels.get(i).getDetail(),
                                tModels.get(i).getImage(), tModels.get(i).getPrice(),
                                tModels.get(i).getQuantity(), tModels.get(i).getSold());

                        filterList.add(model); //thêm mẫu sản phẩm vào danh sách đã lọc
                    }
                }
                //đưa danh sách bộ lọc vào kết quả
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = tModels.size();
                results.values = tModels;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            models = (ArrayList<Model>) results.values;
            notifyDataSetChanged();
        }

    }

}
