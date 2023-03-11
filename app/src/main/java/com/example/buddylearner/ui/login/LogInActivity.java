package com.example.buddylearner.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddylearner.R;
import com.example.buddylearner.databinding.ActivityLoginBinding;
import com.example.buddylearner.ui.signup.SignUpActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    //    la classe binding générée pour le fichier activity_sign_in.xml est ActivitySignInBinding
    private ActivityLoginBinding activityLoginBinding;
    private LogInViewModel logInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View activityView = activityLoginBinding.getRoot();
        setContentView(activityView);


//        récupérer la barre d'action et afficher le bouton haut <-
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            Log.getStackTraceString(npe);
        }


        // TODO: detect if the parent activity is sign in activity for special treatment --

        if(getIntent().getComponent().getClassName().equals("com.example.buddylearner.ui.login.LogInActivity")) {

            updateUiWithLoggedInUser(getIntent());

            // display a message for successful registration
            // Display signed up message in a snackbar on the current context
            String signedUp = getString(R.string.signed_up) + getIntent().getStringExtra("username");
            Snackbar.make(activityView, signedUp, Snackbar.LENGTH_LONG).show();

        }

        // TODO: -- detect if the parent activity is sign in activity for special treatment


        logInViewModel = new ViewModelProvider(this, new LogInViewModelFactory())
                .get(LogInViewModel.class);


        final EditText logInUsernameEditText = activityLoginBinding.logInUsernameEditText;
        final EditText logInPasswordEditText = activityLoginBinding.logInPasswordEditText;
        final Button logInButton = activityLoginBinding.logInButton;
//        final ProgressBar loadingProgressBar = activitySignUpBinding.loading;

        logInViewModel.getLogInUiState().observe(this, logInUiState -> {
            if (logInUiState == null) {
                return;
            }
            logInButton.setEnabled(logInUiState.isDataValid());
            if (logInUiState.getUsernameError() != null) {
                logInUsernameEditText.setError(getString(logInUiState.getUsernameError()));
            }
            if (logInUiState.getPasswordError() != null) {
                logInPasswordEditText.setError(getString(logInUiState.getPasswordError()));
            }
        });

        logInViewModel.getLogInResult().observe(this, logInResult -> {
            if (logInResult == null) {
                return;
            }
//                loadingProgressBar.setVisibility(View.GONE);
            if (logInResult.getError() != null) {
                showLogInFailed(logInResult.getError());
            }
            if (logInResult.getSuccess() != null) {
                updateUiWithUser(logInResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                logInViewModel.logInDataChanged(logInUsernameEditText.getText().toString(), logInPasswordEditText.getText().toString());
            }
        };
        logInUsernameEditText.addTextChangedListener(afterTextChangedListener);
        logInPasswordEditText.addTextChangedListener(afterTextChangedListener);
        logInPasswordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                logInViewModel.logIn(logInUsernameEditText.getText().toString(), logInPasswordEditText.getText().toString());
            }
            return false;
        });

        logInButton.setOnClickListener(v -> {
//                loadingProgressBar.setVisibility(View.VISIBLE);
            logInViewModel.logIn(logInUsernameEditText.getText().toString(), logInPasswordEditText.getText().toString());
        });

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getUserName();
        // TODO : initiate successful signed in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        // Display welcome message in a snackbar on the current context
        View view = activityLoginBinding.getRoot();
        Snackbar.make(view, welcome, Snackbar.LENGTH_LONG).show();
    }

    // TODO: mise à jour des champs de l'ui login

    private void updateUiWithLoggedInUser(Intent intent) {
        activityLoginBinding.logInUsernameEditText.setText(intent.getStringExtra("username"));
        activityLoginBinding.logInPasswordEditText.setText(intent.getStringExtra("password"));
    }

    // TODO: mise à jour des champs de l'ui login

    private void showLogInFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // navigate to the previous activity - it works !
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_FROM_BACKGROUND);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

}