package me.shkschneider.skeleton.data;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class ExternalDataHelper {

    protected ExternalDataHelper() {
        // Empty
    }

    // <http://stackoverflow.com/a/18383302>
    public static File download() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public static File cache() {
        return ApplicationHelper.context().getExternalCacheDir();
    }

    public static File dir() {
        String path = FileHelper.join(Environment.getExternalStorageDirectory().getPath(), "/Android/data/");
        path = FileHelper.join(path, ApplicationHelper.packageName());
        path = FileHelper.join(path, "/files");
        return new File(path);
    }

    public static File file(@NonNull final String name) {
        return ApplicationHelper.context().getExternalFilesDir(name);
    }

    public static boolean delete(@NonNull final String name) {
        return ApplicationHelper.context().deleteFile(name);
    }

    public static boolean wipe() {
        int errors = 0;
        final File dir = dir();
        if (dir.exists()) {
            final File[] files = dir.listFiles();
            if (files == null) {
                return true;
            }
            for (final File file : files) {
                if (! file.delete()) {
                    errors++;
                }
            }
        }
        return (errors == 0);
    }

}
