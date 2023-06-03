package com.example.reportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    Pattern pattern = Pattern.compile("^\\d{10}$");

    private EditText textusername,textphone,textemail,textpassword,textrepassword;
    private Button returnbutton;
    private Button sign_up;

    private FirebaseAuth mAuth;

    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        returnbutton = findViewById(R.id.returnbutton);
        sign_up = findViewById(R.id.sign_up);
        textusername = findViewById(R.id.username);
        textemail = findViewById(R.id.email);
        textphone = findViewById(R.id.phone);
        textpassword = findViewById(R.id.password);
        textrepassword = findViewById(R.id.repassword);

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = textusername.getText().toString().trim();
                String email = textemail.getText().toString().trim();
                String phonenumber = textphone.getText().toString().trim();
                String password = textpassword.getText().toString().trim();
                String repassword = textrepassword.getText().toString().trim();

                if(username.isEmpty()){
                    textusername.setError("Username is required");
                    textusername.requestFocus();
                }
                if(email.isEmpty()){
                    textemail.setError("Email is required");
                    textemail.requestFocus();
                    return;
                }
                if (!pattern.matcher(phonenumber).matches()){
                    textphone.setError("Invalid phone number");
                    textphone.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textemail.setError("Please enter a valid email");
                    textemail.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    textpassword.setError("Password is required !");
                    textpassword.requestFocus();
                    return;
                }
                if (password.length() < 8){
                    textpassword.setError("Min password length is 8 characters !");
                    textpassword.requestFocus();
                    return;
                }
                if(!repassword.equals(password)){
                    textrepassword.setError("Password is incorrect");
                    textrepassword.requestFocus();
                }else{
                    mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(authResult -> {
                        HashMap<String , Object> map = new HashMap<>();
                        map.put("Username" , username);
                        map.put("Email", email);
                        map.put("phone",phonenumber);
                        map.put("id" , Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                        map.put("imageurl" , "default");
                        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification();
                            }

                        });
                        Intent intent = new Intent(SignUp.this , MainActivity.class);
                        Toast.makeText(getApplicationContext(),"A verification E-mail was sent",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });

    }
}