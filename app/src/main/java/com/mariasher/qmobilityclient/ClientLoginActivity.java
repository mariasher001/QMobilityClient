package com.mariasher.qmobilityclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mariasher.qmobilityclient.Utils.FirebaseRealTimeUtils;
import com.mariasher.qmobilityclient.clientActivities.ClientBusinessViewActivity;
import com.mariasher.qmobilityclient.databinding.ActivityClientLoginBinding;

public class ClientLoginActivity extends AppCompatActivity {

    private ActivityClientLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseRealTimeUtils firebaseRealTimeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseRealTimeUtils = new FirebaseRealTimeUtils(this);
        if (mAuth.getCurrentUser() != null) {
            goToClientPortal();
        }
    }

    public void clientSignUpTextViewClicked(View view) {
        Intent intent = new Intent(this, RegisterClientActivity.class);
        startActivity(intent);
        finish();
    }

    public void clientLoginButtonClicked(View view) {
        binding.progressBar.setVisibility(View.VISIBLE);
        String clientEmail = binding.clientLoginEmailEditText.getText().toString();
        String clientPassword = binding.clientLoginPasswordEditText.getText().toString();
        if (checkLoginInformation(clientEmail, clientPassword)) {
            mAuth.signInWithEmailAndPassword(clientEmail, clientPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = task.getResult().getUser().getUid();
                            firebaseRealTimeUtils.getBusinessIdFromEmployeeBusinessLink(userId, businessId -> {
                                if (businessId != null) {
                                    logoutEmployee();
                                } else {
                                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show();
                                    goToClientPortal();
                                }
                                binding.progressBar.setVisibility(View.GONE);
                            });
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Failed to Login. Try Again!!", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void logoutEmployee() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Access Error!")
                .setMessage("You are not authorized to login with your business email!" +
                        " Please use another Email to log in!")
                .setPositiveButton("Ok", (dialog, i) -> {
                    mAuth.signOut();
                })
                .show();
    }

    private boolean checkLoginInformation(String clientEmail, String clientPassword) {
        if (clientEmail.isEmpty())
            return setError(this.binding.clientLoginEmailEditText, "Email is Required!");
        if (!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches())
            return setError(binding.clientLoginEmailEditText, "Enter correct email!");
        if (clientPassword.isEmpty())
            return setError(this.binding.clientLoginPasswordEditText, "Password is required!");

        return true;
    }

    private boolean setError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
        return false;
    }

    public void clientLoginForgotPasswordTextViewClicked(View view) {
        //TODO
    }

    private void goToClientPortal() {
        Intent intent = new Intent(this, ClientBusinessViewActivity.class);
        startActivity(intent);
        finish();
    }
}