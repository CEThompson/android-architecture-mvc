package com.example.mvc.screens.common.dialogs.promptdialog;

import com.example.mvc.screens.common.views.ObservableViewMvc;

public interface PromptViewMvc extends ObservableViewMvc<PromptViewMvc.Listener> {

    interface Listener {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }

    void setTitle(String title);

    void setMessage(String message);

    void setPositiveCaption(String caption);

    void setNegativeCaption(String caption);
}
