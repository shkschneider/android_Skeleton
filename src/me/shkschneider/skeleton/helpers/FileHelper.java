package me.shkschneider.skeleton.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class FileHelper {

    public static final String ASSETS_PREFIX = "file:///android_asset/";

    public static String join(final String dirname, final String basename) {
        if (StringHelper.nullOrEmpty(dirname)) {
            LogHelper.warning("Dirname was NULL");
            return null;
        }
        if (StringHelper.nullOrEmpty(basename)) {
            LogHelper.warning("Basename was NULL");
            return null;
        }

        return String.format("%s%s%s", dirname, SystemHelper.systemProperty(SystemHelper.SYSTEM_PROPERTY_PATH_SEPARATOR), basename);
    }

    public static File get(final String path) {
        if (StringHelper.nullOrEmpty(path)) {
            LogHelper.warning("Path was NULL");
            return null;
        }

        return new File(path);
    }

    public static InputStream openFile(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return null;
        }

        try {
            return new FileInputStream(file);
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static InputStream openRaw(final int rawId) {
        try {
            return SkeletonApplication.CONTEXT.getResources().openRawResource(rawId);
        }
        catch (final Resources.NotFoundException e) {
            LogHelper.error("NotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static InputStream openAsset(final String assetName) {
        try {
            return SkeletonApplication.CONTEXT.getAssets().open(assetName);
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return null;
        }
    }

    public static String readString(final InputStream inputStream) {
        if (inputStream == null) {
            LogHelper.warning("InputStream was NULL");
            return null;
        }

//        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CharsetHelper.UTF8));
//        final StringBuilder stringBuilder = new StringBuilder();
//        String s;
//        while ((s = bufferedReader.readLine()) != null) {
//            stringBuilder.append(s);
//        }
//        bufferedReader.close();
//        return stringBuilder.toString();

        String string = "";
        final Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            string = string.concat(scanner.nextLine() + "\n");
        }
        return string;
    }

    public static String readString(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return null;
        }

        try {
            return readString(new FileInputStream(file));
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static Bitmap readBitmap(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return null;
        }

        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static boolean writeString(final OutputStream outputStream, final String content) {
        if (outputStream == null) {
            LogHelper.warning("OutputStream was NULL");
            return false;
        }
        if (StringHelper.nullOrEmpty(content)) {
            LogHelper.warning("String was NULL");
            return false;
        }

        try {
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return false;
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return false;
        }
    }

    public static boolean writeString(final File file, final String content) {
        try {
            writeString(new FileOutputStream(file), content);
            return true;
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return false;
        }
    }

    public static boolean writeBitmap(final File file, final Bitmap bitmap) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            return true;
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return false;
        }
    }

    public static List<String> list(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return null;
        }
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

    public static boolean remove(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return true;
        }

        return file.delete();
    }

}
