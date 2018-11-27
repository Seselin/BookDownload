package com.seselin.book.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.seselin.book.util.ApkTools;


public class StartActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initNoticeDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAppPermission();
    }

    private void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private final int PERMISSION_REQUEST = 0xa00;

    public static String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void checkAppPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        } else {
            startApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                for (int grantResult : grantResults) {
                    if (grantResult == -1) {
                        showNoticeDialog();
                        return;
                    }
                }
                break;
        }
        startApp();
    }

    private AlertDialog permissionDialog;

    private void showNoticeDialog() {
        if (permissionDialog != null && !permissionDialog.isShowing()) {
            permissionDialog.show();
        }
    }

    private void initNoticeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("权限不足,请到应用管理打开权限。");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionDialog.dismiss();
                        ApkTools.openAppInfo(mContext);
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionDialog.dismiss();
                        finish();
                    }
                });
        permissionDialog = builder.create();
    }

}
