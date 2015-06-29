package me.shkschneider.skeleton.data;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class InternalDataHelper {

    protected InternalDataHelper() {
        // Empty
    }

    public static FileInputStream openInput(@NonNull final String name) {
        try {
            return ApplicationHelper.context().openFileInput(name);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static FileOutputStream openOutput(@NonNull final String name) {
        try {
            return ApplicationHelper.context().openFileOutput(name, Context.MODE_PRIVATE);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static File root() {
        return Environment.getRootDirectory();
    }

    public static File cache() {
        return ApplicationHelper.context().getCacheDir();
    }

    public static File dir() {
        return ApplicationHelper.context().getFilesDir();
    }

    public static File file(@NonNull final String name) {
        final File file = new File(dir().getAbsolutePath() + File.separator + name);
        try {
            if (! file.createNewFile()) {
                LogHelper.w("File.CreateNewFile failed");
            }

            return file;
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static boolean delete(@NonNull final String name) {
        return ApplicationHelper.context().deleteFile(name);
    }

}
