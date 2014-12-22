package me.shkschneider.skeleton.storage;

import android.os.Environment;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import java.io.File;

public class StorageHelper {

    public static String downloads() {
        return FileHelper.join(sdcard(), "Downloads");
    }

    public static String cache() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    public static String sdcard() {
        if (! SdcardStorageHelper.available()) {
            LogHelper.debug("SdCard was unavailable");
            return null;
        }
        return SdcardStorageHelper.path();
    }

    public static String internal() {
        final File dir = SkeletonApplication.CONTEXT.getFilesDir();
        if (dir == null) {
            LogHelper.warning("File was NULL");
            return null;
        }

        return dir.getAbsolutePath();
    }

    public static String external() {
        final File dir = SkeletonApplication.CONTEXT.getExternalFilesDir(".");
        if (dir == null) {
            LogHelper.warning("Dir was NULL");
            return null;
        }

        return dir.getParentFile().getAbsolutePath();
    }

}
