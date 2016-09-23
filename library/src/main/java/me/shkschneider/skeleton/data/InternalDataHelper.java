package me.shkschneider.skeleton.data;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class InternalDataHelper {

    protected InternalDataHelper() {
        // Empty
    }

    @Nullable
    public static FileInputStream openInput(@NonNull final String name) {
        try {
            return ApplicationHelper.context().openFileInput(name);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static FileOutputStream openOutput(@NonNull final String name) {
        try {
            return ApplicationHelper.context().openFileOutput(name, Context.MODE_PRIVATE);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Deprecated // Avoid
    public static File root() {
        return Environment.getRootDirectory();
    }

    public static File cache() {
        return ApplicationHelper.context().getCacheDir();
    }

    public static File dir() {
        return ApplicationHelper.context().getFilesDir();
    }

    @Nullable
    public static File file(@NonNull final String name) {
        return new File(dir(), name);
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
