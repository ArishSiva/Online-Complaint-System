package com.example.ocs.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.complientsystem.C2668R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class PostCompliant extends AppCompatActivity {
    private final String TAG = "PostCompliant";
    ArrayAdapter arrayAdapter;
    /* access modifiers changed from: private */
    public Bitmap bitmap;
    Button compliant_button;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    TextView textUpload;
    EditText text_compliant;
    AutoCompleteTextView text_compliantLevel;
    AutoCompleteTextView text_compliantType;
    EditText text_email;
    AutoCompleteTextView text_location;
    EditText text_mobileNumber;
    EditText text_name;
    EditText text_street;
    ImageView uploadButton;
    private String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_post_compliant);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
        this.user_id = this.firebaseAuth.getCurrentUser().getUid();
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(ContextCompat.getColor(this, C2668R.C2669color.purple_500));
        this.text_name = (EditText) findViewById(C2668R.C2671id.name);
        this.text_email = (EditText) findViewById(C2668R.C2671id.email);
        this.text_mobileNumber = (EditText) findViewById(C2668R.C2671id.mobile);
        this.text_street = (EditText) findViewById(C2668R.C2671id.street);
        this.text_compliant = (EditText) findViewById(C2668R.C2671id.compliant);
        this.uploadButton = (ImageView) findViewById(C2668R.C2671id.uploadImage);
        this.textUpload = (TextView) findViewById(C2668R.C2671id.text_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.text_location = (AutoCompleteTextView) findViewById(C2668R.C2671id.location);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, C2668R.layout.options_item, new String[]{"Thiruvellarai", "Salaippatti", "Kalavaipatty", "Kunnakulam", "Manalippallam"});
        this.arrayAdapter = arrayAdapter2;
        this.text_location.setAdapter(arrayAdapter2);
        this.text_compliantType = (AutoCompleteTextView) findViewById(C2668R.C2671id.compliant_type);
        this.text_compliantType.setAdapter(new ArrayAdapter(this, C2668R.layout.options_item, new String[]{"Street Light", "Drainage", "Rain Water", "Garbage", "Road Reconstruction", "Pipe Leakage"}));
        this.text_compliantLevel = (AutoCompleteTextView) findViewById(C2668R.C2671id.compliant_level);
        this.text_compliantLevel.setAdapter(new ArrayAdapter(this, C2668R.layout.options_item, new String[]{"Low", "Medium", "High"}));
        Button button = (Button) findViewById(C2668R.C2671id.c_button);
        this.compliant_button = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PostCompliant postCompliant = PostCompliant.this;
                postCompliant.handleStoreCompliant(postCompliant.bitmap);
            }
        });
        this.uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    PostCompliant.this.chooseImage();
                } else if (ContextCompat.checkSelfPermission(PostCompliant.this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    ActivityCompat.requestPermissions(PostCompliant.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
                } else {
                    PostCompliant.this.chooseImage();
                }
            }
        });
    }

    private boolean validateName() {
        if (this.text_name.getText().toString().trim().isEmpty()) {
            this.text_name.setError("Field can not be empty..!");
            this.text_name.requestFocus();
            return false;
        }
        this.text_name.setError((CharSequence) null);
        this.text_name.clearFocus();
        return true;
    }

    private boolean validateEmail() {
        String email = this.text_email.getText().toString().trim();
        if (email.isEmpty()) {
            this.text_email.setError("Field can not be empty..!");
            this.text_email.hasFocus();
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            this.text_email.setError("No White space are allowed..!");
            this.text_email.requestFocus();
            return false;
        } else {
            this.text_email.setError((CharSequence) null);
            this.text_email.clearFocus();
            return true;
        }
    }

    private boolean validateMobileNumber() {
        String mobileNumber = this.text_mobileNumber.getText().toString().trim();
        if (mobileNumber.isEmpty()) {
            this.text_mobileNumber.setError("Field can not be empty..!");
            this.text_mobileNumber.requestFocus();
            return false;
        } else if (!mobileNumber.matches("[6-9][0-9]{9}")) {
            this.text_mobileNumber.setError("Invalid Email Address..!");
            this.text_mobileNumber.requestFocus();
            return false;
        } else {
            this.text_mobileNumber.setError((CharSequence) null);
            this.text_mobileNumber.clearFocus();
            return true;
        }
    }

    private boolean validateStreet() {
        if (this.text_street.getText().toString().trim().isEmpty()) {
            this.text_street.setError("Field can not be empty..!");
            this.text_street.requestFocus();
            return false;
        }
        this.text_street.setError((CharSequence) null);
        this.text_street.clearFocus();
        return true;
    }

    private boolean validateLocation() {
        String location = this.text_location.getText().toString();
        Log.d("PostCompliant", "Result:" + location);
        if (location.isEmpty()) {
            this.text_location.setError("Field can not be empty..!");
            this.text_location.requestFocus();
            return false;
        }
        this.text_location.setError((CharSequence) null);
        this.text_location.clearFocus();
        return true;
    }

    private boolean validateCompliantType() {
        if (this.text_compliantType.getText().toString().isEmpty()) {
            this.text_compliantType.setError("Field can not be empty..!");
            this.text_compliantType.requestFocus();
            return false;
        }
        this.text_compliantType.setError((CharSequence) null);
        this.text_compliantType.clearFocus();
        return true;
    }

    private boolean validateCompliantLevel() {
        if (this.text_compliantLevel.getText().toString().isEmpty()) {
            this.text_compliantLevel.setError("Field can not be empty..!");
            this.text_compliantLevel.requestFocus();
            return false;
        }
        this.text_compliantLevel.setError((CharSequence) null);
        this.text_compliantLevel.clearFocus();
        return true;
    }

    private boolean validateCompliant() {
        if (this.text_compliant.getText().toString().trim().isEmpty()) {
            this.text_compliant.setError("Field can not be empty..!");
            this.text_compliant.requestFocus();
            return false;
        }
        this.text_compliant.setError((CharSequence) null);
        this.text_compliant.clearFocus();
        return true;
    }

    /* access modifiers changed from: private */
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "IMAGE SELECT"), 1);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null && data.getData() != null) {
            try {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                this.bitmap = bitmap2;
                this.uploadButton.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleStoreCompliant(Bitmap bitmap2) {
        if (!((!validateName()) | (!validateEmail()) | (!validateMobileNumber()) | (!validateStreet()) | (!validateLocation()) | (!validateCompliantType()) | (!validateCompliant())) && !(!validateCompliantLevel())) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("File Uploading.......");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            this.storageReference.child("Images").child(UUID.randomUUID().toString()).putBytes(baos.toByteArray()).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                Log.d("PostCompliant", "Success Image Url Task:\t" + imageUrl);
                                PostCompliant.this.storeData(imageUrl);
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            }).addOnProgressListener((OnProgressListener) new OnProgressListener<UploadTask.TaskSnapshot>() {
                public void onProgress(UploadTask.TaskSnapshot snapshot) {
                    progressDialog.setMessage("File Uploading...." + ((int) ((((double) snapshot.getBytesTransferred()) * 100.0d) / ((double) snapshot.getTotalByteCount()))) + "%");
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    public void storeData(String r28) {
        /*
            r27 = this;
            r0 = r27
            android.widget.EditText r1 = r0.text_name
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = r1.trim()
            android.widget.EditText r2 = r0.text_email
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r15 = r2.trim()
            android.widget.EditText r2 = r0.text_mobileNumber
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r13 = r2.trim()
            android.widget.EditText r2 = r0.text_street
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r12 = r2.trim()
            android.widget.AutoCompleteTextView r2 = r0.text_location
            android.text.Editable r2 = r2.getText()
            java.lang.String r11 = r2.toString()
            android.widget.AutoCompleteTextView r2 = r0.text_compliantType
            android.text.Editable r2 = r2.getText()
            java.lang.String r10 = r2.toString()
            android.widget.AutoCompleteTextView r2 = r0.text_compliantLevel
            android.text.Editable r2 = r2.getText()
            java.lang.String r9 = r2.toString()
            android.widget.EditText r2 = r0.text_compliant
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r8 = r2.trim()
            r7 = r28
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            com.google.firebase.auth.FirebaseAuth r3 = r0.firebaseAuth
            com.google.firebase.auth.FirebaseUser r3 = r3.getCurrentUser()
            java.lang.String r3 = r3.getUid()
            java.lang.StringBuilder r2 = r2.append(r3)
            long r3 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r6 = r2.toString()
            r14 = r6
            java.lang.String r16 = "Applied"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Doc path"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "PostCompliant"
            android.util.Log.d(r3, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "Data:\t\n"
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r15)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r13)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r12)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r10)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r9)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r8)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r3, r2)
            Models.Note r17 = new Models.Note
            com.google.firebase.Timestamp r5 = new com.google.firebase.Timestamp
            java.util.Date r2 = new java.util.Date
            r2.<init>()
            r5.<init>((java.util.Date) r2)
            java.lang.String r4 = r0.user_id
            r2 = r17
            r3 = r1
            r18 = r4
            r4 = r15
            r19 = r5
            r5 = r13
            r20 = r1
            r1 = r6
            r6 = r12
            r21 = r7
            r7 = r11
            r22 = r8
            r8 = r10
            r23 = r9
            r24 = r10
            r10 = r22
            r25 = r11
            r11 = r19
            r19 = r12
            r12 = r18
            r18 = r13
            r13 = r21
            r26 = r15
            r15 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            com.google.firebase.firestore.FirebaseFirestore r3 = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            java.lang.String r4 = "Compliant"
            com.google.firebase.firestore.CollectionReference r3 = r3.collection(r4)
            com.google.firebase.firestore.DocumentReference r3 = r3.document(r1)
            com.google.android.gms.tasks.Task r3 = r3.set(r2)
            User.PostCompliant$6 r4 = new User.PostCompliant$6
            r4.<init>(r1)
            com.google.android.gms.tasks.Task r3 = r3.addOnSuccessListener(r4)
            User.PostCompliant$5 r4 = new User.PostCompliant$5
            r4.<init>()
            r3.addOnFailureListener(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: User.PostCompliant.storeData(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public void restoreToList(final String documentPath) {
        FirebaseFirestore.getInstance().collection("Compliant").document(documentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("PostCompliant", "DocumentSnapshot data: " + document.getData());
                        FirebaseFirestore.getInstance().collection("CompliantList").document(documentPath).set(Objects.requireNonNull(document.getData())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            public void onSuccess(Void aVoid) {
                                Log.d("PostCompliant", "Send Data to List..!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                Log.e("PostCompliant", "Error: GoTo List \t" + e);
                            }
                        });
                        return;
                    }
                    Log.d("PostCompliant", "No such document");
                    return;
                }
                Log.d("PostCompliant", "get failed with ", task.getException());
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
