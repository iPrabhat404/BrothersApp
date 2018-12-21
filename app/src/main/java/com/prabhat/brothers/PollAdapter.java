package com.prabhat.brothers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HP on 3/30/2018.
 */

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    List<PollQuestions> questionsList;
    Context context;

    public PollAdapter(List<PollQuestions> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final PollQuestions questions = questionsList.get(position);
       // Toast.makeText(context, "Question is"+questions.getQuestionTitle(), Toast.LENGTH_SHORT).show();
        holder.questionTitleTextView.setText(questions.getQuestionTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), VoteActivity.class);
                intent.putExtra("questionTitle", questions.getQuestionTitle());
                intent.putExtra("position", position);

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        int itemCount = questionsList.size();
        return itemCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView questionTitleTextView;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);

            questionTitleTextView = (TextView) itemView.findViewById(R.id.question_title);
            this.view = itemView;
        }
    }
}
