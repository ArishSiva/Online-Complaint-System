package com.example.ocs.User;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.complientsystem.C2668R;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ClickableRecyclerActivity extends AppCompatActivity {
    private static final String TAG = "ClickableRecycler";
    AlertDialog alertDialog;
    ImageView alert_close_btn;
    ImageView displayImage;
    private String document;
    Button feedback_Button;
    EditText feedback_Name;
    EditText feedback_email;
    FirebaseAuth firebaseAuth;
    EditText get_feedback;
    FirebaseStorage mStorage;
    Button send_button;
    StorageReference storageReference;
    private String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_clickable_recycler);
        this.displayImage = (ImageView) findViewById(C2668R.C2671id.displayImage);
        FirebaseStorage instance = FirebaseStorage.getInstance();
        this.mStorage = instance;
        this.storageReference = instance.getReference();
        this.feedback_Button = (Button) findViewById(C2668R.C2671id.feedback);
        FirebaseAuth instance2 = FirebaseAuth.getInstance();
        this.firebaseAuth = instance2;
        this.user_id = instance2.getCurrentUser().getUid();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String disImage = getIntent().getStringExtra(Scopes.PROFILE);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5.0f);
        circularProgressDrawable.setCenterRadius(30.0f);
        circularProgressDrawable.start();
        ((RequestBuilder) Glide.with((FragmentActivity) this).load(Uri.parse(disImage)).placeholder((Drawable) circularProgressDrawable)).into(this.displayImage);
        this.feedback_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickableRecyclerActivity.this.showAlert();
            }
        });
    }

    private boolean validateName() {
        if (this.feedback_Name.getText().toString().trim().isEmpty()) {
            this.feedback_Name.setError("Field can not be empty..!");
            this.feedback_Name.requestFocus();
            return false;
        }
        this.feedback_Name.setError((CharSequence) null);
        this.feedback_Name.clearFocus();
        return true;
    }

    private boolean validateEmail() {
        String email = this.feedback_email.getText().toString().trim();
        if (email.isEmpty()) {
            this.feedback_email.setError("Field can not be empty..!");
            this.feedback_email.hasFocus();
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            this.feedback_email.setError("No White space are allowed..!");
            this.feedback_email.requestFocus();
            return false;
        } else {
            this.feedback_email.setError((CharSequence) null);
            this.feedback_email.clearFocus();
            return true;
        }
    }

    private boolean validateFeedback() {
        if (this.get_feedback.getText().toString().trim().isEmpty()) {
            this.get_feedback.setError("Field can not be empty..!");
            this.get_feedback.requestFocus();
            return false;
        }
        this.get_feedback.setError((CharSequence) null);
        this.get_feedback.clearFocus();
        return true;
    }

    /* access modifiers changed from: private */
    public void showAlert() {
        View v = LayoutInflater.from(this).inflate(C2668R.layout.feedback_activity, (ViewGroup) null);
        this.feedback_Name = (EditText) v.findViewById(C2668R.C2671id.feed_name);
        this.feedback_email = (EditText) v.findViewById(C2668R.C2671id.feed_email);
        this.get_feedback = (EditText) v.findViewById(C2668R.C2671id.feedback);
        this.send_button = (Button) v.findViewById(C2668R.C2671id.feed_btn);
        this.alert_close_btn = (ImageView) v.findViewById(C2668R.C2671id.alert_close_btn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            this.feedback_Name.setText(name);
            this.feedback_email.setText(email);
        }
        this.send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickableRecyclerActivity.this.storeData(v);
            }
        });
        AlertDialog create = new AlertDialog.Builder(this, C2668R.style.AlertDialogTheme).setView(v).create();
        this.alertDialog = create;
        create.show();
        this.alert_close_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickableRecyclerActivity.this.alertDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void storeData(View v) {
        String user = this.user_id;
        String feed_name = this.feedback_Name.getText().toString();
        String feed_email = this.feedback_email.getText().toString();
        String user_feedback = this.get_feedback.getText().toString();
        this.document = getIntent().getStringExtra("document_path");
        if (!((!validateName()) | (!validateEmail())) && !(!validateFeedback())) {
            Map<String, Object> feedback = new HashMap<>();
            feedback.put("name", feed_name);
            feedback.put("email", feed_email);
            feedback.put("feedback", user_feedback);
            feedback.put("user", user);
            feedback.put("compliant_id", this.document);
            FirebaseFirestore.getInstance().collection("Feedback").add(feedback).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(ClickableRecyclerActivity.TAG, "Feedback SuccessFull Store..!");
                    ClickableRecyclerActivity.this.alertDialog.dismiss();
                    ClickableRecyclerActivity.this.feedback_Button.setVisibility(8);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Log.e(ClickableRecyclerActivity.TAG, "Feedback Not Store..!" + e);
                    Toast.makeText(ClickableRecyclerActivity.this, "Notwork Error..Try to Ofter Few Mints", 0).show();
                    ClickableRecyclerActivity.this.feedback_Button.setVisibility(0);
                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
