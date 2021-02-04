package com.example.mvc.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mvc.screens.common.dialogs.BaseDialog;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;

public class PromptDialog extends BaseDialog implements PromptViewMvc.Listener {

    protected static final String ARG_TITLE = "ARG_TITLE";
    protected static final String ARG_MESSAGE = "ARG_MESSAGE";
    protected static final String ARG_POSITIVE_BUTTON_CAPTION = "ARG_POSITIVE_BUTTON_CAPTION";
    protected static final String ARG_NEGATIVE_BUTTON_CAPTION = "ARG_NEGATIVE_BUTTON_CAPTION";

    public static PromptDialog newPromptDialog(String title, String message, String positiveButtonCaption, String negativeButtonCaption) {
        PromptDialog promptDialog = new PromptDialog();
        Bundle args = new Bundle(4);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_CAPTION, positiveButtonCaption);
        args.putString(ARG_NEGATIVE_BUTTON_CAPTION, negativeButtonCaption);
        promptDialog.setArguments(args);
        return promptDialog;
    }

    private DialogsEventBus mDialogsEventBus;
    private PromptViewMvc mViewMvc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("arguments mustn't be null");
        }
        mViewMvc = getCompositionRoot().getViewMvcFactory().getPromptViewMvc(null);

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(mViewMvc.getRootView());

        mViewMvc.setTitle(getArguments().getString(ARG_TITLE));
        mViewMvc.setMessage(getArguments().getString(ARG_MESSAGE));
        mViewMvc.setPositiveCaption(getArguments().getString(ARG_POSITIVE_BUTTON_CAPTION));
        mViewMvc.setNegativeCaption(getArguments().getString(ARG_NEGATIVE_BUTTON_CAPTION));

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onPositiveButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }

    @Override
    public void onNegativeButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

}


