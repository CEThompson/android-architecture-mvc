package com.example.mvc.screens.questionslist;

import android.view.View;

import com.example.mvc.questions.Question;

import java.util.List;

// This interface documents the functionality of the MVC view
// Interfaces for MVC views are a very good investment into long term maintainability
public interface QuestionsListViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }
    
    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

    void bindQuestions(List<Question> questions);
}
