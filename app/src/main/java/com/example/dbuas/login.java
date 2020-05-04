package com.example.dbuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    TextView linksignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        linksignup = (TextView) findViewById(R.id.link_signup);
        linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(login.this, com.example.dbuas.signup.class);
                startActivity(sign);
            }
        });
    }

    public void loginProses(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent homepage = new Intent(login.this, MainActivity.class);
                    startActivity(homepage);
                }else{
                    Toast.makeText(login.this, "Authentication failed.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
