package com.example.immortal.clock_seller.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.immortal.clock_seller.fragments.SignInFragment;
import com.example.immortal.clock_seller.fragments.SignUpFragment;

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public MyFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SignInFragment();
        } else {
            return new SignUpFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

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
