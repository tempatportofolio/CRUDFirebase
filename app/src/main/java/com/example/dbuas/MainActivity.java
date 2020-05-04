package com.example.dbuas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView user_name;
    private TextView user_email;
    CardView buku,mahasiswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buku = (CardView)findViewById(R.id.buku);
        buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next2 = new Intent(MainActivity.this, Buku.class);
                startActivity(next2);
            }
        });
        mahasiswa = (CardView)findViewById(R.id.mahasiswa);
        mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this, Mahasiswa.class);
                startActivity(next);
            }
        });
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            user_name.setText(name);
            user_email.setText(email);
        }
    }

    public void logoutProcess(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent loginpage = new Intent(MainActivity.this, login.class);
        startActivity(loginpage);
    }
}
