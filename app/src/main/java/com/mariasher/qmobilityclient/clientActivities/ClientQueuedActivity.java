package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter.QUEUE_ID;
import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;
import static com.mariasher.qmobilityclient.clientActivities.ViewQueueDetailsAndEnterActivity.CLIENT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.Utils.DateTimeUtils;
import com.mariasher.qmobilityclient.Utils.Enums.ClientStatus;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.Utils.NotificationUtils;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.database.Queue;
import com.mariasher.qmobilityclient.databinding.ActivityClientQueuedBinding;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;

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
            binding.assignedCounterClientViewTextView.setText(client.getAssignedCounter());

            if (client.getClientStatus().equals(ClientStatus.ONCALL.toString())) {
                String message = "Please go to counter: " + client.getAssignedCounter();
                NotificationUtils.showNotification(this, businessInfo.getBusinessName(), message);
            }


            if (client.getClientStatus().equals(ClientStatus.DEQUEUED.toString())) {
                goToClientBusinessViewActivity();
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
                        .orElse(null);
            }
            int nextNumber = (firstClient != null ? firstClient.getAssignedNumberInQueue() : 1);
            binding.numberOnCallClientTextView.setText("" + (nextNumber - 1));
        });
    }

    private void setExpectedTime() {
        //TODO
    }

    public void exitQueueButtonClicked(View view) {
        client.setClientStatus(ClientStatus.DEQUEUED.toString());
        client.setBusinessId("");
        client.setQueueId("");
        client.setAssignedNumberInQueue(0);
        client.setAssignedCounter("");
        client.setQueueExitTime(DateTimeUtils.convertDateAndTimeToString(LocalDateTime.now()));

        firebaseRealTimeUtils.updateClient(client, isClientUpdated -> {
            if (isClientUpdated) {
                Toast.makeText(this, "You are Dequeued!", Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, Object> clientsInQueue = queue.getClientsInQueue();
        clientsInQueue.remove(client.getClientId());
        queue.setClientsInQueue(clientsInQueue);

        firebaseRealTimeUtils.updateQueue(businessID, queue, isQueueUpdated -> {
            if (isQueueUpdated) {
                goToClientBusinessViewActivity();
            }
        });
    }

    private void goToClientBusinessViewActivity() {
        Intent intent = new Intent(this, ClientBusinessViewActivity.class);
        startActivity(intent);
        finish();
    }
}