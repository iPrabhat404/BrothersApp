package com.prabhat.brothers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ResultFragment extends Fragment {

    private RecyclerView recyclerViewResults;
    private RecyclerView.Adapter myAdapter;
    ProgressDialog progressDialog;

    List<PollQuestions> resultsList = new ArrayList<>();


    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        recyclerViewResults = (RecyclerView)rootView.findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setHasFixedSize(true);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("polls");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long l = dataSnapshot.getChildrenCount();
                resultsList.clear();

                for (int i = 0; i < l; i++){

                    PollQuestions results = new PollQuestions();
                    results.setQuestionTitle((String) dataSnapshot.child(i + "/question").getValue());
                    results.setOpt1((String)dataSnapshot.child(i + "/option1").getValue());
                    results.setOpt2((String)dataSnapshot.child(i + "/option2").getValue());
                    results.setOpt1Votes((String)dataSnapshot.child(i + "/option1Votes").getValue().toString());
                    results.setOpt2Votes((String)dataSnapshot.child(i + "/option2Votes").getValue().toString());

                    resultsList.add(results);
                }

                myAdapter = new ResultAdapter(resultsList, getContext());
                recyclerViewResults.setAdapter(myAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
