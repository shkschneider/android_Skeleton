package me.shkschneider.skeleton.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.Serializable;

import me.shkschneider.skeleton.helper.LogHelper;

// <http://developer.android.com/reference/java/io/Serializable.html>
public class DiskCache {

    // Prevents direct initialization
    private DiskCache(@NonNull final Context context) {
        // Empty
    }

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesInternal>
    public static class Internal extends Cache {

        public Internal(@NonNull final Context context) {
            super(context);
        }

    }

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesExternal>
    public static class External extends Cache {

        public External(@NonNull final Context context) {
            super(context);
        }

    }

    // Prevents direct initialization
    private static class Cache {

        private Context mContext;
        protected File DIR;

        private Cache(@NonNull final Context context) {
            mContext = context;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        synchronized public boolean put(@NonNull final String key, final Serializable value) {
            if (DIR == null) {
                LogHelper.warning("Dir was NULL");
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
                LogHelper.warning("Dir was NULL");
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

        @SuppressWarnings("ResultOfMethodCallIgnored")
        synchronized public void clear() {
            if (DIR == null) {
                LogHelper.warning("Dir was NULL");
                return;
            }

            if (DIR.exists()) {
                final String[] files = DIR.list();
                for (final String file : files) {
                    final String path = FileHelper.join(DIR.getAbsolutePath(), file);
                    FileHelper.get(path).delete();
                }
            }
        }

        public int size() {
            if (DIR == null) {
                LogHelper.warning("Dir was NULL");
                return 0;
            }

            final File dir = ExternalDataHelper.cache(mContext);
            if (dir.exists()) {
                return dir.list().length;
            }
            return 0;
        }

    }

}
