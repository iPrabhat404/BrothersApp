package com.prabhat.brothers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CHOOSE_IMAGE = 2601;
    private ImageButton submitUserDetails;
    private CircleImageView setProfileImage;
    private EditText editTextName, editTextMobile, editTextAbout, editTextSkills;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    Uri uriProfileImage;

    String profileImageUrl = null;

    //firebase objects
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceEdit, databaseReferenceShow, databaseReference;
    private String userID;
    private String userEmail;

    String[] months = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };
    Users uInfo = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        submitUserDetails = findViewById(R.id.done_button);
        setProfileImage = findViewById(R.id.set_profile_image);
        editTextName = findViewById(R.id.enter_name);
        editTextMobile = findViewById(R.id.enter_phone);
        editTextAbout = findViewById(R.id.enter_bio);
        editTextSkills = findViewById(R.id.enter_skills);

        progressBar = findViewById(R.id.progress_bar);
        progressDialog = new  ProgressDialog(this);
        progressDialog.setMessage("Fetching Profile Details");
        progressDialog.show();

        //getting user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceEdit = firebaseDatabase.getReference("users");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        userEmail = user.getEmail();
        databaseReferenceShow = firebaseDatabase.getReference("users/"+userID);
        databaseReference = firebaseDatabase.getReference();

        submitUserDetails.setOnClickListener(this);
        setProfileImage.setOnClickListener(this);

        databaseReferenceShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showDetails(dataSnapshot);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showDetails(DataSnapshot dataSnapshot) {

        uInfo.setUserName(dataSnapshot.getValue(Users.class).getUserName());
        uInfo.setMobileNumber(dataSnapshot.getValue(Users.class).getMobileNumber());
        uInfo.setUserSkills(dataSnapshot.getValue(Users.class).getUserSkills());
        uInfo.setUserBio(dataSnapshot.getValue(Users.class).getUserBio());
        uInfo.setProfileImageUrl(dataSnapshot.getValue(Users.class).getProfileImageUrl());


        //appending the details to the views

        if (uInfo.getProfileImageUrl() != null){
            GlideApp.with(getApplicationContext()).load(uInfo.getProfileImageUrl()).fitCenter().into(setProfileImage);
        }

        if (uInfo.getUserName() != null){
            editTextName.setText(uInfo.getUserName());
        }

        if (uInfo.getUserBio() != null){
            editTextAbout.setText(uInfo.getUserBio());
        }

        if (uInfo.getMobileNumber() != null){
            editTextMobile.setText(uInfo.getMobileNumber());
        }

        if (uInfo.getUserSkills() != null){
            editTextSkills.setText(uInfo.getUserSkills());
        }

    }


    @Override
    public void onClick(View view) {

        String userName = editTextName.getText().toString().trim();
        String mobileNumber = editTextMobile.getText().toString().trim();
        String about = editTextAbout.getText().toString().trim();
        String skills = editTextSkills.getText().toString().trim();

        switch (view.getId()){

            case R.id.done_button:
                saveUserDetails();
                break;

            case R.id.set_profile_image:
                showImageChooser();
                break;

            default:
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){

            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,25,out);
                byte[] imageByteArray = out.toByteArray();
                setProfileImage.setImageBitmap(bitmap);
                uploadProfileImage(imageByteArray);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void uploadProfileImage(byte[] data) {

        final StorageReference profileImageRef = FirebaseStorage.getInstance()
                .getReference("profilepics/"+System.currentTimeMillis()+".jpg");

        if (uriProfileImage != null){

            progressBar.setVisibility(View.VISIBLE);

            profileImageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressBar.setVisibility(View.GONE);
                    toastMessage("Profile Image Updated.");
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.GONE);
                            toastMessage(e.getMessage());

                        }
                    });
        }

    }

    private void saveUserDetails() {



        //user.setCurrentMonth(currentMonth);

        databaseReferenceShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = editTextName.getText().toString().trim();
                String mobileNumber = editTextMobile.getText().toString().trim();
                String userBio = editTextAbout.getText().toString().trim();
                String userSkills = editTextSkills.getText().toString().trim();

                //testing for valid input

                if (name.isEmpty()){
                    editTextName.setError("Name is Required");
                    editTextName.requestFocus();
                    return;
                }

                if (mobileNumber == null){
                    editTextMobile.setError("Name is Required");
                    editTextMobile.requestFocus();
                    return;
                }

                if (userSkills.isEmpty()){
                    editTextSkills.setError("Name is Required");
                    editTextSkills.requestFocus();
                    return;
                }

                if (userBio.isEmpty()){
                    editTextAbout.setError("Name is Required");
                    editTextAbout.requestFocus();
                    return;
                }


                //creating user details

                if (profileImageUrl != null){
                    uInfo.setProfileImageUrl(profileImageUrl);
                }
                Users user = new Users(name, mobileNumber,userEmail, userBio, userSkills, uInfo.getProfileImageUrl());
                //databaseReference.child(userID);

                //current date and month
                Calendar c = Calendar.getInstance();
                int monthNum = c.get(Calendar.MONTH);
                int yearNum = c.get(Calendar.YEAR);
                String month = months[monthNum];
                final String currentMonth = month + ", " + yearNum;

                user.setCurrentMonth(currentMonth);

                if(dataSnapshot.hasChild("paymentStatus")) {
                    user.setPaymentStatus((String) dataSnapshot.child("paymentStatus").getValue().toString());
                    user.setPaymentAmount((String) dataSnapshot.child("paymentAmount").getValue().toString());
                    user.setPaymentAmountTotal((String) dataSnapshot.child("paymentAmountTotal").getValue().toString());
                    user.setMonthsPending((String) dataSnapshot.child("monthsPending").getValue().toString());
                    user.setVoteWeight((String) dataSnapshot.child("voteWeight").getValue().toString());
                }else{
                    user.setPaymentStatus("Not Updated");
                    user.setPaymentAmount("Not Updated");
                    user.setMonthsPending("Not Updated");
                    user.setPaymentAmountTotal("Not Updated");
                    user.setVoteWeight("0");
                }

                databaseReferenceEdit.child(userID).setValue(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //setting user details to database

        toastMessage("Profile Updated.");
        startActivity(new Intent(getApplicationContext(), homeActivity.class));
        finish();

    }

    private void toastMessage(String s) {

        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();

    }

    private void showImageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
    }
}
