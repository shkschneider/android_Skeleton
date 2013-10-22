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
package me.shkschneider.skeleton.storage;

import android.content.Context;

import java.io.FileNotFoundException;

import me.shkschneider.skeleton.android.FileHelper;
import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class InternalStorageHelper {

    public static Boolean available() {
        return true;
    }

    public static Boolean write(final Context context, final String name, final String content, final Boolean append) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        try {
            int mode = Context.MODE_PRIVATE;
            if (append) {
                mode |= Context.MODE_APPEND;
            }
            return FileHelper.writeString(context.openFileOutput(name, mode), content);
        }
        catch (FileNotFoundException e) {
            LogHelper.e("FileNotFoundException: " + e.getMessage());
            return false;
        }
    }

    public static String read(final Context context, final String name) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        try {
            return FileHelper.readString(context.openFileInput(name));
        }
        catch (FileNotFoundException e) {
            LogHelper.e("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

}
