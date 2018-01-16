package com.jay.permissions;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

/**
 * Created by jay on 2017/11/21 上午11:45
 */
public class Permissions23 {

    private static final String TAG = "Permissions23";
    private final PermissionsFragment mPermissionsFragment;

    public Permissions23(Activity activity) {
        mPermissionsFragment = getPermissionsFragment(activity);
    }


    private PermissionsFragment getPermissionsFragment(Activity activity) {
        PermissionsFragment permissionsFragment = (PermissionsFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (permissionsFragment == null) {
            permissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionsFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions, Callback callback) {
        mPermissionsFragment.requestPermissions(permissions, callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasPermission(Activity activity, String permission) {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public static Intent getApplicationSettingsIntent(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    public interface Callback {
        void onPermissionsGranted();

        void onPermissionsDenied(String[] permissions, Boolean[] isShowRequestPermissionRationale);
    }
}