package me.shkschneider.skeleton.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// <https://github.com/orhanobut/rootchecker/blob/master/rootchecker/src/main/java/com/orhanobut/rootchecker/RootChecker.java>
public class RootHelper {

    @Deprecated
    public static boolean rooted() {
        final List<String> paths = new ArrayList<String>() {
            {
                add("/data/local/bin/su");
                add("/data/local/su");
                add("/data/local/xbin/su");
                add("/sbin/su");
                add("/system/bin/failsafe/su");
                add("/system/bin/su");
                add("/system/sd/xbin/su");
                add("/system/xbin/su");
            }
        };
        for (final String path : paths) {
            final File file = new File(path);
            if (file.exists() && file.isFile() && file.canExecute()) {
                return true;
            }
        }
        return false;
    }

    // TODO run()

}
