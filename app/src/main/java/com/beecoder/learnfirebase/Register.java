package com.beecoder.learnfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText emailRegister, passwordRegister;

    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailRegister = (EditText) findViewById(R.id.emailRegister);
        passwordRegister = (EditText) findViewById(R.id.passRegister);

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.goLogin).setOnClickListener(this);

    }
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Intent login = new Intent(Register.this, VerifEmail.class);
                            startActivity(login);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    };

    private boolean validateForm() {
        boolean valid = true;

        String email = emailRegister.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailRegister.setError("Required.");
            valid = false;
        } else {
            emailRegister.setError(null);
        }

        String password = passwordRegister.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordRegister.setError("Required.");
            valid = false;
        } else {
            passwordRegister.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        int i = (view.getId());
        if (i == R.id.btn_signup){
            createAccount(emailRegister.getText().toString(), passwordRegister.getText().toString());
        }else if (i == R.id.goLogin){
            Intent login = new Intent(Register.this, Login.class);
            startActivity(login);
        }
    }
}
