package me.shkschneider.skeleton.helpers;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeHelper {

    public static boolean write(@NotNull final Object object, @NotNull final File file) {
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static Object read(@NotNull final File file) {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
