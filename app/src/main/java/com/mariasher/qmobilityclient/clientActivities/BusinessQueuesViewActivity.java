package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.ClientLoginActivity;
import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.databinding.ActivityBusinessQueuesViewBinding;

public class BusinessQueuesViewActivity extends AppCompatActivity {

    private ActivityBusinessQueuesViewBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseRealTimeUtils firebaseRealTimeUtils;
    private String businessID;
    private BusinessInfo businessInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusinessQueuesViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        businessID = intent.getStringExtra(BUSINESS_ID);

        mAuth = FirebaseAuth.getInstance();
        firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);

        firebaseRealTimeUtils.getBusinessInfo(businessID, businessInfo -> {
            this.businessInfo = businessInfo;
            binding.businessNameQueueViewHeaderTextView.setText(businessInfo.getBusinessName());
        });

        firebaseRealTimeUtils.getQueuesFromBusiness(businessID, businessQueues -> {
            binding.viewBusinessQueuesRecyclerView.setAdapter(
                    new BusinessQueuesViewAdapter(businessQueues, businessID, this, this)
            );
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ClientBusinessViewActivity.class);
        startActivity(intent);
        finish();
    }
}