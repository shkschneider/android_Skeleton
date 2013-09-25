package me.shkschneider.skeleton;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Files {

    public static final String ASSETS_PREFIX = "file:///android_asset/";

    // Get

    public static java.io.File get(final String path) {
        if (! TextUtils.isEmpty(path)) {
            return new java.io.File(path);
        }
        else {
            Log.w("Path was NULL");
        }
        return null;
    }

    // Open

    public static InputStream openFile(final java.io.File file) {
        if (file != null) {
            InputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
                inputStream.close();
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }

            return inputStream;
        }
        else {
            Log.w("File was NULL");
        }
        return null;
    }

    public static InputStream openRaw(final Context context, final int id) {
        if (context != null) {
            return context.getResources().openRawResource(id);
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static InputStream openAsset(final Context context, final String name) {
        if (context != null) {
            try {
                return context.getAssets().open(name);
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    // Read & Write

    public static String readString(final InputStream inputStream) {
        if (inputStream != null) {
            String string = "";
            final Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                string = string.concat(scanner.nextLine() + "\n");
            }
            return string;
        }
        else {
            Log.w("InputStream was NULL");
        }
        return null;
    }

    public static String readString(final java.io.File file) {
        if (file != null) {
            try {
                return readString(new FileInputStream(file));
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            Log.w("File was NULL");
        }
        return null;
    }

    public static Bitmap readBitmap(final java.io.File file) {
        if (file != null) {
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            Log.w("File was NULL");
        }
        return null;
    }

    public static Boolean writeString(final OutputStream outputStream, final String content) {
        if (outputStream != null) {
            if (! TextUtils.isEmpty(content)) {
                try {
                    outputStream.write(content.getBytes());
                    outputStream.close();
                    return true;
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("OutputStream was NULL");
        }
        return false;
    }

    public static Boolean writeString(final java.io.File file, final String content) {
        try {
            return writeString(new FileOutputStream(file), content);
        }
        catch (FileNotFoundException e) {
            Log.e("FileNotFoundException: " + e.getMessage());
        }
        return null;
    }

    public static Boolean writeBitmap(final java.io.File file, final Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            return true;
        }
        catch (FileNotFoundException e) {
            Log.e("FileNotFoundException: " + e.getMessage());
        }
        return false;
    }

    // Serialize & Unserialize

    public static Boolean serialize(final java.io.File file, final Object object) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        }
        catch (IOException e) {
            Log.e("IOException: " + e.getMessage());
        }
        return false;
    }

    public static Object unserialize(final java.io.File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
            return objectOutputStream.readObject();
        }
        catch (IOException e) {
            Log.e("IOException: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.e("ClassNotFoundException: " + e.getMessage());
        }
        return null;
    }

    // Remove

    public static Boolean remove(final java.io.File file) {
        if (file != null) {
            return file.delete();
        }
        else {
            Log.w("File was NULL");
        }
        return false;
    }

    // Internal

    public static String internalDir(final Context context) {
        if (context != null) {
            final java.io.File dir = context.getFilesDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                Log.w("File was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    // External

    public static String externalDir(final Context context) {
        if (context != null) {
            final java.io.File dir = context.getExternalFilesDir(".");
            if (dir != null) {
                return dir.getParentFile().getAbsolutePath();
            }
            else {
                Log.w("File was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    // Cache

    public static String internalCacheDir(final Context context) {
        if (context != null) {
            final java.io.File dir = context.getCacheDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                Log.w("File was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static String externalCacheDir(final Context context) {
        if (context != null) {
            final java.io.File dir = context.getExternalCacheDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                Log.w("File was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static String downloadCache() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    // SDCard

    public static Boolean sdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String sdCard() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
