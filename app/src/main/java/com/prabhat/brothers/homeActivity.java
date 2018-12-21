package com.prabhat.brothers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileButton, memberButton, pollButton, eventButton;
    private FirebaseAuth firebaseAuth;
    private TextView logoutButton;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileButton = (ImageView) findViewById(R.id.profile_button);
        memberButton = (ImageView) findViewById(R.id.member_button);
        pollButton = (ImageView) findViewById(R.id.poll_button);
        eventButton = (ImageView) findViewById(R.id.event_button);
        logoutButton = (TextView) findViewById(R.id.logout_button);

        firebaseAuth = FirebaseAuth.getInstance();

        profileButton.setOnClickListener(this);
        memberButton.setOnClickListener(this);
        pollButton.setOnClickListener(this);
        eventButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.profile_button:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;

            case R.id.member_button:
                startActivity(new Intent(getApplicationContext(), MembersActivity.class));
                break;

            case R.id.poll_button:
                //Toast.makeText(getApplicationContext(), "Poll feature is Coming Soon.", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), PollActivity.class));

                startActivity(new Intent(getApplicationContext(), PollActivity.class));
                break;

            case R.id.event_button:

                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                //Toast.makeText(getApplicationContext(), "Events feature is Coming Soon.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_button:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;

            default:
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() == null){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
        }else {

            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            final String userID = firebaseAuth.getCurrentUser().getUid();
            final DatabaseReference databaseReference = mDatabase.getReference("users/" + userID + "/paymentStatus");


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String paymentStatus = (String) dataSnapshot.getValue();
                    if (paymentStatus.equals("Pending")) {

                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), PaymentReminder.class));
                           // Toast.makeText(getApplicationContext(), "Payment is Pending", Toast.LENGTH_LONG).show();
                            i++;
                        }
                    }/* else {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "Payment Status other than No", Toast.LENGTH_LONG).show();
                        }
                    } */
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
