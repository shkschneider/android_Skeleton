package me.shkschneider.skeleton.android.io

import me.shkschneider.skeleton.android.log.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object Serializer {

    fun write(obj: Any, file: File): Boolean {
        try {
            val objectOutputStream = ObjectOutputStream(FileOutputStream(file))
            objectOutputStream.writeObject(obj)
            objectOutputStream.close()
            return true
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
        } catch (e: IOException) {
            Logger.wtf(e)
        }
        return false
    }

    fun read(file: File): Any? {
        try {
            val objectInputStream = ObjectInputStream(FileInputStream(file))
            val obj = objectInputStream.readObject()
            objectInputStream.close()
            return obj
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
        } catch (e: IOException) {
            Logger.wtf(e)
        } catch (e: ClassNotFoundException) {
            Logger.wtf(e)
        }
        return null
    }

}
