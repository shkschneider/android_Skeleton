package me.shkschneider.skeleton.data

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

import me.shkschneider.skeleton.helper.LogHelper

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
