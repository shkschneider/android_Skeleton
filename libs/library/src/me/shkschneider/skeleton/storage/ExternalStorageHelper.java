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
import android.text.TextUtils;

import me.shkschneider.skeleton.android.FileHelper;
import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class ExternalStorageHelper {

    public static Boolean available() {
        return (! TextUtils.isEmpty(FileHelper.sdCard()));
    }

    public static Boolean write(final Context context, final String name, final String content, final Boolean append) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        if (FileHelper.sdCardWritable()) {
            return FileHelper.writeString(FileHelper.get(FileHelper.join(FileHelper.externalDir(context), name)), content);
        }
        else {
            LogHelper.w("SDCard was not writable");
            return false;
        }
    }

    public static String read(final Context context, final String name) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        if (FileHelper.sdCardReadable()) {
            return FileHelper.readString(FileHelper.get(FileHelper.join(FileHelper.externalDir(context), name)));
        }
        else {
            LogHelper.w("SDCard was not readable");
            return null;
        }
    }

}
