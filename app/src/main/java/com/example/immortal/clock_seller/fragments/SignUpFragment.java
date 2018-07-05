package com.example.immortal.clock_seller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immortal.clock_seller.activity.SignInActivity;
import com.example.immortal.clock_seller.R;
import com.example.immortal.clock_seller.model.User;
import com.example.immortal.clock_seller.utils.DrawableClickListener;
import com.example.immortal.clock_seller.utils.TextChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class SignUpFragment extends Fragment {
    public EditText txtName, txtPhone, txtEmail, txtAddress, txtPass;
    public Button btnSignUp;
    public FragmentCommuniCation fragmentCommuniCation;
    public DatabaseReference mDatabase;
    public FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signup_fragment, container, false);
        inits(view);
        signUpEvents();
        return view;
    }

    /**
     * Ánh xạ các view và tạo sự kiện
     *
     * @param view điều khiển
     */
    private void inits(View view) {
        btnSignUp = view.findViewById(R.id.btn_SUSignUp);
        txtName = view.findViewById(R.id.txt_SUName);
        txtPhone = view.findViewById(R.id.txt_SUPhone);
        txtEmail = view.findViewById(R.id.txt_SUEmail);
        txtAddress = view.findViewById(R.id.txt_SUAddress);
        txtPass = view.findViewById(R.id.txt_SUPass);

        //thêm các sự kiện rightDrawableClick
        rightDrawableClick(txtName, R.drawable.person_24dp);
        rightDrawableClick(txtPhone, R.drawable.phone_24dp);
        rightDrawableClick(txtEmail, R.drawable.email_24dp);
        rightDrawableClick(txtAddress, R.drawable.location24dp);

        txtPass.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtPass) {
            @Override
            public boolean onDrawableClick() {
                if (txtPass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    txtPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24dp, 0, R.drawable.visibility_off_24dp, 0);
                } else {
                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24dp, 0, R.drawable.visibility_24dp, 0);
                }
                return true;
            }
        });


    }

    //Sự kiện
    private void signUpEvents() {
        //sự kiên click button đăng nhập
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
                    activity.signUpFragment(Name, Phone, Email, Address, Pass);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Vui lòng nhập dầy đủ thông tin trước khi đăng ký", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //Interface hỗ trợ giao tiếp fragment
    public interface FragmentCommuniCation {
        void PassingEmail(String Email); //Hàm hỗ trợ truyền email
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentCommuniCation = (FragmentCommuniCation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    /**
     * Thêm sự kiện rightDrawableClick xóa nội dung editText
     *
     * @param editText    edit text cần thêm sự kiện
     * @param drawaleLeft drawable bên trái
     */
    private void rightDrawableClick(final EditText editText, final int drawaleLeft) {
        //sự kiện RightDrawableClick xóa nội dung edittext
        editText.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(editText) {
            @Override
            public boolean onDrawableClick() {
                editText.setText("");
                return true;
            }
        });
        //Sự kiện khi chuỗi của editText thay đổi
        editText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nếu chuỗi rỗng sẽ ẩn drawable
                if (charSequence.length() == 0) {
                    editText.setCompoundDrawablesWithIntrinsicBounds(drawaleLeft, 0, 0, 0);
                } else {
                    //ngược lại hiển thị drawable
                    editText.setCompoundDrawablesWithIntrinsicBounds(drawaleLeft, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });
        editText.setCompoundDrawablesWithIntrinsicBounds(drawaleLeft, 0, 0, 0);

    }
}
