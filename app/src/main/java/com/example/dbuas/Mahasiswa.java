package com.example.dbuas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Mahasiswa extends AppCompatActivity {
    ListView todolist;
    EditText todotext,namamaha,jk,jurusan,alamat;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> listKeys = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        todolist = (ListView) findViewById(R.id.todolist);
        todotext = (EditText) findViewById(R.id.nim);
        alamat = (EditText)findViewById(R.id.alamat);
        namamaha = (EditText) findViewById(R.id.nama);
        jk = (EditText) findViewById(R.id.jk);
        jurusan = (EditText) findViewById(R.id.jurusan);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItems);
        todolist.setAdapter(adapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("data");

        todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = position;
                myRef.child(listKeys.get(position)).removeValue();

            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("namamahasiswa").getValue().toString();
                listItems.add(value);
                listKeys.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index !=-1){
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addtodo(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");


        String namamahasiswa = namamaha.getText().toString();
        String title = todotext.getText().toString();
        String jrsn = jurusan.getText().toString();
        String jek = jk.getText().toString();
        String almt = alamat.getText().toString();
        ref.push().setValue(new datamahasiswa(namamahasiswa,title,jrsn,jek,almt, false));
        namamaha.setText("");
        todotext.setText("");
        jurusan.setText("");
        alamat.setText("");
        jk.setText("");
        Toast.makeText(this,"Data sidah tersimpan", Toast.LENGTH_SHORT).show();
    }
}
