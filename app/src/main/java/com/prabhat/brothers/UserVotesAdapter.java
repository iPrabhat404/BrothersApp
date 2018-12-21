package com.prabhat.brothers;

import android.content.Context;
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

import java.util.List;

/**
 * Created by HP on 3/31/2018.
 */

public class UserVotesAdapter extends RecyclerView.Adapter<UserVotesAdapter.ViewHolder> {

    List<UsersVoted> usersVotedList;
    Context context;

    public UserVotesAdapter(List<UsersVoted> usersVotedList, Context context) {
        this.usersVotedList = usersVotedList;
        this.context = context;
    }

    @Override
    public UserVotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.voted_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UserVotesAdapter.ViewHolder holder, int position) {

        if (usersVotedList.size() ==  0){
            holder.textViewAnswer.setText("No Votes Yet");
            holder.textViewAnswer.setTextColor(context.getResources().getColor(R.color.red_chart));
            holder.textViewPerson.setText("");
            return;
        }

        final UsersVoted userVoted = usersVotedList.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userName = (String)dataSnapshot.child(userVoted.getUserId() +"/userName").getValue();

                holder.textViewPerson.setText(userName);
                holder.textViewAnswer.setText(userVoted.getUserVote());

                switch ((String)holder.textViewAnswer.getText()){

                    case "Yes":
                        holder.textViewAnswer.setTextColor(context.getResources().getColor(R.color.green_chart));
                        break;
                    case "No":
                        holder.textViewAnswer.setTextColor(context.getResources().getColor(R.color.red_chart));
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

    @Override
    public int getItemCount() {
        int size =  usersVotedList.size();

        if (size == 0){
            size++;
        }

        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPerson, textViewAnswer;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewPerson = (TextView)itemView.findViewById(R.id.textViewPerson);
            textViewAnswer = (TextView)itemView.findViewById(R.id.textViewAnswer);
            this.view = itemView;
        }
    }
}
