package com.example.immortal.clock_seller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.activity.SignInActivity;
import com.example.immortal.clock_seller.R;

public class SignInFragment extends Fragment {
    public Button btnSignIn;
    public TextView txtEmail, txtPass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signin_fragment, container, false);

        inits(view);
        signInEvent();
        return view;
    }

    private void inits(View view) {
        btnSignIn = view.findViewById(R.id.btn_SISignIn);
        txtEmail = view.findViewById(R.id.txt_SIEmail);
        txtPass = view.findViewById(R.id.txt_SIPass);
    }


    private void signInEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email, pass;
                    email = txtEmail.getText().toString().toLowerCase();
                    pass = txtPass.getText().toString();
                    SignInActivity activity = (SignInActivity) getActivity();
                    activity.signInFragment(email, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Vui lòng nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
    }

}
