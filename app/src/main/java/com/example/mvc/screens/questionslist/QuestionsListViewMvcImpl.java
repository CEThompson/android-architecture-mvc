package com.example.mvc.screens.questionslist;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import com.example.mvc.R;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.mvc.screens.common.toolbar.ToolbarViewMvc;
import com.example.mvc.screens.common.ViewMvcFactory;
import com.example.mvc.screens.common.views.BaseObservableViewMvc;

import java.util.List;

// NOTE: This class represents the UI Layer
public class QuestionsListViewMvcImpl
        extends BaseObservableViewMvc<QuestionsListViewMvc.Listener>
        implements QuestionsRecyclerAdapter.Listener, QuestionsListViewMvc {

    private final RecyclerView mRecyclerQuestions;
    private final QuestionsRecyclerAdapter mAdapter;
    private final ProgressBar mProgressBar;

    private final ToolbarViewMvc mToolbarViewMvc;
    private final Toolbar mToolbar;

    private final NavDrawerHelper mNavDrawerHelper;

    public QuestionsListViewMvcImpl(LayoutInflater inflater,
                                    @Nullable ViewGroup parent,
                                    ViewMvcFactory viewMvcFactory,
                                    NavDrawerHelper navDrawerHelper) {
        this.mNavDrawerHelper = navDrawerHelper;
        setRootView(inflater.inflate(R.layout.layout_questions_list, parent, false));

        mRecyclerQuestions = findViewById(R.id.recycler_questions);
        mRecyclerQuestions.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new QuestionsRecyclerAdapter(this, viewMvcFactory);
        mRecyclerQuestions.setAdapter(mAdapter);
        mProgressBar = findViewById(R.id.progress);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        initToolbar();

    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getContext().getString(R.string.questions_list_screen_title));
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.enableMenuButtonAndListen(new ToolbarViewMvc.MenuClickListener() {
            @Override
            public void onMenuClicked() {
                mNavDrawerHelper.openDrawer();
            }
        });
    }


    @Override
    public void onQuestionClicked(Question question) {
        for (Listener listener : getListeners()) {
            listener.onQuestionClicked(question);
        }
    }

    @Override
    public void bindQuestions(List<Question> questions) {
        mAdapter.bindQuestions(questions);
    }


    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
