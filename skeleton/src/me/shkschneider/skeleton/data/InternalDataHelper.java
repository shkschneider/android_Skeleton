package me.shkschneider.skeleton.data;

import android.content.Context;
import android.os.Environment;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.LogHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class InternalDataHelper {

    public static FileInputStream openInput(final String name) {
        try {
            return SkeletonApplication.CONTEXT.openFileInput(name);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static FileOutputStream openOutput(final String name) {
        try {
            return SkeletonApplication.CONTEXT.openFileOutput(name, Context.MODE_PRIVATE);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static File root() {
        return Environment.getRootDirectory();
    }

    public static File cache() {
        return SkeletonApplication.CONTEXT.getCacheDir();
    }

    public static File dir() {
        return SkeletonApplication.CONTEXT.getFilesDir();
    }

    public static File file(final String name) {
        final File path = new File(dir(), "data");
        path.mkdirs();
        return new File(path, name);
    }

    public static File create(final File file) {
        try {
            if (! file.createNewFile()) {
                LogHelper.warning("File.CreateNewFile failed");
                return null;
            }

            return file;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean delete(final String name) {
        return SkeletonApplication.CONTEXT.deleteFile(name);
    }

}
