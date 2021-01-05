package com.example.mvc.screens.questionslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mvc.R;
import com.example.mvc.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsRecyclerAdapter
        extends RecyclerView.Adapter<QuestionsRecyclerAdapter.ViewHolder>
        implements QuestionsListItemViewMvc.Listener {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final QuestionsListItemViewMvc mViewMvc;
        public ViewHolder(QuestionsListItemViewMvc viewMvc){
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }
    }

    private final LayoutInflater mInflater;
    private final Listener mListener;
    private List<Question> mQuestions = new ArrayList<>();

    public QuestionsRecyclerAdapter(LayoutInflater inflater, Listener listener){
        mInflater = inflater;
        mListener = listener;
    }

    public void bindQuestions(List<Question> questions) {
        mQuestions = new ArrayList<>(questions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        QuestionsListItemViewMvc viewMvc = new QuestionsListItemViewMvcImpl(mInflater, parent);
        viewMvc.registerListener(this);
        return new ViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mViewMvc.bindQuestion(mQuestions.get(i));
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    @Override
    public void onQuestionClicked(Question question) {
        mListener.onQuestionClicked(question);
    }
}
