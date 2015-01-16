package me.shkschneider.skeleton.data;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class ExternalDataHelper {

    public static File download() {
        return Environment.getDownloadCacheDirectory();
    }

    public static File cache() {
        return ApplicationHelper.context().getExternalCacheDir();
    }

    public static File dir() {
        return Environment.getExternalStorageDirectory();
    }

    public static File file(@NonNull final String name) {
        return ApplicationHelper.context().getExternalFilesDir(name);
    }

    public static boolean delete(@NonNull final String name) {
        return ApplicationHelper.context().deleteFile(name);
    }

}
