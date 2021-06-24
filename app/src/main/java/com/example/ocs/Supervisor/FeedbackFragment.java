package com.example.ocs.Supervisor;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import AdapterClass.FeedbackAdapter;
import Interface.FeedbackClickable;
import Models.FeedbackModel;

public class FeedbackFragment extends Fragment implements FeedbackClickable {
    private static final String TAG = "FeedbackFragment";

    /* renamed from: db */
    private FirebaseFirestore f527db;
    /* access modifiers changed from: private */
    public FeedbackAdapter feedbackAdapter;
    FirebaseAuth firebaseAuth;
    private CollectionReference notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public FeedbackFragment() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f527db = instance;
        this.notebookRef = instance.collection("Feedback");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(C2668R.layout.fragment_feedback, container, false);
        this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(C2668R.C2671id.swipeRefreshLayout);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                FeedbackFragment.this.initRecycler(rootView);
                FeedbackFragment.this.feedbackAdapter.notifyDataSetChanged();
                FeedbackFragment.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler(rootView);
        return rootView;
    }

    /* access modifiers changed from: private */
    public void initRecycler(View view) {
        this.feedbackAdapter = new FeedbackAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("feedback", Query.Direction.DESCENDING), FeedbackModel.class).build(), this);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(C2668R.C2671id.feed_recycler_view);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setAdapter(this.feedbackAdapter);
        this.feedbackAdapter.startListening();
    }

    public void onStart() {
        super.onStart();
        this.feedbackAdapter.startListening();
    }

    public void onStop() {
        super.onStop();
        this.feedbackAdapter.stopListening();
    }

    public void onClickableFeedback(int position, String name, String complaintId, String userId) {
        Log.d(TAG, "Activated" + name + "\n" + complaintId + "\n" + userId + "\n" + position);
        new AlertDialog.Builder(getContext()).setMessage("UserName:\t\t" + name + "\nUserId:\t\t\t\t\t" + complaintId + "\n\nCompliantId:\t" + userId).create().show();
    }
}
