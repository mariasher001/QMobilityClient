package com.mariasher.qmobilityclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        Runnable runnable = () -> {
            startActivity(intent);
            finish();
        };

        binding.initialConstraintLayout.postDelayed(runnable, 2000);
    }
}