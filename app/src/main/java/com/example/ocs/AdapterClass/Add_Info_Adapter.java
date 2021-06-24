package com.example.ocs.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocs.Models.Information_Model;
import com.example.ocs.R;

public class Add_Info_Adapter extends FirestoreRecyclerAdapter<Information_Model, Add_Info_Adapter.NoteViewHolder> {
    public Add_Info_Adapter(FirestoreRecyclerOptions<Information_Model> options) {
        super(options);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(NoteViewHolder holder, int position, Information_Model model) {
        holder.date.setText(model.getProDate());
        holder.title.setText(model.getProTitle());
        holder.time.setText(model.getProTime());
    }

    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_info_adapter, parent, false));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView title;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.info_title);
            this.date = (TextView) itemView.findViewById(R.id.info_date);
            this.time = (TextView) itemView.findViewById(R.id.info_time);
        }
    }
}
