package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return false;
        }
    }

    public static Object read(@NonNull final File file) {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

}
