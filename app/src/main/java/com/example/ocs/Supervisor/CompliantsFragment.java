package com.example.ocs.Supervisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ocs.AdapterClass.NoteAdapter;
import com.example.ocs.Interface.MCliclkableInterface;
import com.example.ocs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CompliantsFragment extends Fragment implements MCliclkableInterface {
    private static final String TAG = "CompliantsFragment";
    /* access modifiers changed from: private */
    public NoteAdapter adapter;
    AlertDialog alertDialog;
    ImageButton alert_close_btn;
    Button completed;

    /* renamed from: db */
    private FirebaseFirestore f526db;
    /* access modifiers changed from: private */
    public String docId;
    FirebaseAuth firebaseAuth;
    private CollectionReference notebookRef;
    ImageButton postImageView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public CompliantsFragment() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        this.f526db = instance;
        this.notebookRef = instance.collection("CompliantList");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_compliants, container, false);
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                CompliantsFragment.this.initRecycler(rootView);
                CompliantsFragment.this.adapter.notifyDataSetChanged();
                CompliantsFragment.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        initRecycler(rootView);
        return rootView;
    }

    /* access modifiers changed from: private */
    public void initRecycler(View view) {
        this.adapter = new NoteAdapter(new FirestoreRecyclerOptions.Builder().setQuery(this.notebookRef.orderBy("compliant_id", Query.Direction.DESCENDING), Note.class).build(), this);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.startListening();
    }

    public void onClickable(int position, String cId, String imageUrl) {
        Log.d(TAG, "Success:\t" + position);
        this.docId = cId;
        UpdatingOnProcess(cId);
        showAlert(imageUrl);
    }

    private void showAlert(String imageUri) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.activity_note_clickable, (ViewGroup) null);
        this.alert_close_btn = (ImageButton) v.findViewById(R.id.alert_close_btn);
        this.postImageView = (ImageButton) v.findViewById(R.id.postDisplayImage);
        this.completed = (Button) v.findViewById(R.id.completed);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5.0f);
        circularProgressDrawable.setCenterRadius(30.0f);
        circularProgressDrawable.start();
        ((RequestBuilder) Glide.with((Fragment) this).load(Uri.parse(imageUri)).placeholder((Drawable) circularProgressDrawable)).into((ImageView) this.postImageView);
        AlertDialog create = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme).setView(v).create();
        this.alertDialog = create;
        create.show();
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CompliantsFragment.this.alertDialog.dismiss();
            }
        });
        this.completed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(CompliantsFragment.this.getContext(), R.style.AlertDialogTheme).setTitle((CharSequence) "COMPLETED").setMessage((CharSequence) "Do you really want to Complete for the compliant.. ?").setPositiveButton((CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CompliantsFragment.this.firebaseAuth = FirebaseAuth.getInstance();
                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Compliant").document(CompliantsFragment.this.docId);
                        Map<String, Object> map = new HashMap<>();
                        map.put(NotificationCompat.CATEGORY_STATUS, "Completed...");
                        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            public void onSuccess(Void aVoid) {
                                Log.d(CompliantsFragment.TAG, "Successfull Updated..!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                Log.e(CompliantsFragment.TAG, "Updating Error:\t" + e);
                            }
                        });
                        CompliantsFragment.this.StoreHistory();
                        dialogInterface.dismiss();
                        CompliantsFragment.this.alertDialog.dismiss();
                    }
                }).setNegativeButton((CharSequence) "CANCEL", (DialogInterface.OnClickListener) null).create().show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void StoreHistory() {
        this.f526db.collection("CompliantList").document(this.docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(CompliantsFragment.TAG, "DocumentSnapshot data: " + document.getData());
                        FirebaseFirestore.getInstance().collection("history").add(Objects.requireNonNull(document.getData())).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(CompliantsFragment.TAG, "Successfull Store history");
                                CompliantsFragment.this.handleDeleteDocument();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                Log.d(CompliantsFragment.TAG, "Error");
                                Log.e(CompliantsFragment.TAG, "Error is:\t" + e);
                            }
                        });
                        return;
                    }
                    Log.d(CompliantsFragment.TAG, "No such document");
                    return;
                }
                Log.d(CompliantsFragment.TAG, "get failed with ", task.getException());
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleDeleteDocument() {
        this.f526db.collection("CompliantList").document(this.docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                Log.d(CompliantsFragment.TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.w(CompliantsFragment.TAG, "Error deleting document", e);
            }
        });
    }

    private void UpdatingOnProcess(String cId) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Compliant").document(cId);
        Map<String, Object> map = new HashMap<>();
        map.put(NotificationCompat.CATEGORY_STATUS, "Processing...");
        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                Log.d(CompliantsFragment.TAG, "Successfull Updated..!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.e(CompliantsFragment.TAG, "Updating Error:\t" + e);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.adapter.startListening();
    }

    public void onStop() {
        super.onStop();
        this.adapter.stopListening();
    }
}
