package com.example.loginfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        username.setText("Welcome, " + mAuth.getCurrentUser().getDisplayName());
    }

    public void callSignOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void callNewPost(View view) {
        startActivity(new Intent(getApplicationContext(), NewPost.class));
    }

    public void callViewPost(View view) {
        startActivity(new Intent(getApplicationContext(), ViewPost.class));
    }

    public void callLocationService(View view) {
        startActivity(new Intent(getApplicationContext(), ViewLocation.class));
    }
}
