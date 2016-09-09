package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.shkschneider.skeleton.helper.LogHelper;

public class SerializeHelper {

    protected SerializeHelper() {
        // Empty
    }

    public static boolean write(@NonNull final Object object, @NonNull final File file) {
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return true;
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return false;
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    @Nullable
    public static Object read(@NonNull final File file) {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
        catch (final IOException e) {
            LogHelper.wtf(e);
            return null;
        }
        catch (final ClassNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
