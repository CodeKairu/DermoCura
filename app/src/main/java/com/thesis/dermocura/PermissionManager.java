package com.thesis.dermocura;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

public class PermissionManager {

    private static PermissionManager instance = null;
    private Context context;

    private PermissionManager() {

    }

    public static PermissionManager getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionManager();
        }
        instance.init(context);
        return instance;
    }

    private void init(Context context) {this.context = context; }

    boolean checkPermissions(String[] permissions) {
        int size = permissions.length;

        for (int i = 0; i < size; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) == PermissionChecker.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    void askPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    void handlePermissionResult(Activity activity, int requestCode, String[] permissions,
                                int[] grandResults) {
        if (grandResults.length > 0) {
            for (int i = 0; i < grandResults.length; i++) {
                if (grandResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            //showPermissionRational(activity, requestCode);
        }
    }

    private void showPermissionRational(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("You need to Allow Permissions",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    askPermissions(activity,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.CAMERA},
                                            requestCode);
                                }
                            }
                        });
                return;
            }
        }
    }

    void showMessageOKCancel(String msg, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("ok", onClickListener)
                .setNegativeButton("Cancel", onClickListener)
                .create()
                .show();
    }

}
