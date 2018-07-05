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

    private void inits(View view) {
        btnSignUp = view.findViewById(R.id.btn_SUSignUp);
        txtName = view.findViewById(R.id.txt_SUName);
        txtPhone = view.findViewById(R.id.txt_SUPhone);
        txtEmail = view.findViewById(R.id.txt_SUEmail);
        txtAddress = view.findViewById(R.id.txt_SUAddress);
        txtPass = view.findViewById(R.id.txt_SUPass);

        txtName.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtName) {
            @Override
            public boolean onDrawableClick() {
                txtName.setText("");
                return true;
            }
        });
        txtPhone.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtPhone) {
            @Override
            public boolean onDrawableClick() {
                txtPhone.setText("");
                return true;
            }
        });
        txtEmail.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtEmail) {
            @Override
            public boolean onDrawableClick() {
                txtEmail.setText("");
                return true;
            }
        });
        txtAddress.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtAddress) {
            @Override
            public boolean onDrawableClick() {
                txtAddress.setText("");
                return true;
            }
        });

        txtName.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.person_24dp, 0, 0, 0);
                } else {
                    txtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.person_24dp, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });
        txtPhone.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_24dp, 0, 0, 0);
                } else {
                    txtPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_24dp, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });
        txtEmail.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, 0, 0);
                } else {
                    txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });
        txtAddress.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location24dp, 0, 0, 0);
                } else {
                    txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location24dp, 0, R.drawable.cancel_24dp, 0);
                }
                super.onTextChanged(charSequence, i, i1, i2);
            }
        });

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

        txtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.person_24dp, 0, 0, 0);
        txtPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_24dp, 0, 0, 0);
        txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_24dp, 0, 0, 0);
        txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location24dp, 0, 0, 0);

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

    public interface FragmentCommuniCation {
        void PassingEmail(String Email);
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

}
