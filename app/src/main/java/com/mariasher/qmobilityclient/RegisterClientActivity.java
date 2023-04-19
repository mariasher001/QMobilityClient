package com.mariasher.qmobilityclient;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mariasher.qmobilityclient.Utils.Firebase.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.database.Client;
import com.mariasher.qmobilityclient.databinding.ActivityRegisterClientBinding;

public class RegisterClientActivity extends AppCompatActivity {
    private ActivityRegisterClientBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mReal;

    FirebaseRealTimeUtils firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mReal = FirebaseDatabase.getInstance();

    }

    public void registerButtonClicked(View view) {
        setClientDetails();
    }

    private void setClientDetails() {
        String clientUserName = binding.registerClientUserNameEditText.getText().toString();
        String clientEmail = binding.registerClientEmailEditText.getText().toString();
        String clientPassword = binding.registerClientPasswordEditText.getText().toString();
        String clientPhoneNumber = binding.registerClientPhoneNumberEditText.getText().toString();
        if (checkClientRegistrationInformation(clientUserName, clientEmail, clientPassword, clientPhoneNumber)) {
            Log.d(TAG, "setClientDetails: Hi i am here");
            Client client = new Client("", clientUserName, clientEmail, clientPhoneNumber);
            createClientInFireBaseAuth(client, clientPassword);
        }



    }

    private void createClientInFireBaseAuth(Client client, String clientPassword) {
        mAuth.createUserWithEmailAndPassword(client.getClientEmail(), clientPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String clientId = mAuth.getCurrentUser().getUid();
                        client.setClientId(clientId);
                        firebaseRealTimeUtils.registerClientInFireBaseDatabase(client, result -> {
                            if (result) {
                                Toast.makeText(this, "User Registered Successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(this, ClientLoginActivity.class);
                                startActivity(intent);
                                finish();
                                mAuth.signOut();
                            }
                        });

                    } else
                        Toast.makeText(this, "Failed To register User! Try Again!", Toast.LENGTH_LONG).show();
                });
    }

    private boolean checkClientRegistrationInformation(String clientUserName, String clientEmail, String clientPassword, String clientPhoneNumber) {
        if (clientUserName.isEmpty())
            return setError(binding.registerClientUserNameEditText, "UserName cannot be empty");
        if (clientEmail.isEmpty())
            return setError(binding.registerClientEmailEditText, "Email Is Required!");
        if (!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches())
            return setError(binding.registerClientEmailEditText, "Enter correct email!");
        if (clientPassword.isEmpty())
            return setError(binding.registerClientPasswordEditText, "Password Is Required!");
        if (clientPassword.length() < 6)
            return setError(binding.registerClientPasswordEditText, "Password cannot be less than 6 characters!");
        if (clientPhoneNumber.isEmpty())
            return setError(binding.registerClientPhoneNumberEditText, "PhoneNumber is Required!");
        return true;
    }

    private boolean setError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
        return false;
    }

    public void registerClientLoginTextViewClicked(View view) {
        Intent intent = new Intent(this, ClientLoginActivity.class);
        startActivity(intent);
        finish();

    }
}