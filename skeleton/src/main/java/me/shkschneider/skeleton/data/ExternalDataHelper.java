package me.shkschneider.skeleton.data;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

import me.shkschneider.skeleton.SkeletonApplication;

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

    public static File file(@NonNull final String name) {
        return SkeletonApplication.CONTEXT.getExternalFilesDir(name);
    }

    public static boolean delete(@NonNull final String name) {
        return SkeletonApplication.CONTEXT.deleteFile(name);
    }

}
