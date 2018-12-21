package com.prabhat.brothers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventsActivity extends AppCompatActivity {

    TextView dateTextView, placeTextView, preDateTextView, prePlaceTextView, presentMembersTextView, absentMembersTextView;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar)findViewById(R.id.eventToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTextView = (TextView)findViewById(R.id.date);
        placeTextView = (TextView)findViewById(R.id.place);
        preDateTextView = (TextView)findViewById(R.id.preDate);
        prePlaceTextView = (TextView)findViewById(R.id.prePlace);
        presentMembersTextView = (TextView)findViewById(R.id.membersPresent);
        absentMembersTextView = (TextView)findViewById(R.id.membersAbsent);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dateTextView.setText((String)dataSnapshot.child("upcoming/date").getValue().toString());
                placeTextView.setText((String)dataSnapshot.child("upcoming/place").getValue().toString());
                preDateTextView.setText((String)dataSnapshot.child("previous/date").getValue().toString());
                prePlaceTextView.setText((String)dataSnapshot.child("previous/place").getValue().toString());
                presentMembersTextView.setText((String)dataSnapshot.child("previous/membersPresent").getValue().toString());
                absentMembersTextView.setText((String)dataSnapshot.child("previous/membersNotPresent").getValue().toString());

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
