package com.example.buddylearner.ui.topics;

import android.content.Intent;
import android.os.Bundle;

import com.example.buddylearner.R;
import com.example.buddylearner.databinding.ActivityTopicsBinding;
import com.example.buddylearner.ui.base.HomeActivity;
import com.example.buddylearner.ui.login.LogInActivity;
import com.example.buddylearner.ui.signup.SignUpActivity;
import com.example.buddylearner.ui.topics.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

public class TopicsActivity extends AppCompatActivity {

    private ActivityTopicsBinding activityTopicsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTopicsBinding = ActivityTopicsBinding.inflate(getLayoutInflater());
        setContentView(activityTopicsBinding.getRoot());

        //setSupportActionBar(activityTopicsBinding.toolbar);

        //        récupérer la barre d'action et afficher le bouton haut <-
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            Log.getStackTraceString(npe);
        }


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = activityTopicsBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = activityTopicsBinding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = activityTopicsBinding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // navigate to the previous activity - it works !
        Intent intent = new Intent(TopicsActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_FROM_BACKGROUND);
        startActivity(intent);

        return super.onOptionsItemSelected(item);

    }
}