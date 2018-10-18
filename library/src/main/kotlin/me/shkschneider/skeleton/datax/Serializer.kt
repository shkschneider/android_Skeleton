package me.shkschneider.skeleton.datax

import me.shkschneider.skeleton.helperx.Logger
import java.io.*

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
