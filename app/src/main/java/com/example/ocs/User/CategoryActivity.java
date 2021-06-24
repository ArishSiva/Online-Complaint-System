package com.example.ocs.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.complientsystem.C2668R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import Admin.AdminActivity;
import Supervisor.OfficersLogin;

public class CategoryActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private static final String TAG = "CategoryActivity";
    ImageView adminBtn;
    EditText adminId;
    private Button admin_Button;
    /* access modifiers changed from: private */
    public String admin_key;
    AlertDialog alertDialog;
    ImageButton alert_close_btn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView officerBtn;
    ImageView userBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_category);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startLoginActivity();
            finish();
        }
        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(ContextCompat.getColor(this, C2668R.C2669color.white));
        this.userBtn = (ImageView) findViewById(C2668R.C2671id.user);
        this.officerBtn = (ImageView) findViewById(C2668R.C2671id.officer);
        this.adminBtn = (ImageView) findViewById(C2668R.C2671id.admin);
        this.userBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryActivity.this.startActivity(new Intent(CategoryActivity.this, LoginActivity.class));
                CategoryActivity.this.finish();
            }
        });
        this.officerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryActivity.this.startActivity(new Intent(CategoryActivity.this, OfficersLogin.class));
                CategoryActivity.this.finish();
            }
        });
        this.adminBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryActivity.this.showAdminLog(v);
            }
        });
    }

    private boolean validateId() {
        String get_id = this.adminId.getText().toString().trim();
        if (get_id.isEmpty()) {
            this.adminId.setError("Field can not be empty..!");
            this.adminId.hasFocus();
            return false;
        } else if (!get_id.equals(this.admin_key)) {
            this.adminId.setError("Please Enter Valid id..!");
            this.adminId.requestFocus();
            return false;
        } else {
            this.adminId.setError((CharSequence) null);
            this.adminId.clearFocus();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void showAdminLog(View view) {
        this.firebaseFirestore.collection("Officers-Admins").document("admin-Key").addSnapshotListener((Activity) this, (EventListener<DocumentSnapshot>) new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CategoryActivity> cls = CategoryActivity.class;
            }

            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (value != null) {
                    String unused = CategoryActivity.this.admin_key = value.getString("key");
                    Log.d(CategoryActivity.TAG, "admin_key:\t" + CategoryActivity.this.admin_key);
                    return;
                }
                throw new AssertionError();
            }
        });
        View view2 = LayoutInflater.from(this).inflate(C2668R.layout.show_admin_log, (ViewGroup) null);
        this.alert_close_btn = (ImageButton) view2.findViewById(C2668R.C2671id.alert_close_btn);
        this.adminId = (EditText) view2.findViewById(C2668R.C2671id.admin_id);
        this.admin_Button = (Button) view2.findViewById(C2668R.C2671id.admin_btn);
        AlertDialog create = new AlertDialog.Builder(this, C2668R.style.AlertDialogTheme).setView(view2).create();
        this.alertDialog = create;
        create.show();
        this.admin_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryActivity.this.handleAdminLog();
            }
        });
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryActivity.this.alertDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleAdminLog() {
        if (validateId()) {
            startActivity(new Intent(this, AdminActivity.class));
            this.alertDialog.dismiss();
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    public void onAuthStateChanged(FirebaseAuth firebaseAuth2) {
        if (firebaseAuth2.getCurrentUser() != null) {
            startLoginActivity();
        }
    }
}
