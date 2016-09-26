package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.data.InternalDataHelper;

public class AssetsHelper {

    protected AssetsHelper() {
        // Empty
    }

    public static AssetManager assetManager(@NonNull final Context context) {
        return context.getAssets();
    }

    @Nullable
    public static List<String> list(@NonNull final Context context) {
        final List<String> list = new ArrayList<>();
        try {
            Collections.addAll(list, assetManager(context).list(""));
            return list;
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static InputStream open(@NonNull final Context context, @NonNull final String name) {
        final AssetManager assetManager = assetManager(context);
        try {
            return assetManager.open(name);
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean dump(@NonNull final Context context) {
        int errors = 0;
        final List<String> assets = list(context);
        if (assets == null) {
            errors++;
        }
        else {
            for (final String asset : assets) {
                try {
                    final InputStream inputStream = open(context, asset);
                    if (inputStream == null) {
                        throw new NullPointerException();
                    }
                    final OutputStream outputStream = InternalDataHelper.openOutput(context, asset);
                    if (outputStream == null) {
                        throw new NullPointerException();
                    }
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, read);
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
                catch (final IOException e) {
                    LogHelper.wtf(e);
                    errors++;
                }
            }
        }
        return (errors == 0);
    }

}
