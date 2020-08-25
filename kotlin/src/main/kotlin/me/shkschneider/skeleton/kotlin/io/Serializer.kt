package me.shkschneider.skeleton.kotlin.io

import me.shkschneider.skeleton.kotlin.jvm.tryOr
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object Serializer {

    fun write(obj: Any, file: File): Boolean =
        tryOr(false) {
            val objectOutputStream = ObjectOutputStream(FileOutputStream(file))
            objectOutputStream.writeObject(obj)
            objectOutputStream.close()
            true
        } == true

    fun read(file: File): Any? =
        tryOrNull {
            val objectInputStream = ObjectInputStream(FileInputStream(file))
            val obj = objectInputStream.readObject()
            objectInputStream.close()
            obj
        }

}
