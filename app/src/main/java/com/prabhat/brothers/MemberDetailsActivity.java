package com.prabhat.brothers;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberDetailsActivity extends AppCompatActivity {

    TextView nameTextView, aboutTextView, emailTextView, mobileTextView, skillsTextView;
    TextView paymentStatusTextview, monthsPendingTextView, amountPaidCurrentTextView, amountPaidTotalTextView;
    String name, about, email, mobile, skills, paymentStatus, monthsPending, amountPaidCurrent, amountPaidTotal;
    CircleImageView memberDP;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    FirebaseAuth firebaseAuth;

    String userId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar)findViewById(R.id.memberToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        position = bundle.getInt("position");

        nameTextView = (TextView)findViewById(R.id.member_name);
        aboutTextView = (TextView)findViewById(R.id.member_about);
        emailTextView = (TextView)findViewById(R.id.email);
        mobileTextView = (TextView)findViewById(R.id.memberMobile);
        skillsTextView = (TextView)findViewById(R.id.skillset);
        paymentStatusTextview = (TextView)findViewById(R.id.paymentStatus);
        monthsPendingTextView = (TextView)findViewById(R.id.monthsPending);
        amountPaidCurrentTextView = (TextView)findViewById(R.id.amountPaidCurrent);
        amountPaidTotalTextView = (TextView)findViewById(R.id.amountPaidTotal);
        memberDP = (CircleImageView)findViewById(R.id.memberDP);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               MemberDetails member = dataSnapshot.child(userId).getValue(MemberDetails.class);
               if (dataSnapshot.child(userId).child("profileImageUrl").getValue() != null) {
                   member.setProfileImageUrl((String) dataSnapshot.child(userId).child("profileImageUrl").getValue().toString());
               }else {
                   Toast.makeText(getApplicationContext(), "You don't have a Profile Image", Toast.LENGTH_LONG).show();
               }

               if (member.getPaymentStatus().equals("Pending")){
                   paymentStatusTextview.setTextColor(Color.RED);
                   monthsPendingTextView.setTextColor(Color.RED);
               }else {
                   paymentStatusTextview.setTextColor(Color.GREEN);
                   monthsPendingTextView.setTextColor(Color.GREEN);
               }

               if (member.getProfileImageUrl() != null) {
                   GlideApp.with(getApplicationContext())
                           .load(member.getProfileImageUrl())
                           .fitCenter()
                           .into(memberDP);
               }

                nameTextView.setText(member.getUserName());
                aboutTextView.setText(member.getUserBio());
                emailTextView.setText(member.getUserEmail());
                mobileTextView.setText(member.getMobileNumber());
                skillsTextView.setText(member.getUserSkills());
                paymentStatusTextview.setText(member.getPaymentStatus());
                monthsPendingTextView.setText(member.getMonthsPending());
                amountPaidCurrentTextView.setText(member.getPaymentAmount());
                amountPaidTotalTextView.setText(member.getPaymentAmountTotal());

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
