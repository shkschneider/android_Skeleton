package me.shkschneider.skeleton.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

@SuppressWarnings("unused")
public final class SerializeHelper {

    public static boolean write(final Object object, final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return false;
        }

        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return true;
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return false;
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return false;
        }
    }

    public static Object read(final File file) {
        if (file == null) {
            LogHelper.warning("File was NULL");
            return null;
        }

        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
        catch (final StreamCorruptedException e) {
            LogHelper.error("StreamCorruptedException: " + e.getMessage());
            return null;
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return null;
        }
        catch (final ClassNotFoundException e) {
            LogHelper.error("ClassNotFoundException: " + e.getMessage());
            return null;
        }
    }

}
