package com.prabhat.brothers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 3/30/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    List<PollQuestions> questionsList;
    Context context;

    private int[] colors = {
            R.color.green_chart,
            R.color.red_chart,
            R.color.blue_chart,
            R.color.purple_chart,
            R.color.primaryBlue,
    };

    public ResultAdapter(List<PollQuestions> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ResultAdapter.ViewHolder holder, final int position) {

        final PollQuestions pollQuestions = questionsList.get(position);

        holder.questionTextView.setText(pollQuestions.getQuestionTitle());

        //adding data to piechart
        List<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(Float.valueOf(pollQuestions.getOpt1Votes()), pollQuestions.getOpt1()));
        pieEntries.add(new PieEntry(Float.valueOf(pollQuestions.getOpt2Votes()), pollQuestions.getOpt2()));

        PieDataSet dataSet = new PieDataSet(pieEntries, pollQuestions.getQuestionTitle());
        dataSet.setColors(colors, context);
        dataSet.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(20.0f);
        data.setValueTypeface(Typeface.DEFAULT_BOLD);

        holder.chart.setData(data);
        holder.chart.setEntryLabelTextSize(18.0f);
        holder.chart.setUsePercentValues(true);
        holder.chart.setHoleRadius(50f);
        holder.chart.getLegend().setEnabled(false);
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setTransparentCircleRadius(55f);
        holder.chart.animateY(1000);
        holder.chart.invalidate();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserVotesActivity.class);
                intent.putExtra("position", position);

                view.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTextView;
        PieChart chart;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);

            questionTextView = (TextView) itemView.findViewById(R.id.question_title_result);
            chart = (PieChart) itemView.findViewById(R.id.chart);
            this.view = itemView;

        }
    }
}
