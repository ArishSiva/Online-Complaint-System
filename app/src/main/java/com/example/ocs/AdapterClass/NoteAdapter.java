package com.example.ocs.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.complientsystem.R;
import com.firebase.p013ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;

import Interface.MCliclkableInterface;
import Models.Note;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    MCliclkableInterface mCliclkableInterface;

    public NoteAdapter(FirestoreRecyclerOptions<Note> options, MCliclkableInterface mClick) {
        super(options);
        this.mCliclkableInterface = mClick;
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(NoteHolder holder, int position, Note model) {
        holder.f521id.setText(model.getCompliant_id());
        holder.name.setText(model.getName());
        holder.location.setText(model.getLocation());
        holder.compliant.setText(model.getCompliantType());
        holder.street.setText(model.getStreet());
        holder.priority.setText(model.getCompliantLevel());
        holder.bind(model);
    }

    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_compliants_itme, parent, false));
    }

    public void notifyDataSetChanged() {
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView compliant;

        /* renamed from: id */
        TextView f521id;
        TextView location;
        TextView name;
        Note noteModel;
        TextView priority;
        TextView street;

        public void bind(Note note) {
            this.noteModel = note;
        }

        public NoteHolder(View itemView) {
            super(itemView);
            this.f521id = (TextView) itemView.findViewById(R.id.show_compliantId);
            this.name = (TextView) itemView.findViewById(R.id.show_name);
            this.location = (TextView) itemView.findViewById(R.id.show_Location);
            this.street = (TextView) itemView.findViewById(R.id.show_street);
            this.priority = (TextView) itemView.findViewById(R.id.show_priority);
            this.compliant = (TextView) itemView.findViewById(R.id.show_compliant);
            itemView.setOnClickListener(new View.OnClickListener(NoteAdapter.this) {
                public void onClick(View v) {
                    NoteAdapter.this.mCliclkableInterface.onClickable(NoteHolder.this.getAdapterPosition(), NoteHolder.this.noteModel.getCompliant_id(), NoteHolder.this.noteModel.getImageDownUrl());
                }
            });
        }
    }
}
