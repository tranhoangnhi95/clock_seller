package com.example.immortal.clock_seller.Interface;

import android.view.View;

public interface HotProductItemClickListner {
    void addToCartClick(View view, int position, boolean isLongClick);
    void itemClick(View view, int position, boolean isLongClick);
    void increaseClick(View view, int position, boolean isLongClick);
    void decreaseClick(View view, int position, boolean isLongClick);
}
