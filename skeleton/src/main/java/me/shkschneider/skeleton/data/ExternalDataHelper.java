package me.shkschneider.skeleton.data;

import android.os.Environment;

import me.shkschneider.skeleton.SkeletonApplication;

import java.io.File;

public class ExternalDataHelper {

    public static File download() {
        return Environment.getDownloadCacheDirectory();
    }

    public static File cache() {
        return SkeletonApplication.CONTEXT.getExternalCacheDir();
    }

    public static File dir() {
        return Environment.getExternalStorageDirectory();
    }

    public static File file(final String name) {
        return SkeletonApplication.CONTEXT.getExternalFilesDir(name);
    }

    public static boolean delete(final String name) {
        return SkeletonApplication.CONTEXT.deleteFile(name);
    }

}
