package com.example.ocs.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.complientsystem.C2668R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import p003de.hdodenhof.circleimageview.CircleImageView;

public class userProfile extends AppCompatActivity {
    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    private final String TAG = "UserProfile";
    int TAKE_IMAGE_CODE = 10001;
    CircleImageView circleImageView;
    EditText prof_name;
    ProgressBar progressBar;
    Button updateButton;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_user_profile);
        this.prof_name = (EditText) findViewById(C2668R.C2671id.profile_name);
        this.updateButton = (Button) findViewById(C2668R.C2671id.uploadButton);
        this.circleImageView = (CircleImageView) findViewById(C2668R.C2671id.profile_image);
        this.progressBar = (ProgressBar) findViewById(C2668R.C2671id.progressBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("UserProfile", "onCreate: " + user.getDisplayName());
            if (user.getDisplayName() != null) {
                this.prof_name.setText(user.getDisplayName());
                this.prof_name.setSelection(user.getDisplayName().length());
            }
            if (user.getPhotoUrl() != null) {
                Glide.with((FragmentActivity) this).load(user.getPhotoUrl()).into((ImageView) this.circleImageView);
            }
        }
        this.progressBar.setVisibility(8);
        this.updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userProfile.this.updateProfile(v);
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateProfile(final View v) {
        v.setEnabled(false);
        this.progressBar.setVisibility(0);
        this.DISPLAY_NAME = this.prof_name.getText().toString();
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(this.DISPLAY_NAME).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                v.setEnabled(true);
                userProfile.this.progressBar.setVisibility(8);
                Toast.makeText(userProfile.this, "Successfully updated profile", 0).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                v.setEnabled(true);
                userProfile.this.progressBar.setVisibility(8);
                Log.e("UserProfile", "onFailure: ", e.getCause());
            }
        });
    }

    public void handleImageClick(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, this.TAKE_IMAGE_CODE);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.TAKE_IMAGE_CODE && resultCode == -1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            this.circleImageView.setImageBitmap(bitmap);
            handleUpload(bitmap);
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child("profileImages").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpeg");
        reference.putBytes(baos.toByteArray()).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                userProfile.this.getDownloadUrl(reference);
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.e("UserProfile", "onFailure: ", e.getCause());
            }
        });
    }

    /* access modifiers changed from: private */
    public void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            public void onSuccess(Uri uri) {
                Log.d("UserProfile", "onSuccess: " + uri);
                userProfile.this.setUserProfileUrl(uri);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setUserProfileUrl(Uri uri) {
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(uri).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                Toast.makeText(userProfile.this, "Updated successfully", 0).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(userProfile.this, "Profile image failed...", 0).show();
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
