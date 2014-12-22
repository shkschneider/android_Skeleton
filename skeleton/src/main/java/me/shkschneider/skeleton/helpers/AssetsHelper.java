package me.shkschneider.skeleton.helpers;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.data.InternalDataHelper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssetsHelper {

    public static AssetManager assetManager() {
        return SkeletonApplication.CONTEXT.getAssets();
    }

    public static List<String> list() {
        final List<String> list = new ArrayList<String>();
        try {
            Collections.addAll(list, assetManager().list(""));
            return list;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static InputStream open(@NonNull final String name) {
        final AssetManager assetManager = assetManager();
        try {
            return assetManager.open(name);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean dump() {
        int errors = 0;
        for (final String asset : list()) {
            try {
                final InputStream inputStream = open(asset);
                final OutputStream outputStream = InternalDataHelper.openOutput(asset);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            }
            catch (Exception e) {
                LogHelper.wtf(e);
                errors++;
            }
        }
        return (errors == 0);
    }

}
