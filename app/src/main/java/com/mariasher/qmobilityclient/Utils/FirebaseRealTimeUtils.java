package com.mariasher.qmobilityclient.Utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.Utils.interfaces.Callback;

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
}
