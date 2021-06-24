package com.example.ocs.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;

import Interface.FeedbackClickable;
import Models.FeedbackModel;

public class FeedbackAdapter extends FirestoreRecyclerAdapter<FeedbackModel, NoteHolder> {
    FeedbackClickable feedbackClickable;

    public FeedbackAdapter(FirestoreRecyclerOptions<FeedbackModel> options, FeedbackClickable clickable) {
        super(options);
        this.feedbackClickable = clickable;
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(NoteHolder holder, int position, FeedbackModel model) {
        holder.feedback.setText(model.getFeedback());
        holder.bind(model);
    }

    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(C2668R.layout.feedback_item_adapter, parent, false));
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView feedback;
        FeedbackModel feedbackModel;

        public void bind(FeedbackModel model) {
            this.feedbackModel = model;
        }

        public NoteHolder(View itemView) {
            super(itemView);
            this.feedback = (TextView) itemView.findViewById(C2668R.C2671id.feedback_message);
            itemView.setOnClickListener(new View.OnClickListener(FeedbackAdapter.this) {
                public void onClick(View v) {
                    FeedbackAdapter.this.feedbackClickable.onClickableFeedback(NoteHolder.this.getLayoutPosition(), NoteHolder.this.feedbackModel.getName(), NoteHolder.this.feedbackModel.getUser(), NoteHolder.this.feedbackModel.getCompliant_id());
                }
            });
        }
    }
}
