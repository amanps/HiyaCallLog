package aman.com.hiyacalllog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class Utils {

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Context context, String permission, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{permission},
                requestCode);
    }

    public static class Constants {
        public static final int LOG_SIZE = 50;
        public static final int CALL_LOG_PERMISSION = 0;
    }

}
