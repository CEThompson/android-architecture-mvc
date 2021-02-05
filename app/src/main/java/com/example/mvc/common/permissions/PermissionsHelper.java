package com.example.mvc.common.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.mvc.common.BaseObservable;

public class PermissionsHelper extends BaseObservable<PermissionsHelper.Listener> {

    public interface Listener {
        void onPermissionGranted(String permission, int requestCode);

        void onPermissionDeclined(String permission, int requestCode);

        void onPermissionDeclinedFinal(String permission, int requestCode);
    }

    private final Activity mActivity;

    public PermissionsHelper(Activity activity) {
        this.mActivity = activity;
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(mActivity, new String[]{permission}, requestCode);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length < 1) throw new RuntimeException("No permissions on request result");
        String permission = permissions[0];
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            notifyPermissionGranted(permission, requestCode);
            //mDialogsManager.showPermissionGrantedDialog(null);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                notifyPermissionDeclined(permission, requestCode);
                //mDialogsManager.showPermissionDeniedAndCanAskForMoreDialog(null);
            } else {
                //mDialogsManager.showPermissionDeniedAndCantAskForMoreDialog(null);
                notifyPermissionDeclinedFinal(permission, requestCode);
            }
        }

    }

    private void notifyPermissionDeclinedFinal(String permission, int requestCode) {
        for (Listener listener : getListeners()) {
            listener.onPermissionDeclinedFinal(permission, requestCode);
        }
    }

    private void notifyPermissionDeclined(String permission, int requestCode) {
        for (Listener listener : getListeners()) {
            listener.onPermissionDeclined(permission, requestCode);
        }
    }

    private void notifyPermissionGranted(String permission, int requestCode) {
        for (Listener listener : getListeners()) {
            listener.onPermissionGranted(permission, requestCode);
        }
    }

}
