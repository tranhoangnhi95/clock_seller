package com.example.immortal.clock_seller.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.activity.SignInActivity;
import com.example.immortal.clock_seller.R;
import com.example.immortal.clock_seller.utils.DrawableClickListener;
import com.example.immortal.clock_seller.utils.TextChangeListener;

import java.lang.reflect.Type;

public class SignInFragment extends Fragment {
    public Button btnSignIn;
    public EditText txtEmail, txtPass;

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

    public static SignInFragment getInstance() {
        return new SignInFragment();
    }

    /**
     * Ánh xạ các view và tạo sự kiện
     * @param view điều khiển
     */
    @SuppressLint("ClickableViewAccessibility")
    private void inits(View view) {
        btnSignIn = view.findViewById(R.id.btn_SISignIn);
        txtEmail = view.findViewById(R.id.txt_SIEmail);
        txtPass = view.findViewById(R.id.txt_SIPass);
        //sự kiện RightDrawableClick xóa nội dung edittext
        txtEmail.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtEmail) {
            @Override
            public boolean onDrawableClick() {
                txtEmail.setText("");
                return true;
            }
        });
        //Sự kiện khi chuỗi của editText thay đổi
        txtEmail.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nếu chuỗi rỗng sẽ ẩn drawable
                if (charSequence.length() == 0) {
                    txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, 0, 0);
                } else {
                    //ngược lại hiển thị drawable
                    txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });

        //sự kiện RightDrawableClick hiển thị hoặc ẩn nội dung mật khẩu
        txtPass.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtPass) {
            @Override
            public boolean onDrawableClick() {
                if (txtPass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    txtPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24dp, 0, R.drawable.visibility_off_24dp, 0);
                }else {
                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24dp, 0, R.drawable.visibility_24dp, 0);
                }
                return true;
            }
        });
        txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, 0, 0);
    }

    //sự kiên click button đăng nhập
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

    /**
     * Hiển thị email ra edittext
     * @param Email chuỗi email
     */
    public void DisplayEmail(String Email) {
        txtEmail.setText(Email);
        txtPass.setText("");
    }
}
