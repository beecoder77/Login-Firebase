package com.beecoder.learnfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.logout).setOnClickListener(this);
    }
    private void signOut() {
        mAuth.signOut();
        Intent login = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(login);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.logout){
            signOut();
        }
    }
}
