package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.Serializable;

public class DiskCacher {

    // Prevents direct initialization
    private DiskCacher() {
        // Empty
    }

    public static class Internal extends Cache {

        public Internal() {
            DIR = InternalDataHelper.cache();
        }

    }

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
            if (DIR.exists()) {
                final String path = FileHelper.join(DIR.getAbsolutePath(), key);
                final File file = FileHelper.get(path);
                if (file.exists()) {
                    file.delete();
                }
                return Serializer.write(value, file);
            }
            return false;
        }

        synchronized public Object get(@NonNull final String key) {
            if (DIR.exists()) {
                final String path = FileHelper.join(DIR.getAbsolutePath(), key);
                final File file = FileHelper.get(path);
                if (file.exists()) {
                    return Serializer.read(file);
                }
            }
            return null;
        }

        synchronized public void clear() {
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
            final File dir = ExternalDataHelper.cache();
            if (dir.exists()) {
                return dir.list().length;
            }
            return 0;
        }

    }

}
