package com.example.reportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity {

    private TextView username_profil;
    private CircleImageView profil_img;

    private CardView update_profile,add_report,report_archive;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        username_profil = findViewById(R.id.username_profil);
        profil_img = findViewById(R.id.profil_img);
        update_profile = findViewById(R.id.manage_profil);
        add_report = findViewById(R.id.add_report_card);
        report_archive = findViewById(R.id.report_archive);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user!=null;
        String userID = user.getUid();

        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String username = snapshot.child("Username").getValue(String.class);
                    String image = snapshot.child("imageurl").getValue(String.class);

                    username_profil.setText(username.substring(0,1).toUpperCase()+username.substring(1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,UpdateProfil.class);
                startActivity(intent);
            }
        });

        add_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,AddReport.class);
                startActivity(intent);
            }
        });

    }
}