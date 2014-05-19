package me.shkschneider.skeleton.storage;

import android.os.Environment;

import me.shkschneider.skeleton.java.StringHelper;

public class SdcardStorageHelper {

    public static String path() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static boolean available() {
        return (! StringHelper.nullOrEmpty(path()));
    }

    public static boolean readable() {
        return (available() && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY));
    }

    public static boolean writable() {
        return (available() && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

}
