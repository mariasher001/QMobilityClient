package com.mariasher.qmobilityclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mariasher.qmobilityclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);


    }

    private void init(Bundle savedInstanceState) {
        Intent intent = new Intent(this, ClientLoginActivity.class);
        startActivity(intent);

        Runnable runnable = () -> {
            startActivity(intent);
            finish();
        };

        binding.clientAppInitialScreenTextView.postDelayed(runnable, 2000);
    }
}