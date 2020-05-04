package com.example.dbuas;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context,ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    public View getView(int position,View convertView, ViewGroup parent){
        if (convertView ==null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_note,parent,false);
        }

        TextView note_id = (TextView) convertView.findViewById(R.id.note_id);
        TextView note_title = (TextView) convertView.findViewById(R.id.note_title);
        TextView note_description = (TextView) convertView.findViewById(R.id.note_description);
        TextView note_penerbit = (TextView) convertView.findViewById(R.id.note_penerbit);
        TextView note_stok = (TextView) convertView.findViewById(R.id.note_stok);

        Note note = getItem(position);
        note_id.setText(note.getDocId());
        note_title.setText("Judul : " +note.getTitle());
        note_description.setText("Pengarang : " +note.getDescription());
        note_penerbit.setText("Penerbit : " +note.getPenerbit());
        note_stok.setText("Stok : " + note.getStok());
        return convertView;
    }
}