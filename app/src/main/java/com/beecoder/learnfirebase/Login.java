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

public class Login extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "Login";

    EditText emailLogin, passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passLogin);

        findViewById(R.id.goRegister).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, go to Home Activity
                            Log.d(TAG, "signInWithEmail:success");
                            Intent home = new Intent(Login.this, MainActivity.class);
                            startActivity(home);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Gagal Login.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = emailLogin.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailLogin.setError("Required.");
            valid = false;
        } else {
            emailLogin.setError(null);
        }

        String password = passwordLogin.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordLogin.setError("Required.");
            valid = false;
        } else {
            passwordLogin.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.btn_login){
            signIn(emailLogin.getText().toString(), passwordLogin.getText().toString());
        } else if (i == R.id.goRegister){
            Intent register = new Intent(Login.this, Register.class);
            startActivity(register);
        }
    }
}
