package com.example.ocs.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import AdapterClass.Admin_CompliantAdapter;
import Models.Note;

public class Admin_ShowComplaint extends AppCompatActivity {
    private static final String TAG = "Admin_ShowComplaint";
    /* access modifiers changed from: private */
    public Admin_CompliantAdapter adapter;

    /* renamed from: db */
    private FirebaseFirestore f523db;
    private Query notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public Admin_ShowComplaint() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f523db = instance;
        this.notebookRef = instance.collection("Compliant");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_admin__show_complaint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) findViewById(C2668R.C2671id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Admin_ShowComplaint.this.initRecycler();
                Admin_ShowComplaint.this.adapter.notifyDataSetChanged();
                Admin_ShowComplaint.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C2668R.C2673menu.search_view, menu);
        return true;
    }

    /* access modifiers changed from: private */
    public void initRecycler() {
        this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
        RecyclerView recyclerView2 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.startListening();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case C2668R.C2671id.Kalavaippatty /*2131230726*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("location", (Object) "Kalavaipatty").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView2 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView2;
                recyclerView2.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Kalavaipatty");
                return true;
            case C2668R.C2671id.Kunnakulam /*2131230727*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("location", (Object) "Kunnakulam").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView3 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView3;
                recyclerView3.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Kunnakulam");
                return true;
            case C2668R.C2671id.Salaippatty /*2131230735*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("location", (Object) "Salaippatti").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView4 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView4;
                recyclerView4.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Salaippatti");
                return true;
            case C2668R.C2671id.allComplaints /*2131230811*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView5 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView5;
                recyclerView5.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                return true;
            case C2668R.C2671id.apply /*2131230817*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo(NotificationCompat.CATEGORY_STATUS, (Object) "Applied").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView6 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView6;
                recyclerView6.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Applied");
                return true;
            case C2668R.C2671id.completed /*2131230882*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo(NotificationCompat.CATEGORY_STATUS, (Object) "Completed...").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView7 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView7;
                recyclerView7.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Completed...");
                return true;
            case C2668R.C2671id.manalippallum /*2131231042*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("location", (Object) "Manalippallam").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView8 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView8;
                recyclerView8.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Manalippallam");
                return true;
            case C2668R.C2671id.process /*2131231162*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo(NotificationCompat.CATEGORY_STATUS, (Object) "Processing...").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView9 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView9;
                recyclerView9.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Processing...");
                return true;
            case C2668R.C2671id.thiruvellarai /*2131231301*/:
                this.adapter = new Admin_CompliantAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("location", (Object) "Thiruvellarai").orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build());
                RecyclerView recyclerView10 = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
                this.recyclerView = recyclerView10;
                recyclerView10.setHasFixedSize(true);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(this.adapter);
                this.adapter.startListening();
                Log.d(TAG, "filter:\t" + "Thiruvellarai");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
