package com.example.ocs.Supervisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import AdapterClass.NoteAdapter;
import Interface.MCliclkableInterface;
import Models.Note;

public class HistoryFragment extends Fragment implements MCliclkableInterface {
    /* access modifiers changed from: private */
    public NoteAdapter adapter;

    /* renamed from: db */
    private FirebaseFirestore f528db;
    private CollectionReference notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public HistoryFragment() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f528db = instance;
        this.notebookRef = instance.collection("history");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(C2668R.layout.fragment_history, container, false);
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) rootView.findViewById(C2668R.C2671id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                HistoryFragment.this.initRecycler(rootView);
                HistoryFragment.this.adapter.notifyDataSetChanged();
                HistoryFragment.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler(rootView);
        return rootView;
    }

    /* access modifiers changed from: private */
    public void initRecycler(View view) {
        this.adapter = new NoteAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("compliant_id", Query.Direction.ASCENDING), Note.class).build(), this);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(C2668R.C2671id.recycler_view);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.startListening();
    }

    public void onClickable(int position, String cId, String ImageUrl) {
    }
}
