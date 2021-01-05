package com.example.mvc.screens.questionslist;

import android.view.View;

import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.ViewMvc;

public interface QuestionsListItemViewMvc extends ViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);

    void bindQuestion(Question question);
}
