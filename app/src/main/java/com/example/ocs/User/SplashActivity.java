package com.example.ocs.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.complientsystem.C2668R;

public class SplashActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(ContextCompat.getColor(this, C2668R.C2669color.white));
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
    }
}
