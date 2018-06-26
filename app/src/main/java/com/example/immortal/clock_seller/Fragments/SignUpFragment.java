package com.example.immortal.clock_seller.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.Activity.SignInActivity;
import com.example.immortal.clock_seller.R;

public class SignUpFragment extends Fragment {
    public TextView txtName, txtPhone, txtEmail, txtAddress, txtPass;
    public Button btnSignUp;

    public interface FragmentCommunication{
        public void EmailSending(String email);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signup_fragment,container,false);
        inits(view);
        signUpEvents();
        return view;
    }

    private void inits(View view) {
        btnSignUp = view.findViewById(R.id.btn_SUSignUp);
        txtName = view.findViewById(R.id.txt_SUName);
        txtPhone = view.findViewById(R.id.txt_SUPhone);
        txtEmail = view.findViewById(R.id.txt_SUEmail);
        txtAddress = view.findViewById(R.id.txt_SUAddress);
        txtPass = view.findViewById(R.id.txt_SUPass);
    }

    private void signUpEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        String Name, Phone, Email, Address, Pass;
                        Name = txtName.getText().toString();
                        Phone = txtPhone.getText().toString();
                        Email = txtEmail.getText().toString().toLowerCase();
                        Address = txtAddress.getText().toString();
                        Pass = txtPass.getText().toString();

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
