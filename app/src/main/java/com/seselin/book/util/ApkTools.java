package com.seselin.book.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by Seselin on 2016/10/9 15:14.
 * <p>
 * Apk相关工具类
 */

public class ApkTools {

    /**
     * 获取Apk的编译时间
     *
     * @param context 上下文
     * @return 编译时间
     */
    public static String getBuildTime(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String buildTime = "";
        try {
            buildTime = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString("buildTime");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return buildTime;
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String version = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 打开当前应用详情
     *
     * @param context 上下文
     */
    public static void openAppInfo(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
