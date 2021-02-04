package com.example.mvc.screens.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mvc.screens.common.dialogs.promptdialog.PromptViewMvc;
import com.example.mvc.screens.common.dialogs.promptdialog.PromptViewMvcImpl;
import com.example.mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.mvc.screens.common.navdrawer.NavDrawerViewMvcImpl;
import com.example.mvc.screens.common.toolbar.ToolbarViewMvc;
import com.example.mvc.screens.questiondetails.QuestionDetailsViewMvc;
import com.example.mvc.screens.questiondetails.QuestionDetailsViewMvcImpl;
import com.example.mvc.screens.questionslist.questionslistitem.QuestionsListItemViewMvc;
import com.example.mvc.screens.questionslist.questionslistitem.QuestionsListItemViewMvcImpl;
import com.example.mvc.screens.questionslist.QuestionsListViewMvc;
import com.example.mvc.screens.questionslist.QuestionsListViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private final NavDrawerHelper mNavDrawerHelper;

    public ViewMvcFactory(LayoutInflater mLayoutInflater, NavDrawerHelper mNavDrawerHelper) {
        this.mLayoutInflater = mLayoutInflater;
        this.mNavDrawerHelper = mNavDrawerHelper;
    }

    public QuestionsListViewMvc getQuestionsListViewMvc(@Nullable ViewGroup parent){
        return new QuestionsListViewMvcImpl(mLayoutInflater, parent, this, mNavDrawerHelper);
    }

    public QuestionsListItemViewMvc getQuestionsListItemViewMvc(@Nullable ViewGroup parent){
        return new QuestionsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public QuestionDetailsViewMvc getQuestionDetailsViewMvc(@Nullable ViewGroup parent) {
        return new QuestionDetailsViewMvcImpl(mLayoutInflater, parent, this);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent){
        return new ToolbarViewMvc(mLayoutInflater, parent);
    }

    public NavDrawerViewMvc getNavDrawerViewMvc(@Nullable ViewGroup parent) {
        return new NavDrawerViewMvcImpl(mLayoutInflater, parent);
    }

    public PromptViewMvc getPromptViewMvc(@Nullable ViewGroup parent) {
        return new PromptViewMvcImpl(
                mLayoutInflater,
                parent
        );
    }
}
