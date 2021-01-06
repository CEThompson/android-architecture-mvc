package com.example.mvc.screens.questionslist;

import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.ObservableViewMvc;

import java.util.List;

// NOTE: This class represents a "Decoupling Layer," a thin layer separating the UI layer (QuestionsListViewMvcImpl)
// From the Application Layer (QuestionsListActivity)

// This interface documents the functionality of the MVC view
// Interfaces for MVC views are a very good investment into long term maintainability
public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    void showProgressIndication();
    void hideProgressIndication();

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);
}
