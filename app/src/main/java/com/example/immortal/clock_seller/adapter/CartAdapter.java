package com.example.immortal.clock_seller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.immortal.clock_seller.model.Cart;
import com.example.immortal.clock_seller.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    public Context context;
    public int resource;
    public ArrayList<Cart> carts;
    public TextView txtCTotal;

    /**
     * Hàm khởi tạo adapter giỏ hàng.
     *
     * @param context   context (ngữ cảnh của ứng dụng)
     * @param resource  layout
     * @param carts     giỏ hàng
     * @param txtCTotal textview để cập nhật lại tổng khi thay đổi số lượng sản phẩm trong adapter
     */
    public CartAdapter(Context context, int resource, ArrayList<Cart> carts, TextView txtCTotal) {
        this.context = context;
        this.resource = resource;
        this.carts = carts;
        this.txtCTotal = txtCTotal;
    }

    /**
     * Lấy số lượng item
     *
     * @return số lượng
     */
    @Override
    public int getCount() {
        return carts.size();
    }

    /**
     * Lấy item tại vị trí i
     *
     * @param i vị trí
     * @return đối tượng Cart
     */
    @Override
    public Object getItem(int i) {
        return carts.get(i);
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

    /**
     * Lớp hỗ trợ khởi tạo item và giúp hoạt động nhanh hơn
     */
    public class ViewHolder {
        public TextView txtName, txtPrice, txtTotal;
        public ImageView imgImage;
        public Button btnIncrease, btnDecrease, btnQuantity;
        public ImageButton btnDelete;
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
            viewHolder.btnDelete = view.findViewById(R.id.btn_Delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHolder.txtName.setText(cart.getName());
        //format giá thành dàng 000.000.000
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(String.format(context.getString(R.string.price), decimalFormat.format(cart.getPrice())));
        viewHolder.txtTotal.setText(String.format(context.getString(R.string.total), decimalFormat.format(cart.getTotal())));
        //đọc ảnh từ internet truyền vào cho ImageView
        Glide.with(context)
                .load(cart.getImage()) //link ảnh
                .apply(
                        RequestOptions
                                .overrideOf(100, 100) //resize ảnh
                                .placeholder(R.drawable.noimage) //ảnh ban đầu
                                .error(R.drawable.noimage) //ảnh được sử dụng khi lỗi
                                .formatOf(DecodeFormat.PREFER_RGB_565) //format ảnh
                                .timeout(3000) //thời gian tối đa
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(viewHolder.imgImage);
        viewHolder.btnQuantity.setText(String.valueOf(cart.getQuantity()));
        final Cart cart1 = cart;
        final ViewHolder finalViewHolder = viewHolder;

        //sự kiện click button giảm số lượng
        viewHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //số lượng <1 thì sản phẩm sẽ bị xỏa khỏi giỏ hàng
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) <= 1) {
                    carts.remove(carts.indexOf(cart1));
                    notifyDataSetChanged();
                } else {
                    //giảm số lượng sản hẩm
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
                //Cập nhật lại  thành tiền ở giỏ hàng
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtCTotal.setText(decimalFormat.format(total));
            }
        });

        //sự kiện click button tăng số lượng sản
        viewHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nếu số lượng hiện tại là tối đa của mẫu sản phẩm, thì số lượng sẽ không tăng nữa
                if (Integer.valueOf(finalViewHolder.btnQuantity.getText().toString()) >= cart1.getMaxQuantity()) {
                    finalViewHolder.btnQuantity.setText(String.valueOf(cart1.getMaxQuantity()));
                    carts.get(carts.indexOf(cart1)).setQuantity(cart1.getMaxQuantity());
                    notifyDataSetChanged();
                } else {
                    //tăng số lượng sản phẩm
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
                //Cập nhật lại thành tiền ở giỏ hàng
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtCTotal.setText(decimalFormat.format(total));
            }
        });

        //sự kiện button xóa sản phẩm
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setMessage("Bạn muốn xóa sản phẩm?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                carts.remove(cart1); //xóa sản phẩm khỏi giỏ hàng
                                dialogInterface.cancel();
                                notifyDataSetChanged();

                                long total = 0;
                                for (int k = 0; k < carts.size(); k++) {
                                    total += carts.get(k).getTotal();
                                }
                                //Cập nhật lại thành tiền ở giỏ hàng
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
                final android.app.AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.my_secondary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.my_secondary));
                    }
                });
                alertDialog.show();
            }
        });

        //sự kiện longclick item
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
                                carts.remove(cart1); //xóa sản phẩm khỏi giỏ hàng
                                dialogInterface.cancel();
                                notifyDataSetChanged();

                                long total = 0;
                                for (int k = 0; k < carts.size(); k++) {
                                    total += carts.get(k).getTotal();
                                }
                                //Cập nhật lại thành tiền ở giỏ hàng
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
                final android.app.AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.my_secondary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.my_secondary));
                    }
                });
                alertDialog.show();
                return true;
            }
        });
        return view;
    }
}
