package com.example.musicvisualizer.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.security.Permission;

/**
 * Created by paozi on 2016/4/15.
 */
public class PermissionsChecker {

    private Context mContext;

    public PermissionsChecker(Context context){
        mContext = context.getApplicationContext();
    }

    public boolean lakesPermissions(String... permissions){
        for (String permission : permissions){
            if (lakesPermission(permission)){
                return true;
            }
        }
        return false;
    }

    private boolean lakesPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }
}
