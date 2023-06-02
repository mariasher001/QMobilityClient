package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter.QUEUE_ID;
import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.Utils.DateTimeUtils;
import com.mariasher.qmobilityclient.Utils.Enums.ClientStatus;
import com.mariasher.qmobilityclient.Utils.Enums.QueueStatus;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.Utils.interfaces.Callback;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.database.Queue;
import com.mariasher.qmobilityclient.databinding.ActivityViewQueueDetailsAndEnterBinding;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;

public class ViewQueueDetailsAndEnterActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "clientId";
    private ActivityViewQueueDetailsAndEnterBinding binding;
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
        binding = ActivityViewQueueDetailsAndEnterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        businessID = intent.getStringExtra(BUSINESS_ID);
        queueId = intent.getStringExtra(QUEUE_ID);
        mAuth = FirebaseAuth.getInstance();
        clientId = mAuth.getCurrentUser().getUid();
        firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);

        firebaseRealTimeUtils.getBusinessInfo(businessID, businessInfo -> {
            this.businessInfo = businessInfo;
            binding.businessNameDetailsHeaderTextView.setText(businessInfo.getBusinessName());
        });

        firebaseRealTimeUtils.getSingleQueueData(queueId, businessID, queue -> {
            this.queue = queue;
            binding.queueNameDetailsHeaderTextView.setText(queue.getQueueName());
            binding.queueStatusQueueDetailsTextView.setText(queue.getQueueStatus());
            binding.numberOfClientsInQueueDetailsTextView.setText("" + queue.getClientsInQueue().size());
            calculateNextNumberOnCall();
            calculateYourExpectedNumber();
            calculateExpectedTimeOfTurn();
        });

        firebaseRealTimeUtils.getClientDetails(clientId, client -> {
            this.client = client;
        });
    }

    private void calculateNextNumberOnCall() {
        firebaseRealTimeUtils.getClientsInQueue(queue.getClientsInQueue(), clients -> {
            Client firstClient = null;
            if (!clients.isEmpty()) {
                firstClient = clients.stream()
                        .filter(client1 -> client1.getClientStatus().equals(ClientStatus.QUEUED.toString()))
                        .min(Comparator.comparingInt(Client::getAssignedNumberInQueue))
                        .orElse(null);
            }
            int nextNumber = (firstClient != null ? firstClient.getAssignedNumberInQueue() : 1);
            binding.nextNumberOnCallDetailsTextView.setText("" + nextNumber);
        });
    }

    private void calculateYourExpectedNumber() {
        firebaseRealTimeUtils.getClientsInQueue(queue.getClientsInQueue(), clients -> {
            Client lastClient = null;
            if (!clients.isEmpty()) {
                lastClient = clients.stream()
                        .max(Comparator.comparingInt(Client::getAssignedNumberInQueue))
                        .get();
            }
            binding.yourExpectedNumberDetailsTextView.setText("" + (lastClient != null ? lastClient.getAssignedNumberInQueue() + 1 : 1));
        });
    }

    private void calculateExpectedTimeOfTurn() {
        //TODO
    }

    public void enterQueueButtonClicked(View view) {

        if (!queue.getQueueStatus().equals(QueueStatus.ACTIVE.toString())) {
            Toast.makeText(this, "You can't enter the queue", Toast.LENGTH_SHORT).show();
            return;
        }
        if (client.getClientStatus().equals(ClientStatus.QUEUED.toString())) {
            Toast.makeText(this, "You can't enter the queue", Toast.LENGTH_SHORT).show();
            return;
        }

        setClientDetails(isClientDetailsSet -> {
            if (isClientDetailsSet) {
                firebaseRealTimeUtils.updateClient(client, isClientUpdated -> {
                    if (isClientUpdated) {
                        Toast.makeText(this, "You are queued!", Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String, Object> clientsInQueue = queue.getClientsInQueue();
                clientsInQueue.put(client.getClientId(), client.getClientStatus());
                queue.setClientsInQueue(clientsInQueue);
                firebaseRealTimeUtils.updateQueue(businessID, queue, isQueueUpdated -> {
                    if (isQueueUpdated) {
                        goToClientQueuedActivity();
                    }
                });
            }
        });
    }

    private void setClientDetails(Callback<Boolean> callback) {
        client.setBusinessId(businessID);
        client.setQueueId(queueId);
        client.setClientStatus(ClientStatus.QUEUED.toString());
        client.setQueueEntryTime(DateTimeUtils.convertDateAndTimeToString(LocalDateTime.now()));
        client.setQueueExitTime("");
        firebaseRealTimeUtils.getClientsInQueue(queue.getClientsInQueue(), clients -> {
            Client lastClient = null;
            if (!clients.isEmpty()) {
                lastClient = clients.stream()
                        .max(Comparator.comparingInt(Client::getAssignedNumberInQueue))
                        .get();
            }
            client.setAssignedNumberInQueue(lastClient != null ? lastClient.getAssignedNumberInQueue() + 1 : 1);
            callback.onSuccess(true);
        });
    }

    private void goToClientQueuedActivity() {
        Intent intent = new Intent(this, ClientQueuedActivity.class);
        intent.putExtra(BUSINESS_ID, businessID);
        intent.putExtra(QUEUE_ID, queueId);
        intent.putExtra(CLIENT_ID, clientId);
        startActivity(intent);
        finish();
    }

}