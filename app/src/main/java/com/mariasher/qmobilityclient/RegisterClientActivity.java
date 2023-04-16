package com.mariasher.qmobilityclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mariasher.qmobilityclient.databinding.ActivityClientLoginBinding;

public class RegisterClientActivity extends AppCompatActivity {
ActivityClientLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityClientLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {

    }

    public void registerButtonClicked(View view) {
    }

    public void registerClientLoginTextViewClicked(View view) {
    }

    public void registerClientSignUpTextViewClicked(View view) {
    }
}