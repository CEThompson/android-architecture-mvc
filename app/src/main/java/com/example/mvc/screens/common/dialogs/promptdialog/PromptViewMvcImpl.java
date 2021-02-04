package com.example.mvc.screens.common.dialogs.promptdialog;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvc.R;
import com.example.mvc.screens.common.views.BaseObservableViewMvc;

// NOTE: This class represents the UI Layer
public class PromptViewMvcImpl
        extends BaseObservableViewMvc<PromptViewMvc.Listener>
        implements PromptViewMvc {

    private TextView mTxtTitle;
    private TextView mTxtMessage;
    private AppCompatButton mBtnPositive;
    private AppCompatButton mBtnNegative;


    public PromptViewMvcImpl(LayoutInflater inflater,
                             @Nullable ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.dialog_prompt, parent, false));

        mTxtTitle = findViewById(R.id.txt_title);
        mTxtMessage = findViewById(R.id.txt_message);
        mBtnPositive = findViewById(R.id.btn_positive);
        mBtnNegative = findViewById(R.id.btn_negative);

        mBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()) {
                    listener.onPositiveButtonClicked();
                }
            }
        });

        mBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()) {
                    listener.onNegativeButtonClicked();
                }
            }
        });


    }

    /*private void onNegativeButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

    private void onPositiveButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }*/

    @Override
    public void setTitle(String title) {
        mTxtTitle.setText(title);
    }

    @Override
    public void setMessage(String message) {
        mTxtMessage.setText(message);
    }

    @Override
    public void setPositiveCaption(String caption) {
        mBtnPositive.setText(caption);
    }

    @Override
    public void setNegativeCaption(String caption) {
        mBtnNegative.setText(caption);
    }

}
