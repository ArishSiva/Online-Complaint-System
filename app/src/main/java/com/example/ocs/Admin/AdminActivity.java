package com.example.ocs.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.C2668R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "AdminActivity";
    EditText adminId;
    private Button admin_Button;
    /* access modifiers changed from: private */
    public String admin_key;
    AlertDialog alertDialog;
    ImageButton alert_close_btn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_admin);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
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
    public void handleAdminLog() {
        if (validateId()) {
            startActivity(new Intent(this, Admin_ShowFeedback.class));
            this.alertDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void handleAdminManageLog() {
        if (validateId()) {
            startActivity(new Intent(this, Admin_AccountManage.class));
            this.alertDialog.dismiss();
        }
    }

    public void adminComplaintCard(View view) {
        startActivity(new Intent(this, Admin_ShowComplaint.class));
    }

    public void adminFeedbackCard(View view) {
        this.firebaseFirestore.collection("Officers-Admins").document("admin-Key").addSnapshotListener((Activity) this, (EventListener<DocumentSnapshot>) new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<AdminActivity> cls = AdminActivity.class;
            }

            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (value != null) {
                    String unused = AdminActivity.this.admin_key = value.getString("key");
                    Log.d(AdminActivity.TAG, "admin_key:\t" + AdminActivity.this.admin_key);
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
                AdminActivity.this.handleAdminLog();
            }
        });
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.this.alertDialog.dismiss();
            }
        });
    }

    public void adminHistoryCard(View view) {
        startActivity(new Intent(this, Admin_ShowHistory.class));
    }

    public void adminComplaintSearch(View view) {
    }

    public void adminInformation(View view) {
        startActivity(new Intent(this, Add_Admin_Information.class));
    }

    public void handleAccountManaging(View view) {
        this.firebaseFirestore.collection("Officers-Admins").document("admin-Key").addSnapshotListener((Activity) this, (EventListener<DocumentSnapshot>) new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<AdminActivity> cls = AdminActivity.class;
            }

            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (value != null) {
                    String unused = AdminActivity.this.admin_key = value.getString("key");
                    Log.d(AdminActivity.TAG, "admin_key:\t" + AdminActivity.this.admin_key);
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
                AdminActivity.this.handleAdminManageLog();
            }
        });
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.this.alertDialog.dismiss();
            }
        });
    }
}
