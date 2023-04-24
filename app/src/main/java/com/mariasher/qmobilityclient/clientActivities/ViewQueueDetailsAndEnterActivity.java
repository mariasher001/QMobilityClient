package com.mariasher.qmobilityclient.clientActivities;

import static com.mariasher.qmobilityclient.Utils.Adapters.BusinessQueuesViewAdapter.QUEUE_ID;
import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.database.Queue;
import com.mariasher.qmobilityclient.databinding.ActivityViewQueueDetailsAndEnterBinding;

public class ViewQueueDetailsAndEnterActivity extends AppCompatActivity {

    private ActivityViewQueueDetailsAndEnterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseRealTimeUtils firebaseRealTimeUtils;
    private String businessID;
    private String queueId;

    private BusinessInfo businessInfo;
    private Queue queue;

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
    }

    private void calculateNextNumberOnCall() {
        //TODO
    }

    private void calculateYourExpectedNumber() {
        //TODO
    }

    private void calculateExpectedTimeOfTurn() {
        //TODO
    }

    public void enterQueueButtonClicked(View view) {
        //TODO
    }
}