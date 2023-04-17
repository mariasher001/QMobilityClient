package com.mariasher.qmobilityclient.Utils.Firebase;

import android.content.Context;
import android.telecom.Call;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.interfaces.Callback;

public class FirebaseRealTimeUtils {
    private FirebaseDatabase mReal;
    private Context context;

    public FirebaseRealTimeUtils(Context context) {
        mReal = FirebaseDatabase.getInstance();
        this.context = context;
    }

    public void registerClientInFireBaseDatabase(Client client, Callback<Boolean> callback) {
        //ToDO: Register a user in Firebase Realtime Database in Customers Node!
        mReal.getReference("QMobility")
                .child("Customers")
                .setValue(client)
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful())
                        callback.onSuccess(true);
                    else
                        callback.onSuccess(false);


                });
        //
    }
}
