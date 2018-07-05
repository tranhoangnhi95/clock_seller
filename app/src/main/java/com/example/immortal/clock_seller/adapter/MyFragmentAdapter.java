package com.example.immortal.clock_seller.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.immortal.clock_seller.fragments.SignInFragment;
import com.example.immortal.clock_seller.fragments.SignUpFragment;

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    public MyFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.signInFragment = new SignInFragment();
        this.signUpFragment = new SignUpFragment();
    }

    /**
     * Lấy item ở vị trí position của adapter
     *
     * @param position vị trị
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return signInFragment;
        } else {
            return signUpFragment;
        }
    }

    /**
     * Trả về số lượng item của adapter
     *
     * @return số lượng
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * Lấy tiêu đề của trang tại vị trí position
     *
     * @param position vị trí
     * @return tiêu đề
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sign In";

            case 1:
                return "Sign Up";

            default:
                return null;
        }
    }
}
