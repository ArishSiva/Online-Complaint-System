package com.example.ocs.User;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.ocs.AdapterClass.Add_Info_Adapter;
import com.example.ocs.Models.Information_Model;
import com.example.ocs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class UShow_Information extends AppCompatActivity {
    Add_Info_Adapter add_info_adapter;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private Query notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public UShow_Information() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        firebaseFirestore = instance;
        this.notebookRef = instance.collection("Admin Info").whereGreaterThanOrEqualTo(ServerValues.NAME_OP_TIMESTAMP, (Object) Long.valueOf(System.currentTimeMillis()));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_show__information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                UShow_Information.this.initInfoRecycler();
                UShow_Information.this.add_info_adapter.notifyDataSetChanged();
                UShow_Information.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initInfoRecycler();
    }

    /* access modifiers changed from: private */
    public void initInfoRecycler() {
        this.add_info_adapter = new Add_Info_Adapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy(ServerValues.NAME_OP_TIMESTAMP, Query.Direction.DESCENDING), Information_Model.class).build());
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.info_recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.add_info_adapter);
        this.add_info_adapter.startListening();
    }

    public void onStart() {
        super.onStart();
        this.add_info_adapter.startListening();

    }

    public void onStop() {
        super.onStop();
        this.add_info_adapter.stopListening();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
