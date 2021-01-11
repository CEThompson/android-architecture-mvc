package com.example.mvc.screens.questiondetails;


import com.example.mvc.questions.QuestionDetails;
import com.example.mvc.screens.common.navdrawer.DrawerItems;
import com.example.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.mvc.screens.common.views.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener>, NavDrawerViewMvc {

    public interface Listener {
        void onNavigateUpClicked();
        void onDrawerItemClicked(DrawerItems items);
    }

    void bindQuestion(QuestionDetails question);

    void showProgressIndication();

    void hideProgressIndication();
}
