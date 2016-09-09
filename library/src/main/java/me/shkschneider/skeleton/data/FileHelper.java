package me.shkschneider.skeleton.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemProperties;
import me.shkschneider.skeleton.ui.BitmapHelper;

public class FileHelper {

    protected FileHelper() {
        // Empty
    }

    public static final String PREFIX_ASSETS = "file:///android_asset/";
    public static final String PREFIX_RES = "file:///android_res/";

    public static String join(@NonNull final String dirname, @NonNull final String basename) {
        return String.format(LocaleHelper.locale(),
                "%s%s%s",
                dirname, SystemProperties.property(SystemProperties.SYSTEM_PROPERTY_FILE_SEPARATOR), basename);
    }

    public static File get(@NonNull final String path) {
        return new File(path);
    }

    @Nullable
    public static InputStream openFile(@NonNull final File file) {
        try {
            return new FileInputStream(file);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static InputStream openRaw(@RawRes final int id) {
        try {
            return ApplicationHelper.resources().openRawResource(id);
        }
        catch (final Resources.NotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static InputStream openAsset(@NonNull final String assetName) {
        try {
            return ApplicationHelper.assetManager().open(assetName);
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String readString(@NonNull final InputStream inputStream) {
        try {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            return new Scanner(inputStream).useDelimiter("\\A").next();
        }
        catch (final NoSuchElementException e) {
            return null;
        }
    }

    @Nullable
    public static String readString(@NonNull final File file) {
        try {
            return readString(new FileInputStream(file));
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean writeString(@NonNull final OutputStream outputStream, @NonNull final String content) {
        try {
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean writeString(@NonNull final File file, @NonNull final String content) {
        try {
            return writeString(new FileOutputStream(file), content);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    @Nullable
    public static Bitmap readBitmap(@NonNull final File file) {
        return BitmapHelper.fromFile(file);
    }

    public static boolean writeBitmap(@NonNull final File file, @NonNull final Bitmap bitmap) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    @Nullable
    public static List<String> list(@NonNull final File file) {
        if (! file.isDirectory()) {
            LogHelper.debug("File was not a directory");
            return null;
        }

        final List<String> list = new ArrayList<>();

        final File[] files = file.listFiles();
        if (files == null) {
            LogHelper.debug("Files was NULL");
            return null;
        }

        for (final File f : files) {
            list.add(f.getAbsolutePath());
        }
        return list;
    }

    public static boolean remove(@NonNull final File file) {
        return file.delete();
    }

}
