package com.mariasher.qmobilityclient.Utils;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.Utils.interfaces.Callback;

public class FirebaseRealTimeUtils {
    private FirebaseDatabase mReal;
    private Context context;

    public FirebaseRealTimeUtils(Context context) {
        mReal = FirebaseDatabase.getInstance();
        this.context = context;
    }

    public void updateClientDetailsInFirebase(Client client, Callback<Boolean> callback) {
        mReal.getReference("QMobility")
                .child("Clients")
                .child(client.getClientId())
                .child("clientDetails")
                .setValue(client)
                .addOnCompleteListener(task -> callback.onSuccess(task.isSuccessful()));
    }
}
