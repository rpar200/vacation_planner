package com.erde.erde_vacation_planner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.erde.erde_vacation_planner.R;

public class Registration extends AppCompatActivity {

    Button register;
    FirebaseAuth auth;

    EditText mEmail;
    EditText mPassword;
    EditText mVerifyPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mVerifyPassword = findViewById(R.id.verifypassword);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                String verifypass = mVerifyPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    mEmail.setError("Email is required.");
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    mEmail.setError("Please enter a valid email address.");
                    return;
                }

                if (pass.isEmpty()) {
                    mPassword.setError("Password is required.");
                    return;
                }

                if (pass.length() < 8) {
                    mPassword.setError("Password must be at least 8 characters.");
                }

                else if (!pass.equals(verifypass)) {);
                    mVerifyPassword.setError("Passwords do not match.");

                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete()) {
                                Toast.makeText(Registration.this, "Account created!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, Login.class));
                            } else {
                                Toast.makeText(Registration.this, "Account creation unsuccessful. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}