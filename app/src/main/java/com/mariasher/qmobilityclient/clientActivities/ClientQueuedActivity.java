package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter.QUEUE_ID;
import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;
import static com.mariasher.qmobilityclient.clientActivities.ViewQueueDetailsAndEnterActivity.CLIENT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.Utils.Enums.ClientStatus;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.database.Queue;
import com.mariasher.qmobilityclient.databinding.ActivityClientQueuedBinding;

import java.util.Comparator;

public class ClientQueuedActivity extends AppCompatActivity {

    private ActivityClientQueuedBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseRealTimeUtils firebaseRealTimeUtils;
    private String businessID;
    private String queueId;
    private String clientId;
    private BusinessInfo businessInfo;
    private Queue queue;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientQueuedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        businessID = intent.getStringExtra(BUSINESS_ID);
        queueId = intent.getStringExtra(QUEUE_ID);
        clientId = intent.getStringExtra(CLIENT_ID);

        mAuth = FirebaseAuth.getInstance();
        firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);

        setBusiness();
        setQueueAndNumberOnCall();
        setClient();
        setExpectedTime();
    }

    private void setBusiness() {
        firebaseRealTimeUtils.getBusinessInfo(businessID, businessInfo -> {
            this.businessInfo = businessInfo;
            binding.businessNameClientViewHeaderTextView.setText(businessInfo.getBusinessName());
        });
    }

    private void setQueueAndNumberOnCall() {
        firebaseRealTimeUtils.getSingleQueueData(queueId, businessID, queue -> {
            this.queue = queue;
            binding.queueNameSmallTextView.setText(queue.getQueueName());
            setNumberOnCall();
        });
    }

    private void setClient() {
        firebaseRealTimeUtils.getClientDetails(clientId, client -> {
            this.client = client;
            binding.yourAssignedNumberClientViewTextView.setText("" + client.getAssignedNumberInQueue());

            if (client.getClientStatus().equals(ClientStatus.DEQUEUED.toString())) {
                finish();
            }
        });
    }

    private void setNumberOnCall() {
        firebaseRealTimeUtils.getClientsInQueue(queue.getClientsInQueue(), clients -> {
            Client firstClient = null;
            if (!clients.isEmpty()) {
                firstClient = clients.stream()
                        .filter(client1 -> client1.getClientStatus().equals(ClientStatus.QUEUED.toString()))
                        .min(Comparator.comparingInt(Client::getAssignedNumberInQueue))
                        .get();
            }
            int nextNumber = (firstClient != null ? firstClient.getAssignedNumberInQueue() : 1);
            binding.numberOnCallClientTextView.setText("" + (nextNumber - 1));
        });
    }

    private void setExpectedTime() {
        //TODO
    }

    public void exitQueueButtonClicked(View view) {
    }
}