package com.example.ocs.Admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.ocs.AdapterClass.FeedbackAdapter;
import com.example.ocs.Interface.FeedbackClickable;
import com.example.ocs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Admin_ShowFeedback extends AppCompatActivity implements FeedbackClickable {

    /* renamed from: db */
    private FirebaseFirestore f524db;
    /* access modifiers changed from: private */
    public FeedbackAdapter feedbackAdapter;
    FirebaseAuth firebaseAuth;
    private CollectionReference notebookRef;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public Admin_ShowFeedback() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f524db = instance;
        this.notebookRef = instance.collection("Feedback");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_admin__show_feedback);
        this.firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Admin_ShowFeedback.this.initRecycler();
                Admin_ShowFeedback.this.feedbackAdapter.notifyDataSetChanged();
                Admin_ShowFeedback.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler();
    }

    /* access modifiers changed from: private */
    public void initRecycler() {
        this.feedbackAdapter = new FeedbackAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("feedback", Query.Direction.DESCENDING), FeedbackModel.class).build(), this);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.Admin_Feed_recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.feedbackAdapter);
        this.feedbackAdapter.startListening();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public void onClickableFeedback(int position, String name, String userId, String complaintId) {
        new AlertDialog.Builder(this).setMessage("UserName:\t\t" + name + "\nUserId:\t\t\t\t\t" + complaintId + "\n\nCompliantId:\t" + userId).create().show();
    }
}
