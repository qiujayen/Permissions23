package com.permissions23.demo;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jay.permissions.Permissions23;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Permissions23.PermissionCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camrea();
            }
        });
    }

    private void camrea() {
        if (!Permissions23.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
//            if (Permissions23.checkPermissionPermanentlyDenied(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Permissions23.showAppSettingsDialog(this, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Permissions23.openAppicationSettingsActivity(MainActivity.this);
//                    }
//                });
//            } else {
                Permissions23.requestPermissions(this, 123, Manifest.permission.READ_CONTACTS);
//            }
        } else {
            //empty code
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();
        Permissions23.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, boolean isAllAccept, String... perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, boolean isAllReject, String... perms) {
        if (Permissions23.checkPermissionPermanentlyDenied(this, perms)) {
            Permissions23.showAppSettingsDialog(this, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Permissions23.openAppicationSettingsActivity(MainActivity.this);
                }
            });
        }
    }
}
