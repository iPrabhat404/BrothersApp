package com.prabhat.brothers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentReminder extends Activity {

    String paymentReminderText;
    TextView paymentReminderTextView;
    Button okButton;

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_reminder);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        final String userID = mAuth.getCurrentUser().getUid();
        databaseReference = database.getReference("users/" + userID);
        paymentReminderTextView = (TextView)findViewById(R.id.payment_reminder_text);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userName = (String)dataSnapshot.child("userName").getValue();

                String monthsPending = (String)dataSnapshot.child("monthsPending").getValue();

                paymentReminderTextView.setText(
                        "Hi "+ userName +", Please, make your pending contributions on or before 15th of this month. " +
                                "You have pending contributions for "+ monthsPending +" month/months."
                );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }
}
