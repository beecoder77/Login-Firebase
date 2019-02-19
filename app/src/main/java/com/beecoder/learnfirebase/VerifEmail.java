package com.beecoder.learnfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifEmail extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "VerifEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_email);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.VerifikasiEmail).setOnClickListener(this);
    }


    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.VerifikasiEmail).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(VerifEmail.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(VerifEmail.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.VerifikasiEmail){
            sendEmailVerification();
        } else if (i == R.id.login){
            Intent login = new Intent(VerifEmail.this, Login.class);
            finish();
            startActivity(login);
        }
    }
}
