package me.shkschneider.skeleton.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

    public static final String ASSETS_PREFIX = "file:///android_asset/";

    public static String join(@NotNull final String dirname, @NotNull final String basename) {
        return String.format("%s%s%s", dirname, SystemHelper.systemProperty(SystemHelper.SYSTEM_PROPERTY_PATH_SEPARATOR), basename);
    }

    public static File get(@NotNull final String path) {
        return new File(path);
    }

    public static InputStream openFile(@NotNull final File file) {
        try {
            return new FileInputStream(file);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static InputStream openRaw(final int rawId) {
        try {
            return ApplicationHelper.resources().openRawResource(rawId);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static InputStream openAsset(@NotNull final String assetName) {
        try {
            return ApplicationHelper.assets().open(assetName);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String readString(@NotNull final InputStream inputStream) {
        String string = "";
        final Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            string = string.concat(scanner.nextLine() + "\n");
        }
        return string;
    }

    public static String readString(@NotNull final File file) {
        try {
            return readString(new FileInputStream(file));
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Bitmap readBitmap(@NotNull final File file) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean writeString(@NotNull final OutputStream outputStream, @NotNull final String content) {
        try {
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean writeString(@NotNull final File file, @NotNull final String content) {
        try {
            return writeString(new FileOutputStream(file), content);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean writeBitmap(@NotNull final File file, @NotNull final Bitmap bitmap) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static List<String> list(@NotNull final File file) {
        if (! file.isDirectory()) {
            LogHelper.debug("File was not a directory");
            return null;
        }

        final List<String> list = new ArrayList<String>();

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

    public static boolean remove(@NotNull final File file) {
        return file.delete();
    }

}
