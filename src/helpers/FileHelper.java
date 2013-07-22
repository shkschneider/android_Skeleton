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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class FileHelper {

    public static final String ASSETS_PREFIX = "file:///android_asset/";

    // Get

    public static File get(final String path) {
        return (new File(path));
    }

    // Open

    public static InputStream openFile(final File file) {
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.close();
        } catch (FileNotFoundException e) {
            LogHelper.e("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
        }

        return inputStream;
    }

    public static InputStream openRaw(final Context context, final int id) {
        if (context == null) {
            return null;
        }

        return context.getResources().openRawResource(id);
    }

    public static InputStream openAsset(final Context context, final String name) {
        try {
            return context.getAssets().open(name);
        } catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
        }

        return null;
    }

    // Read & Write

    public static String read(final InputStream inputStream) {

        if (inputStream == null) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine() + "\n");
        }
        return stringBuilder.toString();
    }

    public static Boolean write(final File file, final String content, final int mode) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
            return Boolean.TRUE;
        } catch (FileNotFoundException e) {
            LogHelper.e("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
        }
        return Boolean.FALSE;
    }

    // Serialize & Unserialize

    public static Boolean serialize(final File file, final Object object) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
        }
        return Boolean.FALSE;
    }

    public static Object unserialize(final File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
            return objectOutputStream.readObject();
        } catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LogHelper.e("ClassNotFoundException: " + e.getMessage());
        }
        return null;
    }

    // Remove

    public static Boolean remove(final File file) {
        if (file == null) {
            return Boolean.FALSE;
        }
        return file.delete();
    }

}
