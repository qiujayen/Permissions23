package com.jay.permissions;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 2018/1/15 下午3:23
 */
public class PermissionsFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 866;
    private Permissions23.Callback mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            List<String> deniedPermissions = new ArrayList<>();
            List<Boolean> deniedPermissionRationale = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                String perm = permissions[i];
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(perm);
                    deniedPermissionRationale.add(shouldShowRequestPermissionRationale(perm));
                }
            }
            if (!deniedPermissions.isEmpty()) {
                String[] deniedArray = new String[deniedPermissions.size()];
                deniedPermissions.toArray(deniedArray);

                Boolean[] deniedRationaleArray = new Boolean[deniedPermissionRationale.size()];
                deniedPermissionRationale.toArray(deniedRationaleArray);

                mCallback.onPermissionsDenied(deniedArray, deniedRationaleArray);
            } else {
                mCallback.onPermissionsGranted();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestPermissions(String[] permissions, Permissions23.Callback callback) {
        mCallback = callback;
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
    }
}
