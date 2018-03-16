package me.shkschneider.skeleton.data

import me.shkschneider.skeleton.helper.LogHelper
import java.io.*

object SerializeHelper {

    fun write(obj: Any, file: File): Boolean {
        try {
            val objectOutputStream = ObjectOutputStream(FileOutputStream(file))
            objectOutputStream.writeObject(obj)
            objectOutputStream.close()
            return true
        } catch (e: FileNotFoundException) {
            LogHelper.wtf(e)
        } catch (e: IOException) {
            LogHelper.wtf(e)
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
            LogHelper.wtf(e)
        } catch (e: IOException) {
            LogHelper.wtf(e)
        } catch (e: ClassNotFoundException) {
            LogHelper.wtf(e)
        }
        return null
    }

}
