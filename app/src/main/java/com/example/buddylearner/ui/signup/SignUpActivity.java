package com.example.buddylearner.ui.signup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddylearner.R;
import com.example.buddylearner.data.enums.UserRole;
import com.example.buddylearner.databinding.ActivitySignUpBinding;
import com.example.buddylearner.ui.login.LogInActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SignUpActivity extends AppCompatActivity {

//    la classe binding générée pour le fichier activity_sign_in.xml est ActivitySignInBinding
    private ActivitySignUpBinding activitySignUpBinding;
    private SignUpViewModel signUpViewModel;
    EditText signUpUsernameEditText;
    EditText signUpEmailEditText;
    EditText signUpPasswordEditText;
    Button signUpButton;
    AutoCompleteTextView signUpUserRoleAutocomplete;

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

        signUpUsernameEditText = activitySignUpBinding.signUpUsernameEditText;
        signUpEmailEditText = activitySignUpBinding.signUpEmailEditText;
        signUpPasswordEditText = activitySignUpBinding.signUpPasswordEditText;
        signUpButton = activitySignUpBinding.signUpButton;
        signUpUserRoleAutocomplete = activitySignUpBinding.signUpUserRoleAutocomplete;

        UserRole userRole;

        List menuOptions = new ArrayList<String>(){
            {
                add("learner");
                add("tutor");
            }
        };


        // create a ArrayList String type
        // and Initialize an ArrayList with asList()
        // it work !
//        ArrayList<String> gfg = new ArrayList<String>(
//                Arrays.asList("Geeks",
//                        "for",
//                        "Geeks"));


        // create a ArrayList String type
        // and Initialize an ArrayList with List.of()
        // require min java 9
//        List<String> gfg = new ArrayList<>(
//                List.of("Geeks",
//                        "for",
//                        "Geeks"));


        // create a stream of elements using Stream.of()
        // method collect the stream elements into an
        // ArrayList using the collect() method and
        // Collectors.toCollection() method
        // require api minsdk 24
//        ArrayList<String> list
//                = Stream.of("Geeks", "For", "Geeks")
//                .collect(Collectors.toCollection(
//                        ArrayList::new));



        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.role_item, menuOptions);
        signUpUserRoleAutocomplete.setAdapter(adapter);


//        // récupérer l'élément "exposed dropdown menu"
//        MaterialAutoCompleteTextView exposedDropdownMenu = findViewById(R.id.exposed_dropdown_menu);
//
//        // récupérer les éléments du menu
//                String[] items = getResources().getStringArray(R.array.dropdown_items);
//
//        // sélectionner l'élément à afficher
//                String selectedItem = "Élément à sélectionner";
//
//        // trouver l'index de l'élément à afficher dans la liste
//                int selectedIndex = Arrays.asList(items).indexOf(selectedItem);
//
//        // sélectionner l'élément dans le menu
//                exposedDropdownMenu.setSelectedItem(selectedIndex);




//        final ProgressBar loadingProgressBar = activitySignUpBinding.loading;

        signUpViewModel.getSignUpUiState().observe(this, new Observer<SignUpUiState>() {
            @Override
            public void onChanged(@Nullable SignUpUiState signUpUiState) {
                if (signUpUiState == null) {
                    return;
                }
                signUpButton.setEnabled(signUpUiState.isDataValid());
                if (signUpUiState.getUsernameError() != null) {
                    signUpUsernameEditText.setError(getString(signUpUiState.getUsernameError()));
                }
                if (signUpUiState.getEmailError() != null) {
                    signUpEmailEditText.setError(getString(signUpUiState.getEmailError()));
                }
                if (signUpUiState.getPasswordError() != null) {
                    signUpPasswordEditText.setError(getString(signUpUiState.getPasswordError()));
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
                    UserRole role = signUpUserRoleAutocomplete.getText().toString().equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner : UserRole.tutor;

                    signUpViewModel.signUp(
                            signUpUsernameEditText.getText().toString(),
                            signUpEmailEditText.getText().toString(),
                            signUpPasswordEditText.getText().toString(),
                            role
                    );
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRole role = signUpUserRoleAutocomplete.getText().toString().equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner : UserRole.tutor;

//                loadingProgressBar.setVisibility(View.VISIBLE);
                signUpViewModel.signUp(
                        signUpUsernameEditText.getText().toString(),
                        signUpEmailEditText.getText().toString(),
                        signUpPasswordEditText.getText().toString(),
                        role
                );

                //TODO: implement logic for signed up user verification to change activity
                Intent logInActivity = new Intent(SignUpActivity.this, LogInActivity.class);
//                logInActivity.putExtra("currentUser", new SignedUpUserView(signUpUsernameEditText.getText().toString(),
//                        signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString()));
                logInActivity.putExtra("username", signUpUsernameEditText.getText().toString());
                logInActivity.putExtra("password", signUpPasswordEditText.getText().toString());
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

    public void showDropdown(ArrayAdapter<String> adapter, int position) {
        if(!TextUtils.isEmpty(this.getText(position).toString())) {
            if(adapter != null) {
                adapter.getFilter().filter(null);
            }
        }
    }

}
