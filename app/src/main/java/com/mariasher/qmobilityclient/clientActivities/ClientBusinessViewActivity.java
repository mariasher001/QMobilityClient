package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter.QUEUE_ID;
import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;
import static com.mariasher.qmobilityclient.clientActivities.ViewQueueDetailsAndEnterActivity.CLIENT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.ClientLoginActivity;
import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter;
import com.mariasher.qmobilityclient.Utils.Enums.ClientStatus;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.databinding.ActivityClientBusinessViewBinding;

public class ClientBusinessViewActivity extends AppCompatActivity {

    private ActivityClientBusinessViewBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseRealTimeUtils firebaseRealTimeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBusinessViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);

        firebaseRealTimeUtils.getClientDetails(mAuth.getCurrentUser().getUid(), client -> {
            if (!client.getClientStatus().equals(ClientStatus.DEQUEUED.toString())) {
                goToClientQueued(client);
            }
        });

        firebaseRealTimeUtils.getAllBusinesses(businesses -> {
            //TODO sort businesses by location
            binding.viewBusinessesRecyclerView.setAdapter(new ClientBusinessViewAdapter(businesses, this, this));
        });
    }

    private void goToClientQueued(Client client) {
        Intent intent = new Intent(this, ClientQueuedActivity.class);
        intent.putExtra(BUSINESS_ID, client.getBusinessId());
        intent.putExtra(QUEUE_ID, client.getQueueId());
        intent.putExtra(CLIENT_ID, client.getClientId());
        startActivity(intent);
        finish();
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