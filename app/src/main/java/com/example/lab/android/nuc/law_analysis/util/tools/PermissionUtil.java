package com.example.lab.android.nuc.law_analysis.util.tools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private static final int PERMISSION_REQUEST_CODE = 1 << 5;
    private static OnRequestPermissionsListener sListener;

    public void requestPermissions(List<String> pe) {

    }

    public static void requestPermissions(final Activity activity, String[] permissions,
                                          OnRequestPermissionsListener listener) {
        sListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission :
                permissions) {
            if (ContextCompat.checkSelfPermission( activity, permission )
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add( permission );
            }
        }

        if (!permissionList.isEmpty()) {
            showDialog( activity, permissionList );
        } else {
            listener.onGranted();
        }
    }

    public static void showDialog(final Activity activity, final List<String> permissionList) {
        AlertDialog hintDialog = new AlertDialog.Builder( activity )
                .setTitle( "提醒" )
                .setMessage( "欢迎使用程序！在使用前，有一些权限需要您同意，否则无法执行！" )
                .setCancelable( false )
                .setPositiveButton( "好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions( activity,
                                permissionList.toArray( new String[permissionList.size()] ),
                                PERMISSION_REQUEST_CODE );
                    }
                } )
                .setNegativeButton( "不好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog falseDialog = new AlertDialog.Builder( activity )
                                .setMessage( "抱歉,我们不能让您继续运行不完整的程序" )
                                .setCancelable( false )
                                .setPositiveButton( "是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                } )
                                .show();
                    }
                } )
                .show();
    }

    public static void onRequestPermissionsResult(int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add( permissions[i] );
                    }

                    if (deniedPermissions.isEmpty()) {
                        sListener.onGranted();
                    } else {
                        sListener.onDenied();
                    }
                }
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
