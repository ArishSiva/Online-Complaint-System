package com.example.ocs.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ocs.R;
import com.example.ocs.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    TextView Email;
    private final String TAG = "MainActivity";
    CircleImageView circleImageView;
    FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    TextView userName;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.firebaseAuth = FirebaseAuth.getInstance();
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, CategoryActivity.class));
            finish();
        }
        this.mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_about, R.id.nav_contact, R.id.nav_terms).setDrawerLayout((DrawerLayout) findViewById(R.id.drawer_layout)).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) this, navController, this.mAppBarConfiguration);
        NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.nav_view), navController);
        updateNavHeader();
    }

    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), this.mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void handleLogOutItem(MenuItem item) {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).setTitle((CharSequence) "LOGOUT").setMessage((CharSequence) "Do you really want to exit ?").setPositiveButton((CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity();
            }
        }).setNegativeButton((CharSequence) "CANCEL", (DialogInterface.OnClickListener) null).create().show();
    }

    /* access modifiers changed from: private */
    public void startActivity() {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    public void updateNavHeader() {
        View headerView = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        this.userName = (TextView) headerView.findViewById(R.id.h_name);
        this.Email = (TextView) headerView.findViewById(R.id.h_email);
        CircleImageView circleImageView2 = (CircleImageView) headerView.findViewById(R.id.profile_image);
        this.circleImageView = circleImageView2;
        circleImageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, userProfile.class));

        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            this.userName.setText(name);
            this.Email.setText(email);
            Glide.with((FragmentActivity) this).load(user.getPhotoUrl()).into((ImageView) this.circleImageView);
        }
    }

    public void handlePostCompliant(View view) {
        startActivity(new Intent(this, PostCompliant.class));
    }

    public void handleViewStatus(View view) {
        startActivity(new Intent(this, UserStatus.class));
    }

    public void handleUserProfile(View view) {
        startActivity(new Intent(this, userProfile.class));
    }

    public void handleAdminInformation(View view) {
        startActivity(new Intent(this, UShow_Information.class));
    }

    public static class MainActivity extends AppCompatActivity {
    
        private AppBarConfiguration mAppBarConfiguration;
        private ActivityMainBinding binding;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
    
            setSupportActionBar(binding.appBarMain.toolbar);
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }
    
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    
        @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }
    }
}
