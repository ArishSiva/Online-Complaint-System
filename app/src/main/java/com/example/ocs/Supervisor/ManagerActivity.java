package com.example.ocs.Supervisor;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.C2668R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ManagerActivity";
    BottomNavigationView bottomNavigationView;
    CompliantsFragment compliantsFragment = new CompliantsFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2668R.layout.activity_manager);
        BottomNavigationView bottomNavigationView2 = (BottomNavigationView) findViewById(C2668R.C2671id.bottomNavigation);
        this.bottomNavigationView = bottomNavigationView2;
        bottomNavigationView2.setOnNavigationItemSelectedListener(this);
        this.bottomNavigationView.setSelectedItemId(C2668R.C2671id.bottomNavigationComplaints);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C2668R.C2671id.bottomNavigationComplaints /*2131230831*/:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(C2668R.anim.fade_in, C2668R.anim.fade_out).replace(C2668R.C2671id.container, this.compliantsFragment).commit();
                return true;
            case C2668R.C2671id.bottomNavigationFeedback /*2131230832*/:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(C2668R.anim.fade_in, C2668R.anim.fade_out).replace(C2668R.C2671id.container, this.feedbackFragment).commit();
                return true;
            case C2668R.C2671id.bottomNavigationHistory /*2131230833*/:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(C2668R.anim.fade_in, C2668R.anim.fade_out).replace(C2668R.C2671id.container, this.historyFragment).commit();
                return true;
            default:
                return false;
        }
    }
}
