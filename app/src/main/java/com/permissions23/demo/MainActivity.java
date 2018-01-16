package com.permissions23.demo;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jay.permissions.Permissions23;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new Permissions23(this).requestPermissions(new String[]{Manifest.permission.CAMERA}, new Permissions23.Callback() {
                @Override
                public void onPermissionsGranted() {
                    Toast.makeText(MainActivity.this, "授权通过", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionsDenied(String[] permissions, Boolean[] isShowRequestPermissionRationale) {
                    for (Boolean aBoolean : isShowRequestPermissionRationale) {
                        if (!aBoolean) {
                            Toast.makeText(MainActivity.this, "授权未通过，不在询问", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "授权未通过，可以询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });
        }
    }
}
