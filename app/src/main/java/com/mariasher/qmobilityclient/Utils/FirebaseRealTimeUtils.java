package com.mariasher.qmobilityclient.Utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.Utils.interfaces.Callback;
import com.mariasher.qmobilityclient.database.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRealTimeUtils {
    private FirebaseDatabase mReal;
    private Context context;

    public FirebaseRealTimeUtils(Context context) {
        mReal = FirebaseDatabase.getInstance();
        this.context = context;
    }

    public void getBusinessIdFromEmployeeBusinessLink(String employeeId, Callback<String> callback) {
        mReal.getReference("QMobility")
                .child("EmployeeBusinessLink")
                .child(employeeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String businessId = snapshot.getValue(String.class);
                        callback.onSuccess(businessId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void updateClientDetailsInFirebase(Client client, Callback<Boolean> callback) {
        mReal.getReference("QMobility")
                .child("Clients")
                .child(client.getClientId())
                .setValue(client)
                .addOnCompleteListener(task -> callback.onSuccess(task.isSuccessful()));
    }

    public void getAllBusinesses(Callback<List<BusinessInfo>> callback) {
        mReal.getReference("QMobility")
                .child("Businesses")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<BusinessInfo> businessInfoList = new ArrayList<>();
                        for (DataSnapshot businessSnapshot : snapshot.getChildren()) {
                            BusinessInfo businessInfo = getBusinessDetails(businessSnapshot);
                            businessInfoList.add(businessInfo);
                        }
                        callback.onSuccess(businessInfoList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getBusinessInfo(String businessID, Callback<BusinessInfo> callback) {
        mReal.getReference("QMobility")
                .child("Businesses")
                .child(businessID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BusinessInfo businessInfo = getBusinessDetails(snapshot);
                        callback.onSuccess(businessInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private BusinessInfo getBusinessDetails(DataSnapshot businessSnapshot) {
        String businessID = businessSnapshot.child("BusinessDetails").child("businessID").getValue(String.class);
        String businessName = businessSnapshot.child("BusinessDetails").child("businessName").getValue(String.class);
        String businessPhoneNumber = businessSnapshot.child("BusinessDetails").child("businessPhoneNumber").getValue(String.class);
        String businessAddress = businessSnapshot.child("BusinessDetails").child("businessAddress").getValue(String.class);
        String businessLatitude = businessSnapshot.child("BusinessDetails").child("businessLatitude").getValue(String.class);
        String businessLongitude = businessSnapshot.child("BusinessDetails").child("businessLongitude").getValue(String.class);
        String businessType = businessSnapshot.child("BusinessDetails").child("businessType").getValue(String.class);

        return new BusinessInfo(businessID, businessName, businessPhoneNumber, businessAddress, businessLatitude, businessLongitude, businessType);
    }

    public void getQueuesFromBusiness(String businessID, Callback<List<Queue>> callback) {
        mReal.getReference("QMobility")
                .child("Businesses")
                .child(businessID)
                .child("Queues")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Queue> queues = new ArrayList<>();
                        for (DataSnapshot queueSnapshot : snapshot.getChildren()) {
                            Queue queue = getQueueFields(queueSnapshot);
                            queues.add(queue);
                        }
                        callback.onSuccess(queues);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getSingleQueueData(String queueId, String businessID, Callback<Queue> callback) {
        mReal.getReference("QMobility")
                .child("Businesses")
                .child(businessID)
                .child("Queues")
                .child(queueId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Queue queue = getQueueFields(snapshot);
                        callback.onSuccess(queue);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private Queue getQueueFields(DataSnapshot snapshot) {
        String queueId = snapshot.child("queueId").getValue(String.class);
        String creatorId = snapshot.child("creatorId").getValue(String.class);
        String queueName = snapshot.child("queueName").getValue(String.class);
        String queueStartTime = snapshot.child("queueStartTime").getValue(String.class);
        String queueEndTime = snapshot.child("queueEndTime").getValue(String.class);
        String queueStatus = snapshot.child("queueStatus").getValue(String.class);
        Integer numberOfActiveCounters = snapshot.child("numberOfActiveCounters").getValue(Integer.class);
        String averageCustomerTime = snapshot.child("averageCustomerTime").getValue(String.class);
        Double totalClientWaitingTime = snapshot.child("totalClientWaitingTime").getValue(Double.class);
        Integer totalClients = snapshot.child("totalClients").getValue(Integer.class);

        Map<String, Object> queueCounters = new HashMap<>();
        for (DataSnapshot queueCounterSnapshot : snapshot.child("queueCounters").getChildren()) {
            String queueCounterId = queueCounterSnapshot.getKey();
            String counterNumber = queueCounterSnapshot.getValue(String.class);
            queueCounters.put(queueCounterId, counterNumber);
        }

        Map<String, Object> clientsInQueue = new HashMap<>();
        for (DataSnapshot queueCounterSnapshot : snapshot.child("clientsInQueue").getChildren()) {
            String clientId = queueCounterSnapshot.getKey();
            String clientStatus = queueCounterSnapshot.getValue(String.class);
            clientsInQueue.put(clientId, clientStatus);
        }
        Queue queue = new Queue(queueId, creatorId, queueName, queueStartTime, queueEndTime, queueStatus,
                numberOfActiveCounters, averageCustomerTime, queueCounters, clientsInQueue, totalClientWaitingTime, totalClients);

        return queue;
    }

    public void getClientsInQueue(Map<String, Object> clientsInQueue, Callback<List<Client>> callback) {
        List<Client> clients = new ArrayList<>();

        if (clientsInQueue.isEmpty()) {
            callback.onSuccess(clients);
            return;
        }

        mReal.getReference("QMobility")
                .child("Clients")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                            if (clientsInQueue.containsKey(clientSnapshot.getKey())) {
                                Client client = getClientFields(clientSnapshot);
                                clients.add(client);
                            }
                        }
                        callback.onSuccess(clients);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getClientDetails(String clientId, Callback<Client> callback) {
        mReal.getReference("QMobility")
                .child("Clients")
                .child(clientId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Client client = getClientFields(snapshot);
                        callback.onSuccess(client);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private Client getClientFields(DataSnapshot snapshot) {
        String clientId = snapshot.child("clientId").getValue(String.class);
        String clientName = snapshot.child("clientName").getValue(String.class);
        String clientEmail = snapshot.child("clientEmail").getValue(String.class);
        String clientPhoneNumber = snapshot.child("clientPhoneNumber").getValue(String.class);
        String clientStatus = snapshot.child("clientStatus").getValue(String.class);
        String businessId = snapshot.child("businessId").getValue(String.class);
        String queueId = snapshot.child("queueId").getValue(String.class);
        int assignedNumberInQueue = snapshot.child("assignedNumberInQueue").getValue(Integer.class);
        String assignedCounter = snapshot.child("assignedCounter").getValue(String.class);
        String queueEntryTime = snapshot.child("queueEntryTime").getValue(String.class);
        String queueExitTime = snapshot.child("queueExitTime").getValue(String.class);

        return new Client(clientId, clientName, clientEmail, clientPhoneNumber, clientStatus,
                businessId, queueId, assignedNumberInQueue, assignedCounter, queueEntryTime, queueExitTime);
    }

    public void updateClient(Client client, Callback<Boolean> callback) {
        mReal.getReference("QMobility")
                .child("Clients")
                .child(client.getClientId())
                .setValue(client)
                .addOnCompleteListener(task -> {
                    callback.onSuccess(task.isSuccessful());
                });
    }

    public void updateQueue(String businessID, Queue queue, Callback<Boolean> callback) {
        mReal.getReference("QMobility")
                .child("Businesses")
                .child(businessID)
                .child("Queues")
                .child(queue.getQueueId())
                .setValue(queue)
                .addOnCompleteListener(task -> {
                    callback.onSuccess(task.isSuccessful());
                });
    }
}
