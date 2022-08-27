package com.example.forher.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forher.R;
import com.example.forher.data.TestItem;

import java.util.ArrayList;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.AdapterViewHolder> {
    ArrayList<TestItem> getQuestion;

    public TestAdapter(ArrayList<TestItem> getQuestion) {
        this.getQuestion = getQuestion;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, null, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        TestItem testItem = getQuestion.get(position);
        holder.question.setText(testItem.getTestQuestion());
        holder.answerA.setText(testItem.getAnswerA());
        holder.answerB.setText(testItem.getAnswerB());
    }

    @Override
    public int getItemCount() {
        return getQuestion.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        CheckBox answerA, answerB;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.text_question);
            answerA = itemView.findViewById(R.id.YES);
            answerB = itemView.findViewById(R.id.NO);

        }
    }
}
