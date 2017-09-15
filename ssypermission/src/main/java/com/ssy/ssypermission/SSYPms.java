package com.ssy.ssypermission;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by thinkpad on 2017/9/14.
 */

public class SSYPms {

    public static boolean hasPermission(Context context, String permission) {

        if (permission == null || permission.length() <= 0)
            return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        //---Appops权限
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int optMode = appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Binder.getCallingUid(), context.getPackageName());
        boolean opsPermissions = (optMode == AppOpsManager.MODE_ALLOWED);

        //---运行时权限
        boolean runningPermissions = ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;

        //---返回结果
        if (opsPermissions && runningPermissions)
            return true;
        else
            return false;
    }

    public static void reqPermission(Activity context, String[] permissions, int requestCode) {
        boolean b = true;
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermission(context, permissions[i])) {
                b = false;
                break;
            }
        }

        if (b) {
            int[] grantResults = new int[permissions.length];
            for (int i = 0; i < permissions.length; i++) {
                grantResults[i] = PackageManager.PERMISSION_GRANTED;
            }
            ((ActivityCompat.OnRequestPermissionsResultCallback) context).onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            ActivityCompat.requestPermissions(context, permissions, requestCode);
        }
    }
}
