package com.example.ocs.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;

import Models.Note;

public class Admin_CompliantAdapter extends FirestoreRecyclerAdapter<Note, NoteViewHolder> {
    public Admin_CompliantAdapter(FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(NoteViewHolder holder, int position, Note model) {
        holder.f520id.setText(model.getCompliant_id());
        holder.name.setText(model.getName());
        holder.location.setText(model.getLocation());
        holder.compliant.setText(model.getCompliantType());
        holder.street.setText(model.getStreet());
        holder.priority.setText(model.getCompliantLevel());
        holder.status.setText(model.getStatus());
        holder.bind(model);
    }

    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(C2668R.layout.complant_note_item, parent, false));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView compliant;

        /* renamed from: id */
        TextView f520id;
        ImageView imageView;
        private AdapterView.OnItemClickListener listener;
        TextView location;
        TextView name;
        Note noteModel;
        TextView priority;
        TextView status;
        TextView street;

        public void bind(Note note) {
            this.noteModel = note;
        }

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.f520id = (TextView) itemView.findViewById(C2668R.C2671id.note_compliantId);
            this.name = (TextView) itemView.findViewById(C2668R.C2671id.note_name);
            this.location = (TextView) itemView.findViewById(C2668R.C2671id.note_Location);
            this.street = (TextView) itemView.findViewById(C2668R.C2671id.note_street);
            this.priority = (TextView) itemView.findViewById(C2668R.C2671id.note_priority);
            this.status = (TextView) itemView.findViewById(C2668R.C2671id.note_status);
            this.compliant = (TextView) itemView.findViewById(C2668R.C2671id.note_compliant);
        }
    }
}
