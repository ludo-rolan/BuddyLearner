package com.example.buddylearner.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.buddylearner.databinding.ActivitySignUpBinding;
import com.example.buddylearner.ui.login.LogInActivity;

public class SignUpActivity extends AppCompatActivity {

//    la classe binding générée pour le fichier activity_sign_in.xml est ActivitySignInBinding
    private ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View activityView = activitySignUpBinding.getRoot();
        setContentView(activityView);

        Button signup = activitySignUpBinding.signUpButton;
        signup.setOnClickListener(view -> startActivity(new Intent(this, LogInActivity.class)));
    }
}