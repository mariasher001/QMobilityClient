package com.mariasher.qmobilityclient.clientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.ClientLoginActivity;
import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.databinding.ActivityClientBusinessViewBinding;

public class ClientBusinessViewActivity extends AppCompatActivity {

    private ActivityClientBusinessViewBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBusinessViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_client_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void logoutClientMenuItemClicked(@NonNull MenuItem item) {
        mAuth.signOut();
        Intent intent = new Intent(this, ClientLoginActivity.class);
        startActivity(intent);
        finish();
    }
}