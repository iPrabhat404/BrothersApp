package com.prabhat.brothers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PollFragment extends Fragment {

    private RecyclerView recyclerViewPolls;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;

    List<PollQuestions> questionsList = new ArrayList<>();

    public PollFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_poll, container, false);

        recyclerViewPolls = (RecyclerView) rootView.findViewById(R.id.recyclerViewPolls);
        recyclerViewPolls.setHasFixedSize(true);
        recyclerViewPolls.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("polls");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long l = dataSnapshot.getChildrenCount();
                questionsList.clear();
                for (int i = 0; i < l; i++){

                    PollQuestions questions = new PollQuestions();
                    questions.setQuestionTitle((String) dataSnapshot.child(i + "/question").getValue());
                    questions.setOpt1((String)dataSnapshot.child(i + "/option1").getValue());
                    questions.setOpt2((String)dataSnapshot.child(i + "/option2").getValue());
                    questions.setOpt1Votes((String)dataSnapshot.child(i + "/option1Votes").getValue());
                    questions.setOpt2Votes((String)dataSnapshot.child(i + "/option2Votes").getValue());

                    questionsList.add(questions);

                }

                adapter = new PollAdapter(questionsList, getContext());
                recyclerViewPolls.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
