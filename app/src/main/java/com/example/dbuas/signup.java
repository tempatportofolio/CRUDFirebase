package com.example.dbuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView linklogin;
    EditText name,user_email,user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        linklogin = (TextView) findViewById(R.id.link_login);
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(signup.this, login.class);
                startActivity(next);
            }
        });

        name = (EditText) findViewById(R.id.input_name);
        user_email = (EditText) findViewById(R.id.input_email);
        user_password = (EditText) findViewById(R.id.input_password);
        mAuth = FirebaseAuth.getInstance();

    }


    public void register(View view){

        String email  = user_email.getText().toString();
        String password = user_password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.getText().toString())
                                    .setPhotoUri(Uri.parse("https://randomuser.me/api/portraits/men/76.jpg"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(signup.this, "Data updated", Toast.LENGTH_SHORT).show();
                                                Intent homepage = new Intent(signup.this, MainActivity.class);
                                                startActivity(homepage);
                                                finish();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(signup.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
