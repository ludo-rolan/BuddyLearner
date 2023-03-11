package com.example.buddylearner.ui.signup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddylearner.R;
import com.example.buddylearner.databinding.ActivitySignUpBinding;
import com.example.buddylearner.ui.login.LogInActivity;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

//    la classe binding générée pour le fichier activity_sign_in.xml est ActivitySignInBinding
    private ActivitySignUpBinding activitySignUpBinding;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View activityView = activitySignUpBinding.getRoot();
        setContentView(activityView);

//        Button signup = activitySignUpBinding.signUpButton;
//        signup.setOnClickListener(view -> startActivity(new Intent(this, LogInActivity.class)));


        signUpViewModel = new ViewModelProvider(this, new SignUpViewModelFactory())
                .get(SignUpViewModel.class);

        final EditText signUpUsernameEditText = activitySignUpBinding.signUpUsernameEditText;
        final EditText signUpEmailEditText = activitySignUpBinding.signUpEmailEditText;
        final EditText signUpPasswordEditText = activitySignUpBinding.signUpPasswordEditText;
        final Button signUpButton = activitySignUpBinding.signUpButton;
//        final ProgressBar loadingProgressBar = activitySignUpBinding.loading;

        signUpViewModel.getSignUpUiState().observe(this, new Observer<SignUpUiState>() {
            @Override
            public void onChanged(@Nullable SignUpUiState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                signUpButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    signUpUsernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getEmailError() != null) {
                    signUpEmailEditText.setError(getString(loginFormState.getEmailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    signUpPasswordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        signUpViewModel.getSignUpResult().observe(this, new Observer<SignUpResult>() {
            @Override
            public void onChanged(@Nullable SignUpResult signUpResult) {
                if (signUpResult == null) {
                    return;
                }
//                loadingProgressBar.setVisibility(View.GONE);
                if (signUpResult.getError() != null) {
                    showSignUpFailed(signUpResult.getError());
                }
                if (signUpResult.getSuccess() != null) {
                    updateUiWithUser(signUpResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
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
                signUpViewModel.signUpDataChanged(signUpUsernameEditText.getText().toString(),
                        signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString());
            }
        };
        signUpUsernameEditText.addTextChangedListener(afterTextChangedListener);
        signUpEmailEditText.addTextChangedListener(afterTextChangedListener);
        signUpPasswordEditText.addTextChangedListener(afterTextChangedListener);
        signUpPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signUpViewModel.signUp(signUpUsernameEditText.getText().toString(),
                            signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString());
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                signUpViewModel.signUp(signUpUsernameEditText.getText().toString(),
                        signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString());

                //TODO: implement logic for signed up user verification to change activity
                Intent logInActivity = new Intent(SignUpActivity.this, LogInActivity.class);
//                logInActivity.putExtra("currentUser", new SignedUpUserView(signUpUsernameEditText.getText().toString(),
//                        signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString()));
                logInActivity.putExtra("username", signUpUsernameEditText.getText().toString());
                logInActivity.putExtra("password", signUpUsernameEditText.getText().toString());
                startActivity(logInActivity);
            }
        });

    }

    private void updateUiWithUser(SignedUpUserView model) {
        String signedUp = getString(R.string.signed_up) + model.getUsername();
        // TODO : initiate successful signed in experience
//        Toast.makeText(getApplicationContext(), signedUp, Toast.LENGTH_LONG).show();

        // Display signed up message in a snackbar on the current context
//        View view = activitySignUpBinding.getRoot();
//        Snackbar.make(view, signedUp, Snackbar.LENGTH_LONG).show();
    }

    private void showSignUpFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}
