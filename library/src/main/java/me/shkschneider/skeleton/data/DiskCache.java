package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.Serializable;

import me.shkschneider.skeleton.helper.LogHelper;

// <http://developer.android.com/reference/java/io/Serializable.html>
public class DiskCache {

    // Prevents direct initialization
    private DiskCache() {
        // Empty
    }

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesInternal>
    public static class Internal extends Cache {

        public Internal() {
            DIR = InternalDataHelper.cache();
        }

    }

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesExternal>
    public static class External extends Cache {

        public External() {
            DIR = ExternalDataHelper.cache();
        }

    }

    // Prevents direct initialization
    private static class Cache {

        protected File DIR;

        private Cache() {
            // Ignore
        }

        synchronized public boolean put(@NonNull final String key, final Serializable value) {
            if (DIR == null) {
                LogHelper.w("Dir was NULL");
                return false;
            }

            if (DIR.exists()) {
                final String path = FileHelper.join(DIR.getAbsolutePath(), key);
                final File file = FileHelper.get(path);
                if (file.exists()) {
                    file.delete();
                }
                return SerializeHelper.write(value, file);
            }
            return false;
        }

        @Nullable
        synchronized public Serializable get(@NonNull final String key) {
            if (DIR == null) {
                LogHelper.w("Dir was NULL");
                return null;
            }

            if (DIR.exists()) {
                final String path = FileHelper.join(DIR.getAbsolutePath(), key);
                final File file = FileHelper.get(path);
                if (file.exists()) {
                    return (Serializable) SerializeHelper.read(file);
                }
            }
            return null;
        }

        synchronized public void clear() {
            if (DIR == null) {
                LogHelper.w("Dir was NULL");
                return ;
            }

            if (DIR.exists()) {
                final String[] files = DIR.list();
                for (int i = 0; i < files.length; i++) {
                    final String path = FileHelper.join(DIR.getAbsolutePath(), files[i]);
                    final File file = FileHelper.get(path);
                    file.delete();
                }
            }
        }

        public int size() {
            if (DIR == null) {
                LogHelper.w("Dir was NULL");
                return 0;
            }

            final File dir = ExternalDataHelper.cache();
            if (dir.exists()) {
                return dir.list().length;
            }
            return 0;
        }

    }

}
