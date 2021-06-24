package com.example.ocs.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.C2668R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Iterator;

public class OfficersLogin extends AppCompatActivity {
    private final String TAG = "OfficersLogin";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseStorage;
    Button log_button;
    /* access modifiers changed from: private */
    public String off_id;
    /* access modifiers changed from: private */
    public String off_password;
    EditText text_id;
    EditText text_password;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_officers_login);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseStorage = FirebaseFirestore.getInstance();
        this.text_id = (EditText) findViewById(C2668R.C2671id.officer_id);
        this.text_password = (EditText) findViewById(C2668R.C2671id.officer_password);
        Button button = (Button) findViewById(C2668R.C2671id.off_log_button);
        this.log_button = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OfficersLogin.this.firebaseStorage.collection("Officers-Admins").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error == null) {
                            Iterator<QueryDocumentSnapshot> it = value.iterator();
                            while (it.hasNext()) {
                                DocumentSnapshot documentSnapshot = it.next();
                                Log.d("OfficersLogin", "value:" + documentSnapshot.getString("email") + "\t" + documentSnapshot.getString("password"));
                                String unused = OfficersLogin.this.off_id = documentSnapshot.getString("email");
                                String unused2 = OfficersLogin.this.off_password = documentSnapshot.getString("password");
                                OfficersLogin.this.handleLogin(OfficersLogin.this.off_id, OfficersLogin.this.off_password);
                            }
                        }
                    }
                });
            }
        });
    }

    private boolean validateId() {
        if (this.text_id.getText().toString().trim().isEmpty()) {
            this.text_id.setError("Field can not be empty..!");
            this.text_id.hasFocus();
            return false;
        }
        this.text_id.setError((CharSequence) null);
        this.text_id.clearFocus();
        return true;
    }

    private boolean validatePassword() {
        if (this.text_password.getText().toString().trim().isEmpty()) {
            this.text_password.setError("Field can not be empty..!");
            this.text_password.hasFocus();
            return false;
        }
        this.text_password.setError((CharSequence) null);
        this.text_password.clearFocus();
        return true;
    }

    /* access modifiers changed from: private */
    public void handleLogin(String off_id2, String off_password2) {
        if (validateId() && validatePassword()) {
            String get_id = this.text_id.getText().toString().trim();
            String get_password = this.text_password.getText().toString().trim();
            if (get_id.equals(off_id2) && get_password.equals(off_password2)) {
                startActivity(new Intent(this, ManagerActivity.class));
                finish();
            }
        }
    }
}
