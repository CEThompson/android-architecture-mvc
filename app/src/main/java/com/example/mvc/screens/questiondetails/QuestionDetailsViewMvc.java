package com.example.mvc.screens.questiondetails;


import com.example.mvc.questions.QuestionDetails;
import com.example.mvc.screens.common.ViewMvc;

public interface QuestionDetailsViewMvc extends ViewMvc {

    void bindQuestion(QuestionDetails question);

    void showProgressIndication();

    void hideProgressIndication();
}
