package com.example.mvc.screens.questionslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvc.screens.common.controllers.BaseFragment;

public class QuestionsListFragment extends BaseFragment {

    public static QuestionsListFragment newInstance() {
        return new QuestionsListFragment();
    }

    private final String SAVED_STATE_CONTROLLER = "STATE_KEY";

    private QuestionsListController mQuestionsListController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: determine why passing container causes this to fail while vasily's code works
        QuestionsListViewMvc viewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(container);
        mQuestionsListController = getCompositionRoot().getQuestionsListController();

        if (savedInstanceState != null){
            restoreControllerState(savedInstanceState);
        }

        mQuestionsListController.bindView(viewMvc);
        return viewMvc.getRootView();
    }

    private void restoreControllerState(Bundle savedInstanceState) {
        mQuestionsListController.restoreSavedState(
                (QuestionsListController.SavedState)
                        savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mQuestionsListController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQuestionsListController.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_CONTROLLER, mQuestionsListController.getSavedState());
    }

}
