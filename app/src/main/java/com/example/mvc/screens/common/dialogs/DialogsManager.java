package com.example.mvc.screens.common.dialogs;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.mvc.R;
import com.example.mvc.screens.common.dialogs.infodialog.InfoDialog;
import com.example.mvc.screens.common.dialogs.promptdialog.PromptDialog;

public class DialogsManager {

    private final Context mContext;
    private final FragmentManager mFragmentmanager;

    public DialogsManager(Context context, FragmentManager fragmentmanager) {
        this.mContext = context;
        this.mFragmentmanager = fragmentmanager;
    }

    public void showUseCaseErrorDialog(@Nullable String tag) {
        DialogFragment dialogFragment = PromptDialog.newPromptDialog(
                getString(R.string.error_network_call_failed_title),
                getString(R.string.error_network_call_failed_message),
                getString(R.string.error_network_call_failed_positive_button_caption),
                getString(R.string.error_network_call_failed_negative_button_caption));
        dialogFragment.show(mFragmentmanager, tag);
    }

    private String getString(int stringId) {
        return mContext.getString(stringId);
    }

    public @Nullable
    String getShownDialogTag() {
        for (Fragment fragment: mFragmentmanager.getFragments()){
            if (fragment instanceof BaseDialog){
                return fragment.getTag();
            }
        }
        return null;
    }
}
