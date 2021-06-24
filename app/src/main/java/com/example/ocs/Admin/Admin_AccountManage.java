package com.example.ocs.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.C2668R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Admin_AccountManage extends AppCompatActivity {
    private static final String TAG = "AccountManageActivity";
    ImageButton add_close_dialog;
    Button add_supervisor;
    EditText adminId;
    Button admin_Button;
    /* access modifiers changed from: private */
    public String admin_key;
    AlertDialog alertDialog;
    ImageButton alert_close_btn;
    Button change_button;
    EditText change_password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText r_email;
    Button remove;
    ImageButton remove_close_button;
    EditText s_addEmail;
    EditText s_addName;
    EditText s_addPassword;
    private String suEmail;
    private String suName;
    private String suPassword;
    TextInputLayout textInputLayout;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Admin_AccountManage.this.change_button.setVisibility(8);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String adminPassword = Admin_AccountManage.this.change_password.getText().toString().trim();
            if (!adminPassword.isEmpty() && adminPassword.length() > 5) {
                Admin_AccountManage.this.change_button.setVisibility(0);
                Admin_AccountManage.this.admin_Button.setVisibility(8);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_admin__account_manage);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public void changeAdminPassword(View view) {
        this.firebaseFirestore.collection("Officers-Admins").document("admin-Key").addSnapshotListener((Activity) this, (EventListener<DocumentSnapshot>) new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<Admin_AccountManage> cls = Admin_AccountManage.class;
            }

            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (value != null) {
                    String unused = Admin_AccountManage.this.admin_key = value.getString("key");
                    Log.d(Admin_AccountManage.TAG, "admin_key:\t" + Admin_AccountManage.this.admin_key);
                    return;
                }
                throw new AssertionError();
            }
        });
        View view2 = LayoutInflater.from(this).inflate(C2668R.layout.show_change_admin_password, (ViewGroup) null);
        this.alert_close_btn = (ImageButton) view2.findViewById(C2668R.C2671id.alert_close_btn);
        this.adminId = (EditText) view2.findViewById(C2668R.C2671id.admin_id);
        this.admin_Button = (Button) view2.findViewById(C2668R.C2671id.admin_btn);
        this.textInputLayout = (TextInputLayout) view2.findViewById(C2668R.C2671id.new_pass);
        this.change_password = (EditText) view2.findViewById(C2668R.C2671id.admin_newPassword);
        this.change_button = (Button) view2.findViewById(C2668R.C2671id.change_btn);
        this.textInputLayout.setVisibility(8);
        this.change_button.setVisibility(8);
        this.change_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.firebaseAuth = FirebaseAuth.getInstance();
                String adminNewPassword = Admin_AccountManage.this.change_password.getText().toString();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("Officers-Admins").document("admin-Key");
                Map<String, Object> map = new HashMap<>();
                map.put("key", adminNewPassword);
                docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        Log.d(Admin_AccountManage.TAG, "Successfull Updated..!");
                        Toast.makeText(Admin_AccountManage.this, "Your Password Updated..!", 0).show();
                        Admin_AccountManage.this.alertDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Log.e(Admin_AccountManage.TAG, "Updating Error:\t" + e);
                    }
                });
            }
        });
        this.admin_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.handleAdminManageLog();
            }
        });
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.alertDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(this, C2668R.style.AlertDialogTheme).setView(view2).create();
        this.alertDialog = create;
        create.show();
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
    public void handleAdminManageLog() {
        if (validateId()) {
            this.textInputLayout.setVisibility(0);
            if (this.textInputLayout.getVisibility() == 0) {
                this.admin_Button.setVisibility(8);
                this.adminId.setKeyListener((KeyListener) null);
                this.change_password.addTextChangedListener(this.textWatcher);
            }
        }
    }

    public void addNewSuperVisor(View view) {
        alertShow();
    }

    /* access modifiers changed from: private */
    public boolean validateSuName() {
        if (this.s_addName.getText().toString().trim().isEmpty()) {
            this.s_addName.setError("Field can not be empty..!");
            this.s_addName.hasFocus();
            return false;
        }
        this.s_addName.setError((CharSequence) null);
        this.s_addName.clearFocus();
        return true;
    }

    /* access modifiers changed from: private */
    public boolean validateSuEmail() {
        String sEmail = this.s_addEmail.getText().toString().trim();
        if (sEmail.isEmpty()) {
            this.s_addEmail.setError("Field can not be empty..!");
            this.s_addEmail.hasFocus();
            return false;
        } else if (!sEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            this.s_addEmail.setError("No White space are allowed..!");
            this.s_addEmail.requestFocus();
            return false;
        } else {
            this.s_addEmail.setError((CharSequence) null);
            this.s_addEmail.clearFocus();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean validateSuPassword() {
        if (this.s_addPassword.getText().toString().trim().isEmpty()) {
            this.s_addPassword.setError("Field can not be empty..!");
            this.s_addPassword.hasFocus();
            return false;
        }
        this.s_addPassword.setError((CharSequence) null);
        this.s_addPassword.clearFocus();
        return true;
    }

    private void alertShow() {
        View sView = LayoutInflater.from(this).inflate(C2668R.layout.add_new_supervisor_activity, (ViewGroup) null);
        this.s_addName = (EditText) sView.findViewById(C2668R.C2671id.su_name);
        this.s_addEmail = (EditText) sView.findViewById(C2668R.C2671id.su_email);
        this.s_addPassword = (EditText) sView.findViewById(C2668R.C2671id.su_password);
        this.add_supervisor = (Button) sView.findViewById(C2668R.C2671id.add_btn);
        ImageButton imageButton = (ImageButton) sView.findViewById(C2668R.C2671id.add_close_btn);
        this.add_close_dialog = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.alertDialog.dismiss();
            }
        });
        this.add_supervisor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!((!Admin_AccountManage.this.validateSuName()) | (!Admin_AccountManage.this.validateSuEmail())) && !(!Admin_AccountManage.this.validateSuPassword())) {
                    Admin_AccountManage.this.addSupervisor(v);
                }
            }
        });
        AlertDialog create = new AlertDialog.Builder(this, C2668R.style.AlertDialogTheme).setView(sView).create();
        this.alertDialog = create;
        create.show();
    }

    /* access modifiers changed from: private */
    public void addSupervisor(View view) {
        this.suName = this.s_addName.getText().toString().trim();
        this.suEmail = this.s_addEmail.getText().toString().trim();
        this.suPassword = this.s_addPassword.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("email", this.suEmail);
        map.put("name", this.suName);
        map.put("password", this.suPassword);
        this.firebaseFirestore.collection("Officers-Admins").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            public void onSuccess(DocumentReference documentReference) {
                Log.d(Admin_AccountManage.TAG, "New Supervisor Added..!");
                Toast.makeText(Admin_AccountManage.this, "New Supervisor Added..!", 0).show();
                Admin_AccountManage.this.alertDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.e(Admin_AccountManage.TAG, "Supervisor Adding Error:\t" + e);
                e.printStackTrace();
            }
        });
    }

    public void removeSuperVisor(View view) {
        showRemoveAlert();
    }

    private void showRemoveAlert() {
        View rView = LayoutInflater.from(this).inflate(C2668R.layout.removie_supervisor, (ViewGroup) null);
        this.r_email = (EditText) rView.findViewById(C2668R.C2671id.re_email);
        Button button = (Button) rView.findViewById(C2668R.C2671id.remove_btn);
        this.remove = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.removePerson(v);
            }
        });
        ImageButton imageButton = (ImageButton) rView.findViewById(C2668R.C2671id.remove_close_btn);
        this.remove_close_button = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Admin_AccountManage.this.alertDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(this, C2668R.style.AlertDialogTheme).setView(rView).create();
        this.alertDialog = create;
        create.show();
    }

    private boolean validateRemoveField() {
        String rEmail = this.r_email.getText().toString().trim();
        if (rEmail.isEmpty()) {
            this.r_email.setError("Field can not be empty..!");
            this.r_email.hasFocus();
            return false;
        } else if (!rEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            this.r_email.setError("No White space are allowed..!");
            this.r_email.requestFocus();
            return false;
        } else {
            this.s_addEmail.setError((CharSequence) null);
            this.s_addEmail.clearFocus();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void removePerson(View v) {
        String rEmail = this.r_email.getText().toString().trim();
        Log.d(TAG, "vvv" + rEmail);
        if (validateRemoveField()) {
            this.firebaseFirestore.collection("Officers-Admins").whereEqualTo("email", (Object) rEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    WriteBatch batch = FirebaseFirestore.getInstance().batch();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        batch.delete(documentSnapshot.getReference());
                    }
                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                        public void onSuccess(Void aVoid) {
                            Log.d(Admin_AccountManage.TAG, "Data Removed");
                            Toast.makeText(Admin_AccountManage.this, "Successful Removed..!", 0).show();
                            Admin_AccountManage.this.alertDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            Log.d(Admin_AccountManage.TAG, "Data Removed Error" + e);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
