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
import com.example.immortal.clock_seller.R;

public class SignInFragment extends Fragment{
    Button btn_SignIn;
    TextView txt_Phone, txt_Pass;
    CheckBox cb_Remember;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signin_fragment,container,false);
        btn_SignIn = view.findViewById(R.id.btn_SISignIn);
        txt_Phone = view.findViewById(R.id.txt_SIPhone);
        txt_Pass = view.findViewById(R.id.txt_SIPass);
        cb_Remember = view.findViewById(R.id.btn_SIRemember);
        signInEvent();
        RememberEvent();
        return view;
    }

    private void RememberEvent() {
        cb_Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getContext(),"Tài khoản sẽ được lưu",Toast.LENGTH_SHORT).show();
//                    Intent i_ToProducts = new Intent(SignInActivity.this, ProducstActivity.class);
//                    startActivity(i_ToProducts);
                }
            }
        });
    }

    private void signInEvent() {
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ToMainPage = new Intent(getContext(),MainPageActivity.class);
                startActivity(i_ToMainPage);
            }
        });

    }



    @Override
    public void onPause() {
        super.onPause();
    }

}
