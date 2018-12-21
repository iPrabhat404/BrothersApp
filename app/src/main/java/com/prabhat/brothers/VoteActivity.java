package com.prabhat.brothers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class VoteActivity extends Activity implements View.OnClickListener{

    ProgressDialog progressDialog;
    TextView textView;
    Button btn1, btn2;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();

    PollQuestions pollQuestions = new PollQuestions();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        textView = (TextView)findViewById(R.id.question_title);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);

        Bundle bundle = getIntent().getExtras();
        String position = String.valueOf(bundle.getInt("position"));
        String title = bundle.getString("questionTitle");

        textView.setText(title);
        DatabaseReference databaseReference = database.getReference("polls/"+ position);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                btn1.setText((String)dataSnapshot.child("option1").getValue());
                btn2.setText((String)dataSnapshot.child("option2").getValue());

                pollQuestions.setOpt1Votes((String)dataSnapshot.child("option1Votes").getValue().toString());
                pollQuestions.setOpt2Votes((String)dataSnapshot.child("option2Votes").getValue().toString());

                if (dataSnapshot.hasChild("voted/"+userID+"/option")){
                    String option = (String)dataSnapshot.child("voted/"+ userID +"/option").getValue();
                    switch (option){
                        case "option1":
                            btn1.setBackgroundColor(getResources().getColor(R.color.primaryBlue));
                            btn1.setTextColor(getResources().getColor(R.color.lightPink));
                            btn1.setTextSize(18f);
                            btn1.setTypeface(null, Typeface.BOLD);
                            break;

                        case "option2":
                            btn2.setBackgroundColor(getResources().getColor(R.color.primaryBlue));
                            btn2.setTextColor(getResources().getColor(R.color.lightPink));
                            btn2.setTextSize(18f);
                            btn2.setTypeface(null, Typeface.BOLD);
                            break;

                        default:
                            //some code
                            break;
                    }
                }


                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } */

    @Override
    public void onClick(final View view) {

        Bundle bundle = getIntent().getExtras();
        final String position = String.valueOf(bundle.getInt("position"));
        //final DatabaseReference myRef = database.getReference("polls/"+ position);
        final DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("polls/"+ position +"/voted").hasChild(userID)){
                    Toast.makeText(getApplicationContext(), "You have already voted for this poll.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Float voteWeight = (Float) Float.valueOf(dataSnapshot.child("users/"+ userID +"/voteWeight").getValue().toString());
                switch (view.getId()){

                    case R.id.button1:
                        Float votes1 = Float.valueOf(pollQuestions.getOpt1Votes());
                        votes1 = voteWeight + votes1;
                        String optionVoted1 = (String)dataSnapshot.child("polls/"+ position +"/option1").getValue();
                        myRef.child("polls/"+ position +"/option1Votes").setValue(String.valueOf(votes1));
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/vote").setValue(optionVoted1);
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/userID").setValue(userID);
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/option").setValue("option1");
                        finish();
                        break;

                    case R.id.button2:
                        Float votes2 = Float.valueOf(pollQuestions.getOpt2Votes());
                        votes2 = voteWeight + votes2;
                        String optionVoted2 = (String)dataSnapshot.child("polls/"+ position +"/option2").getValue();
                        myRef.child("polls/"+ position +"/option2Votes").setValue(String.valueOf(votes2));
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/vote").setValue(optionVoted2);
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/userID").setValue(userID);
                        myRef.child("polls/"+ position +"/voted/"+ userID +"/option").setValue("option2");
                        finish();
                        break;

                    default:
                        //some code
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

