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
package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class DirHelper {

    // Internal

    public static String getInternal(final Context context, final String name) {
        if (context != null) {
            final File dir = context.getFilesDir();
            if (dir != null) {
                return (new File(dir.getAbsolutePath(), name).getAbsolutePath());
            }
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
        }
        return null;
    }

    // Cache

    public static String getInternalCache(final Context context) {
        if (context != null) {
            final File cache = context.getCacheDir();
            if (cache != null) {
                return cache.getAbsolutePath();
            }
        }
        return null;
    }

    public static String getExternalCache(final Context context) {
        if (context != null) {
            final File dir = context.getExternalCacheDir();
            if (dir != null) {
                return dir.getAbsolutePath();
            }
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
