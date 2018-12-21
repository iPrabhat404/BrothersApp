package com.prabhat.brothers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.lang.Long;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView editProfileButton, viewProfileImageButton;
    private TextView textViewName, textViewAbout, textViewEmail, textViewPhone, textViewSkill;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String userId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfileButton = findViewById(R.id.edit_profile);
        viewProfileImageButton = findViewById(R.id.profile_image);
        textViewName = findViewById(R.id.user_name);
        textViewAbout = findViewById(R.id.about);
        textViewEmail = findViewById(R.id.email);
        textViewPhone = findViewById(R.id.phone);
        textViewSkill = findViewById(R.id.skills);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Profile Details");
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("users/"+userId);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    showProfileDetails(dataSnapshot);
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editProfileButton.setOnClickListener(this);
        viewProfileImageButton.setOnClickListener(this);
    }

    private void showProfileDetails(DataSnapshot dataSnapshot) {


        Users uInfo = new Users();
        uInfo.setUserName(dataSnapshot.getValue(Users.class).getUserName());
        uInfo.setUserEmail(dataSnapshot.getValue(Users.class).getUserEmail());
        uInfo.setMobileNumber(dataSnapshot.getValue(Users.class).getMobileNumber());
        uInfo.setUserSkills(dataSnapshot.getValue(Users.class).getUserSkills());
        uInfo.setUserBio(dataSnapshot.getValue(Users.class).getUserBio());
        uInfo.setProfileImageUrl(dataSnapshot.getValue(Users.class).getProfileImageUrl());


        //appending the details to the views

        if (uInfo.getProfileImageUrl() != null){
            GlideApp.with(getApplicationContext()).load(uInfo.getProfileImageUrl()).fitCenter().into(viewProfileImageButton);
        }

        if (uInfo.getUserName() != null){
            textViewName.setText(uInfo.getUserName());
        }

        if (uInfo.getUserBio() != null){
            textViewAbout.setText(uInfo.getUserBio());
        }

        if (uInfo.getUserEmail() != null){
            textViewEmail.setText(uInfo.getUserEmail());
        }

        if (uInfo.getMobileNumber() != null){
            textViewPhone.setText(uInfo.getMobileNumber());
        }

        if (uInfo.getUserSkills() != null){
            textViewSkill.setText(uInfo.getUserSkills());
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.edit_profile:
                startActivity(new Intent(getApplicationContext(), ProfileEditActivity.class));
                finish();
                break;

            case R.id.profile_image:
                Toast.makeText(getApplicationContext(), "This Feature is Coming Soon...", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(getApplicationContext(), homeActivity.class));
        finish();
    }
}

