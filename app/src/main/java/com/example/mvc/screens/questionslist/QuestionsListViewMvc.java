package com.example.mvc.screens.questionslist;

import android.view.View;

import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.ViewMvc;

import java.util.List;

// NOTE: This class represents a "Decoupling Layer," a thin layer separating the UI layer (QuestionsListViewMvcImpl)
// From the Application Layer (QuestionsListActivity)

// This interface documents the functionality of the MVC view
// Interfaces for MVC views are a very good investment into long term maintainability
public interface QuestionsListViewMvc extends ViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    void bindQuestions(List<Question> questions);
}
