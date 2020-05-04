package com.example.dbuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Buku extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference note_ref = db.collection("notes");
    EditText edit_title;
    EditText edit_description;
    EditText edit_penerbit;
    EditText edit_stok;
    ListView list_note;
    ArrayList<Note> note_item = new ArrayList<>();
    NoteAdapter adapter;
    private String selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buku);
        edit_title = findViewById(R.id.edit_title);
        edit_description = findViewById(R.id.edit_description);
        edit_penerbit = findViewById(R.id.edit_penerbit);
        edit_stok = findViewById(R.id.edit_stok);
        list_note = findViewById(R.id.list_note);

        list_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = adapter.getItem(position);
                edit_title.setText(note.getTitle());
                edit_description.setText(note.getDescription());
                edit_penerbit.setText(note.getPenerbit());
                edit_stok.setText(note.getStok());
                selectedId = note.getDocId();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        note_ref.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    return;
                }
                note_item.clear();
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocId(documentSnapshot.getId());
                    note_item.add(note);
                }

                adapter = new NoteAdapter(Buku.this,note_item);
                adapter.notifyDataSetChanged();
                list_note.setAdapter(adapter);
            }
        });
    }

    public void addNote(View view){
        String title = edit_title.getText().toString();
        String description = edit_description.getText().toString();
        String penerbit = edit_penerbit.getText().toString();
        String stok = edit_stok.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("title", title);
        user.put("description", description);
        user.put("penerbit", penerbit);
        user.put("stok", stok);

        note_ref.add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        edit_title.setText(null);
                        edit_description.setText(null);
                        edit_penerbit.setText(null);
                        edit_stok.setText(null);
                        Toast.makeText(Buku.this, "Success", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Buku.this, "Error"+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void editNote(View view){
        String title = edit_title.getText().toString();
        String description = edit_description.getText().toString();
        String penerbit = edit_penerbit.getText().toString();
        String stok = edit_stok.getText().toString();
        Note note = new Note(title,description,penerbit,stok);
        note_ref.document(selectedId).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edit_title.setText(null);
                        edit_description.setText(null);
                        edit_penerbit.setText(null);
                        edit_stok.setText(null);
                        Toast.makeText(Buku.this, "Updated ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Buku.this, "Error "+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void deleteNote(View view){

        note_ref.document(selectedId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edit_title.setText(null);
                        edit_description.setText(null);
                        edit_penerbit.setText(null);
                        edit_stok.setText(null);
                        Toast.makeText(Buku.this, "Deleted ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Buku.this, "Error "+e, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
