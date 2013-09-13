/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import me.shkschneider.skeleton.SkeletonLog;

public abstract class SkeletonFileHelper {

    public static final String ASSETS_PREFIX = "file:///android_asset/";

    // Get

    public static File get(final String path) {
        if (! TextUtils.isEmpty(path)) {
            return new File(path);
        }
        else {
            SkeletonLog.d("Path was NULL");
        }
        return null;
    }

    // Open

    public static InputStream openFile(final File file) {
        if (file != null) {
            InputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
                inputStream.close();
            }
            catch (FileNotFoundException e) {
                SkeletonLog.e("FileNotFoundException: " + e.getMessage());
            }
            catch (IOException e) {
                SkeletonLog.e("IOException: " + e.getMessage());
            }

            return inputStream;
        }
        else {
            SkeletonLog.d("File was NULL");
        }
        return null;
    }

    public static InputStream openRaw(final Context context, final int id) {
        if (context != null) {
            return context.getResources().openRawResource(id);
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    public static InputStream openAsset(final Context context, final String name) {
        if (context != null) {
            try {
                return context.getAssets().open(name);
            }
            catch (IOException e) {
                SkeletonLog.e("IOException: " + e.getMessage());
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
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
            SkeletonLog.d("InputStream was NULL");
        }
        return null;
    }

    public static String readString(final File file) {
        if (file != null) {
            try {
                return SkeletonFileHelper.readString(new FileInputStream(file));
            }
            catch (FileNotFoundException e) {
                SkeletonLog.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            SkeletonLog.d("File was NULL");
        }
        return null;
    }

    public static Bitmap readBitmap(final File file) {
        if (file != null) {
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            }
            catch (FileNotFoundException e) {
                SkeletonLog.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            SkeletonLog.d("File was NULL");
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
                    SkeletonLog.e("FileNotFoundException: " + e.getMessage());
                }
                catch (IOException e) {
                    SkeletonLog.e("IOException: " + e.getMessage());
                }
            }
            else {
                SkeletonLog.d("String was NULL");
            }
        }
        else {
            SkeletonLog.d("OutputStream was NULL");
        }
        return false;
    }

    public static Boolean writeString(final File file, final String content) {
        try {
            return SkeletonFileHelper.writeString(new FileOutputStream(file), content);
        }
        catch (FileNotFoundException e) {
            SkeletonLog.e("FileNotFoundException: " + e.getMessage());
        }
        return null;
    }

    public static Boolean writeBitmap(final File file, final Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            return true;
        }
        catch (FileNotFoundException e) {
            SkeletonLog.e("FileNotFoundException: " + e.getMessage());
        }
        return false;
    }

    // Serialize & Unserialize

    public static Boolean serialize(final File file, final Object object) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        }
        catch (IOException e) {
            SkeletonLog.e("IOException: " + e.getMessage());
        }
        return false;
    }

    public static Object unserialize(final File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
            return objectOutputStream.readObject();
        }
        catch (IOException e) {
            SkeletonLog.e("IOException: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            SkeletonLog.e("ClassNotFoundException: " + e.getMessage());
        }
        return null;
    }

    // Remove

    public static Boolean remove(final File file) {
        if (file != null) {
            return file.delete();
        }
        else {
            SkeletonLog.d("File was NULL");
        }
        return false;
    }

    // Internal

    public static String getInternal(final Context context, final String name) {
        if (context != null) {
            final File dir = context.getFilesDir();
            if (dir != null) {
                return (new File(dir.getAbsolutePath(), name).getAbsolutePath());
            }
            else {
                SkeletonLog.d("Context was NULL");
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    // External

    public static String getExternal(final Context context, final String name) {
        if (context != null) {
            final File dir = context.getExternalFilesDir(name);
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                SkeletonLog.d("File was NULL");
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    // Cache

    public static String getInternalCache(final Context context) {
        if (context != null) {
            final File dir = context.getCacheDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                SkeletonLog.d("File was NULL");
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    public static String getExternalCache(final Context context) {
        if (context != null) {
            final File dir = context.getExternalCacheDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
            else {
                SkeletonLog.d("File was NULL");
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    public static String getDownloadCache() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    // SDCard

    public static Boolean hasSdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getSdCard() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
