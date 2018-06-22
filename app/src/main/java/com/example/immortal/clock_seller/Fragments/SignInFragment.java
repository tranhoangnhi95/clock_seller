package com.example.immortal.clock_seller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.MainPageActivity;
import com.example.immortal.clock_seller.Activity.SignInActivity;
import com.example.immortal.clock_seller.R;

public class SignInFragment extends Fragment{
    Button btn_SignIn;
    TextView txt_Email, txt_Pass;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signin_fragment,container,false);
        btn_SignIn = view.findViewById(R.id.btn_SISignIn);
        txt_Email = view.findViewById(R.id.txt_SIEmail);
        txt_Pass = view.findViewById(R.id.txt_SIPass);
        signInEvent();
        return view;
    }


    private void signInEvent() {
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email, pass;
                    email = txt_Email.getText().toString().toLowerCase();
                    pass = txt_Pass.getText().toString();
                    SignInActivity activity = (SignInActivity) getActivity();
                    activity.signInFragment(email,pass);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Vui lòng nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    public void onPause() {
        super.onPause();
    }

}
