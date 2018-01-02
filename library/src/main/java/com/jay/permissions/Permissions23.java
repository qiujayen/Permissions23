package com.jay.permissions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jay on 2017/11/21 上午11:45
 */
public class Permissions23 {

    public interface PermissionCallback/* extends ActivityCompat.OnRequestPermissionsResultCallback */ {

        void onPermissionsGranted(int requestCode, boolean isAllAccept, String... perms);

        void onPermissionsDenied(int requestCode, boolean isAllReject, String... perms);

    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /** Activity */

    public static void requestPermissions(Activity activity, int requestCode, @NonNull String... perms) {
        ActivityCompat.requestPermissions(activity, perms, requestCode);
    }

    /**
     * 检测权限是否被永久否认
     */
    public static boolean checkPermissionPermanentlyDenied(Activity activity, @NonNull String perm) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, perm);
    }

    /**
     * 检测权限是否被永久否认
     */
    public static boolean checkPermissionPermanentlyDenied(Activity activity, @NonNull String... perms) {
        for (String perm : perms) {
            if (!checkPermissionPermanentlyDenied(activity, perm)) {
                return false;
            }
        }
        return true;
    }

    /** Fragment */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermissions(Fragment fragment, int requestCode, @NonNull String... perms) {
        fragment.requestPermissions(perms, requestCode);
    }

    public static void requestPermissions(android.support.v4.app.Fragment fragment, int requestCode, @NonNull String... perms) {
        fragment.requestPermissions(perms, requestCode);
    }

    /**
     * 检测权限是否被永久否认
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermissionPermanentlyDenied(Fragment fragment, @NonNull String perm) {
        return fragment.shouldShowRequestPermissionRationale(perm);
    }

    public static boolean checkPermissionPermanentlyDenied(android.support.v4.app.Fragment fragment, @NonNull String perm) {
        return fragment.shouldShowRequestPermissionRationale(perm);
    }

    /**
     * 检测权限是否被永久否认
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermissionPermanentlyDenied(Fragment fragment, @NonNull String... perms) {
        for (String perm : perms) {
            if (checkPermissionPermanentlyDenied(fragment, perm)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermissionPermanentlyDenied(android.support.v4.app.Fragment fragment, @NonNull String... perms) {
        for (String perm : perms) {
            if (checkPermissionPermanentlyDenied(fragment, perm)) {
                return false;
            }
        }
        return true;
    }

    public static void showAppSettingsDialog(Activity activity, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.title_settings_dialog)
                .setMessage(R.string.rationale_ask_again)
                .setPositiveButton("是", listener)
                .setNegativeButton("否", null).show();
    }

    public static void openApplicationSettingsActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }

    public static void openApplicationSettingsActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, requestCode);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallback callback) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        // Report granted permissions, if any.
        if (!granted.isEmpty()) {
            String[] grantedArray = new String[granted.size()];
            granted.toArray(grantedArray);
            callback.onPermissionsGranted(requestCode, denied.isEmpty(), grantedArray);
        }
        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            String[] deniedArray = new String[denied.size()];
            denied.toArray(deniedArray);
            callback.onPermissionsDenied(requestCode, granted.isEmpty(), deniedArray);
        }

    }
}