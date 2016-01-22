package me.shkschneider.skeleton.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// <https://github.com/orhanobut/rootchecker/blob/master/rootchecker/src/main/java/com/orhanobut/rootchecker/RootChecker.java>
public class RootHelper {

    private static String binary() {
        final List<String> paths = new ArrayList<String>() {
            {
                add("/sbin");
                add("/system/bin");
                add("/system/xbin");
                add("/data/local/xbin");
                add("/data/local/bin");
                add("/system/sd/xbin");
                add("/system/bin/failsafe");
                add("/data/local");
            }
        };
        for (final String path : paths) {
            final File file = new File(path + SystemProperties.property(SystemProperties.SYSTEM_PROPERTY_FILE_SEPARATOR) + "su");
            if (file.exists() && file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public static boolean rooted() {
        return (binary() != null);
    }

    // Careful
    @Deprecated
    public static void run(final String commandLine) {
        // TODO
    }

}
