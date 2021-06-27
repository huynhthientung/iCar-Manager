package com.example.icar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.icar.MainActivity;
import com.example.icar.R;
import com.example.icar.feedback.FeedbackActivity;
import com.example.icar.model.Utils;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icar.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private static final int TIME_INTERVAL = 2000;
    private long backPressed;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onStart() {
        super.onStart();
        Utils.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Load data to header
        View headerView = navigationView.getHeaderView(0);
        TextView txtName = headerView.findViewById(R.id.textView_name);
        TextView txtEmail = headerView.findViewById(R.id.textView_email);
        ImageView avatar = headerView.findViewById(R.id.profile_image);
        txtName.setText(mUser.getDisplayName());
        txtEmail.setText(mUser.getEmail());
        Glide.with(this).asBitmap().load(mUser.getPhotoUrl()).into(avatar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_history, R.id.nav_transaction)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logout();
                return false;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_share).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(HomeActivity.this, "Share", Toast.LENGTH_SHORT).show();
                //TODO: handle later
                return false;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_send).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(HomeActivity.this, "Send", Toast.LENGTH_SHORT).show();
                //TODO: handle later
                return false;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_feedback).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(HomeActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                sendFeedback();
                return false;
            }
        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.nav_share:
//                        Toast.makeText(HomeActivity.this, "Share", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_send:
//                        Toast.makeText(HomeActivity.this, "Send", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_logout:
//                        mAuth.signOut();
//                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                        finish();
//                        break;
//                    default:
//                        break;
//                }
//                // This is for maintaining the behavior of the Navigation view
////                NavigationUI.onNavDestinationSelected(item, navController);
//                //This is for closing the drawer after acting on it
//                drawer.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
    }

    private void sendFeedback() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
//        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
//            mAuth.signOut();
//            finish();
//            return;
//        } else {
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        backPressed = System.currentTimeMillis();
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        finish();
                    }
                }).create().show();
    }

}