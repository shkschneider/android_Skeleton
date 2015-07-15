package me.shkschneider.skeleton.data;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import java.io.File;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class ExternalDataHelper {

    protected ExternalDataHelper() {
        // Empty
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static File download() {
        return Environment.getDownloadCacheDirectory();
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static File cache() {
        return ApplicationHelper.context().getExternalCacheDir();
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static File dir() {
        return Environment.getExternalStorageDirectory();
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static File file(@NonNull final String name) {
        return ApplicationHelper.context().getExternalFilesDir(name);
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean delete(@NonNull final String name) {
        return ApplicationHelper.context().deleteFile(name);
    }

}
