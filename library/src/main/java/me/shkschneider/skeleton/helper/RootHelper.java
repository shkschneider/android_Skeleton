package me.shkschneider.skeleton.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.java.StringHelper;

public class RootHelper {

    // <https://github.com/orhanobut/rootchecker/blob/master/rootchecker/src/main/java/com/orhanobut/rootchecker/RootChecker.java>
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

    @Deprecated
    public static boolean su() {
        return (binary() != null);
    }

    @Deprecated
    public static int id() {
        final String su = binary();
        String output;
        if (StringHelper.nullOrEmpty(su)) {
            output = run(new String[] { "id" });
        }
        else {
            output = run("id");
        }
        if (StringHelper.nullOrEmpty(output)) {
            return -1;
        }
        output = output.replaceFirst("^uid=", "").replaceFirst("[\\(\\s].+", "");
        if (StringHelper.numeric(output)) {
            return Integer.valueOf(output);
        }
        return -1;
    }

    @SuppressWarnings("deprecation")
    public static boolean rooted() {
        return (id() == 0);
    }

    // Careful
    // <http://stackoverflow.com/a/23608939>
    @SuppressWarnings("deprecation")
    @Deprecated
    public static String run(final String command) {
        final String su = binary();
        if (StringHelper.nullOrEmpty(su)) {
            return null;
        }
        return run(new String[] { su, "-c", command });
    }

    // Careful
    // <http://stackoverflow.com/a/23608939>
    @Deprecated
    private static String run(final String[] commands) {
        try {
            final Process process = Runtime.getRuntime().exec(commands);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[1024];
            final StringBuilder stringBuilder = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                stringBuilder.append(buffer, 0, read);
            }
            bufferedReader.close();
            process.waitFor();
            String output = stringBuilder.toString();
            if (StringHelper.nullOrEmpty(output)) {
                return null;
            }
            return output.trim();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
