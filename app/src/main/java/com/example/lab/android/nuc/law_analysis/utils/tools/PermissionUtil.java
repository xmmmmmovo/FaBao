package com.example.lab.android.nuc.law_analysis.utils.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    private static final int REQUEST_EXTERNAL_STORANG = 1;
    private static final int PERMISSION_REQUEST_CODE = 1 << 5;

    private static OnRequestPermissionsListener sListener;

    public static void requestPermissions(Activity activity,OnRequestPermissionsListener listener) {

        sListener = listener;
        List<String> permissionList = new ArrayList<>();
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission( activity,Manifest.
                permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.RECORD_AUDIO );
        }
        if (ContextCompat.checkSelfPermission( activity,Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }
        if (ContextCompat.checkSelfPermission( activity,Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = permissionList.toArray( new String[permissionList.size()] );
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions( activity, permissions,
                    REQUEST_EXTERNAL_STORANG );
            ;
            if (permissions.length == 0) {
                sListener.onGranted();
            } else {
                sListener.onDenied();
            }
        }
    }

    public interface OnRequestPermissionsListener {
        /* 授予权限时执行 */
        void onGranted();

        /* 拒绝权限时执行 */
        void onDenied();
    }
}
