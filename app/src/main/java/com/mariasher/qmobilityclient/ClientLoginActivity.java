package com.mariasher.qmobilityclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mariasher.qmobilityclient.clientActivities.ClientBusinessViewActivity;
import com.mariasher.qmobilityclient.databinding.ActivityClientLoginBinding;

public class ClientLoginActivity extends AppCompatActivity {

    private ActivityClientLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mReal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mReal = FirebaseDatabase.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, ClientBusinessViewActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void clientSignUpTextViewClicked(View view) {
        Intent intent = new Intent(this, RegisterClientActivity.class);
        startActivity(intent);
        finish();
    }

    public void clientLoginButtonClicked(View view) {
        String clientEmail = binding.clientLoginEmailEditText.getText().toString();
        String clientPassword = binding.clientLoginPasswordEditText.getText().toString();
        if (checkLoginInformation(clientEmail, clientPassword)) {
            mAuth.signInWithEmailAndPassword(clientEmail, clientPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show();
                            // TODO:Change intent to Client Portal.

                        else
                            Toast.makeText(this, "Failed to Login. Try Again!!", Toast.LENGTH_LONG).show();
                    });
        }
    }

    private boolean checkLoginInformation(String clientEmail, String clientPassword) {
        if (clientEmail.isEmpty()) {
            return setError(this.binding.clientLoginEmailEditText, "Email is Required!");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches())
            setError(binding.clientLoginEmailEditText, "Enter correct email!");

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

    }


}