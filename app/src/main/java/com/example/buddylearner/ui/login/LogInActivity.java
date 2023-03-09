package com.example.buddylearner.ui.login;

import static androidx.core.app.NavUtils.navigateUpFromSameTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.buddylearner.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        récupérer la barre d'action et afficher le bouton haut <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

//        retour à l'écran d'acceuil du téléphone
//        if (id == android.R.id.home) {
//            // Code à exécuter lorsque l'utilisateur clique sur le bouton "haut"
//            onBackPressed();
//            return true;
//        }

        //navigateUpTo(new Intent(this, RegisterActivity.class));
        navigateUpFromSameTask(this);

        return super.onOptionsItemSelected(item);
    }
}