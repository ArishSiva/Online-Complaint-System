package com.example.ocs.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import AdapterClass.NoteRecyclerAdapter;
import Interface.RecyclerClickableInterface;
import Models.Note;

public class UserStatus extends AppCompatActivity implements RecyclerClickableInterface {
    private static final String TAG = "MainActivity";
    FirebaseAuth firebaseAuth;
    NoteRecyclerAdapter notesRecyclerAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_user_status);
        this.recyclerView = (RecyclerView) findViewById(C2668R.C2671id.recyclerView);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(C2668R.C2671id.swipeRefreshLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                UserStatus userStatus = UserStatus.this;
                userStatus.initRecyclerView(userStatus.firebaseAuth.getCurrentUser());
                UserStatus.this.notesRecyclerAdapter.notifyDataSetChanged();
                UserStatus.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecyclerView(this.firebaseAuth.getCurrentUser());
    }

    /* access modifiers changed from: private */
    public void initRecyclerView(FirebaseUser user) {
        this.notesRecyclerAdapter = new NoteRecyclerAdapter(new FirestoreRecyclerOptions.Builder().setQuery(FirebaseFirestore.getInstance().collection("Compliant").whereEqualTo("user_id", (Object) user.getUid()), Note.class).build(), this);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.notesRecyclerAdapter);
        this.notesRecyclerAdapter.startListening();
    }

    public void onItemClick(int adapterPosition, String disImageUrl, String documentPath) {
        Intent intent = new Intent(this, ClickableRecyclerActivity.class);
        intent.putExtra(Scopes.PROFILE, disImageUrl);
        intent.putExtra("document_path", documentPath);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
