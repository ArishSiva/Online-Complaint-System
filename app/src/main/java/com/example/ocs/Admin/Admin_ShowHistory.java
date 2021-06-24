package com.example.ocs.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
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

public class Admin_ShowHistory extends AppCompatActivity implements MCliclkableInterface {
    /* access modifiers changed from: private */
    public NoteAdapter adapter;

    /* renamed from: db */
    private FirebaseFirestore f525db;
    private CollectionReference notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public Admin_ShowHistory() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f525db = instance;
        this.notebookRef = instance.collection("history");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_admin__show_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) findViewById(C2668R.C2671id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Admin_ShowHistory.this.initRecycler();
                Admin_ShowHistory.this.adapter.notifyDataSetChanged();
                Admin_ShowHistory.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler();
    }

    /* access modifiers changed from: private */
    public void initRecycler() {
        this.adapter = new NoteAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("compliant_id", Query.Direction.ASCENDING), Note.class).build(), this);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(C2668R.C2671id.admin_history_recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.startListening();
    }

    public void onClickable(int position, String cId, String ImageUrl) {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
