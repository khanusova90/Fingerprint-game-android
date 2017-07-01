package cz.hanusova.fingerprint_game.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Util class for getting basic application information
 * <p>
 * Created by khanusova on 6.3.2017.
 */

public final class AppUtils {

    private AppUtils() {
    }

    /**
     * Name of application version from gradle file
     *
     * @param context
     * @return Name of actual appVersion. Returns <code>null</code> in case that error occurs
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Version code from gradle file
     *
     * @param context
     * @return Code of actual appVersion. Returns <code>null</code> in case that error occurs
     */
    public static Integer getVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
