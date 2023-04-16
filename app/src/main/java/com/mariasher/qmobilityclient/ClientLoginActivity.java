package com.mariasher.qmobilityclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.databinding.ActivityClientLoginBinding;

public class ClientLoginActivity extends AppCompatActivity {

    private ActivityClientLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
    }

    public void clientLoginForgotPasswordTextViewClicked(View view) {
    }

    public void clientLoginButtonClicked(View view) {
    }

    public void clientLoginTextViewClicked(View view) {
    }

    public void clientSignUpTextViewClicked(View view) {
    }
}