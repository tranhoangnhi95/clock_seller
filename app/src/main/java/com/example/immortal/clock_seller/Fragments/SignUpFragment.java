package com.example.immortal.clock_seller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.ProfileActivity;
import com.example.immortal.clock_seller.Activity.SignInActivity;
import com.example.immortal.clock_seller.R;

public class SignUpFragment extends Fragment {
    TextView txt_Name,txt_Phone, txt_Email, txt_Address, txt_Pass;
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
        txt_Name = view.findViewById(R.id.txt_SUName);
        txt_Phone = view.findViewById(R.id.txt_SUPhone);
        txt_Email = view.findViewById(R.id.txt_SUEmail);
        txt_Address = view.findViewById(R.id.txt_SUAddress);
        txt_Pass = view.findViewById(R.id.txt_SUPass);
        signUpEvents();
        return view;
    }

    private void signUpEvents() {
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        String Name, Phone, Email, Address, Pass;
                        Name = txt_Name.getText().toString();
                        Phone = txt_Phone.getText().toString();
                        Email = txt_Email.getText().toString().toLowerCase();
                        Address = txt_Address.getText().toString();
                        Pass = txt_Pass.getText().toString();

                        SignInActivity activity = (SignInActivity) getActivity();
                        activity.signUpFragment( Name, Phone, Email, Address, Pass);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(),"Vui lòng nhập dầy đủ thông tin trước khi đăng ký", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
