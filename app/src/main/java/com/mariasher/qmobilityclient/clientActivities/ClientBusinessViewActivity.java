package com.mariasher.qmobilityclient.clientActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mariasher.qmobilityclient.databinding.ActivityClientBusinessViewBinding;

public class ClientBusinessViewActivity extends AppCompatActivity {

    private ActivityClientBusinessViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBusinessViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
    }
}