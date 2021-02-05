package com.example.mvc.screens.common.dialogs;

import android.support.v4.app.DialogFragment;
import com.example.mvc.common.CustomApplication;
import com.example.mvc.common.di.ControllerCompositionRoot;
import com.example.mvc.screens.common.main.MainActivity;

public abstract class BaseDialog extends DialogFragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity) requireActivity()).getActivityCompositionRoot()
            );
        }
        return mControllerCompositionRoot;
    }


}
