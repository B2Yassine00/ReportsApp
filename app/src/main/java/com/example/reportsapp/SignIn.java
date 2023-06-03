package com.example.reportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private Button returnbutton,signinbutton;
    private TextView signuplink,textemail,textpassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        returnbutton = findViewById(R.id.returnbutton2);
        signuplink = findViewById(R.id.signuplink);
        auth = FirebaseAuth.getInstance();
        textemail = findViewById(R.id.email);
        textpassword = findViewById(R.id.password);
        signinbutton = findViewById(R.id.sign_in_button);

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,MainActivity.class);
                startActivity(intent);
            }
        });
        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email= String.valueOf(textemail.getText());
                password = String.valueOf(textpassword.getText());

                if (TextUtils.isEmpty(email)){
                    textemail.setError("Email is required");
                    textemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textemail.setError("Please enter a valid email");
                    textemail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    textpassword.setError("password is required");
                    textpassword.requestFocus();
                    return;
                }
                if (password.length() < 8){
                    textpassword.setError("Min password length is 8 characters !");
                    textpassword.requestFocus();
                    return;
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        if(user.isEmailVerified()) {
                            //redirect to user profile
                            startActivity(new Intent(SignIn.this, HomePage.class));
                        }else{
                            user.sendEmailVerification();
                            Toast.makeText(SignIn.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(SignIn.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}