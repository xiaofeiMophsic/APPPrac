package com.example.musicvisualizer.permissions;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.musicvisualizer.R;

/**
 * Created by paozi on 2016/4/15.
 */
public class PermissionActivity extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 0;

    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_DENIED = -1;

    private static final String EXTRA_PERMISSIONS = "com.example.musicvisualizer.permissions";
    private PermissionsChecker checker;
    private boolean mRequirePermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)){
            throw new RuntimeException("非法启动activity");
        }

        setContentView(R.layout.activity_permissions);

        checker = new PermissionsChecker(this);
        mRequirePermissions = true;
    }

    public static void startActivityForResult(Activity activity, int requestCode, String... permissions){
        Intent intent = new Intent(activity, PermissionActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mRequirePermissions){
            String[] permissions = getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);

            if(checker.lakesPermissions(permissions)){
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            } else{
                allPermissionsGranted();
            }
        } else{
            mRequirePermissions = true;
        }
    }

    private void allPermissionsGranted() {

        setResult(PERMISSION_GRANTED);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)){
            mRequirePermissions = true;
            allPermissionsGranted();
        } else{
            mRequirePermissions = false;
            showPermissionsLakeDialog();
        }
    }

    private void showPermissionsLakeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help);
        builder.setIcon(R.drawable.ic_mic_off_black_24dp);
        builder.setMessage(R.string.string_help_text);
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSION_DENIED);
                finish();
            }
        });
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionActivity.this.startAppSetting();
            }
        });
        builder.show();
    }

    private void startAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private boolean hasAllPermissionsGranted(int[] grantResults){
        for(int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }
}
