package com.example.ocs.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.C2668R;
import com.firebase.p013ui.auth.AuthUI;
import com.firebase.p013ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    int AUTHUI_REQUEST_CODE = 1001;
    private final String TAG = "LoginActivity";
    FirebaseAuth firebaseAuth;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_login);
        this.firebaseAuth = FirebaseAuth.getInstance();
        handleLogin();
    }

    private void handleLogin() {
        startActivityForResult(((AuthUI.SignInIntentBuilder) ((AuthUI.SignInIntentBuilder) ((AuthUI.SignInIntentBuilder) ((AuthUI.SignInIntentBuilder) ((AuthUI.SignInIntentBuilder) ((AuthUI.SignInIntentBuilder) AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)).setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig[]{new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build()}))).setTheme(C2668R.style.LoginTheme)).setTosAndPrivacyPolicyUrls("http://example.coms", "http://examples.com")).setLogo(C2668R.C2670drawable.ic_main_logo)).setAlwaysShowSignInMethodScreen(true)).build(), this.AUTHUI_REQUEST_CODE);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != this.AUTHUI_REQUEST_CODE) {
            return;
        }
        if (resultCode == -1) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("LoginActivity", "onActivityResult: " + user.getEmail());
            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                Toast.makeText(this, "Welcome New User..!", 0).show();
            } else {
                Toast.makeText(this, "Welcome Back Again..!", 0).show();
            }
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (response == null) {
            Log.d("LoginActivity", "onActivityResult: The user has cancelled the signing request");
        } else {
            Log.d("LoginActivity", "onActivityResult: ", response.getError());
        }
    }
}
