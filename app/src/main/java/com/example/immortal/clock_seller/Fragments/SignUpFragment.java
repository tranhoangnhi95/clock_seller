package com.example.immortal.clock_seller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.ProfileActivity;
import com.example.immortal.clock_seller.R;

public class SignUpFragment extends Fragment {
    Button btn_SignUp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signup_fragment,container,false);
        btn_SignUp = view.findViewById(R.id.btn_SUSignUp);
        signUpEvents();
        return view;
    }

    private void signUpEvents() {
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                Intent i_ToProfile = new Intent(getContext(), ProfileActivity.class);
                startActivity(i_ToProfile);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
