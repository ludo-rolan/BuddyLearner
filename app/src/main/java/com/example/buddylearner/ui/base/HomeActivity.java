package com.example.buddylearner.ui.base;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.databinding.ActivityHomeBinding;
import com.example.buddylearner.databinding.NavHeaderHomeBinding;
import com.example.buddylearner.ui.login.LogInActivity;
import com.example.buddylearner.ui.login.LogInViewModel;
import com.example.buddylearner.ui.login.LogInViewModelFactory;
import com.example.buddylearner.ui.topics.TopicsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeViewModel homeViewModel;

    ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        // add the toolbar/actionbar to layout
        setSupportActionBar(activityHomeBinding.appBarHome.toolbar);

        if (activityHomeBinding.appBarHome.fab != null) {
            activityHomeBinding.appBarHome.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show());
        }
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_home);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationView navigationView = activityHomeBinding.navView;
        if (navigationView != null) {

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform,
                    R.id.nav_reflow,
                    R.id.nav_slideshow,
                    R.id.nav_settings
            )
                    .setOpenableLayout(activityHomeBinding.drawerLayout)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }


        // added
        // detect the click on a navigation drawer menu item
        activityHomeBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_topics:
                        activityHomeBinding.drawerLayout.close();
                        Intent intent = new Intent(HomeActivity.this, TopicsActivity.class);
                        startActivity(intent);
                        return true;
                    default: return false;
                }
            }
        });

        // added



        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);


        // get users from firebase firestore

        // already done before
        //logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        // it works !
//        homeViewModel.loadUsers();
//        homeViewModel.getUsers().observe(this, users -> {
//            Log.d(TAG, "liste des utilisateurs : " + users.get(0).getUserName());
//            for (User user: users) {
//                Log.d(TAG, "username : " + user.getUserName());
//            }
//        });

        homeViewModel.loadUser(getIntent().getStringExtra("username"));
        //homeViewModel.loadUser(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        homeViewModel.getUser().observe(this, user -> {
            // Update the UI with the retrieved items
            // Set the user name in the navHeaderHome
            Log.d(TAG, "HomeActivity current user: " + user.getUserName());
//            ça ne fonctionne pas
//            navHeaderHomeBinding.navHeaderHomeUsername.setText(user.getUserName());

            // ça fonctionne, et la récupération de données et la mise à jour de l'ui
            //TextView textView = (activityHomeBinding.drawerLayout.findViewById(R.id.nav_view).getRootView()).findViewById(R.id.nav_header_home_username);
            //textView.setText(user.getUserName());

            // ça ne fonctionne pas
            //navHeaderHomeBinding1.navHeaderHomeUsername.setText(user.getUserName());

            TextView textview =  activityHomeBinding.drawerLayout.findViewById(R.id.nav_view).findViewById(R.id.nav_header_home_username);
            if (textview != null)
                textview.setText(user.getUserName());

        });

        // get users from firebase firestore


        BottomNavigationView bottomNavigationView = activityHomeBinding.appBarHome.contentHome.bottomNavView;
        if (bottomNavigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform,
                    R.id.nav_reflow,
                    R.id.nav_slideshow
            )
                    // added - set navigation drawer icon
                    .setOpenableLayout(activityHomeBinding.drawerLayout)
                    // added
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // change drawer icon button
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_account_circle_24);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        NavigationView navView = findViewById(R.id.nav_view);
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible
            getMenuInflater().inflate(R.menu.overflow, menu);
        }

        getMenuInflater().inflate(R.menu.overflow, menu);

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        if (item.getItemId() == R.id.nav_settings) {
//            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//            navController.navigate(R.id.nav_settings);
//        }

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.nav_settings:
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
                navController.navigate(R.id.nav_settings);
                break;
            case R.id.nav_logout:
                homeViewModel.disconnectUser();
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(intent);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}