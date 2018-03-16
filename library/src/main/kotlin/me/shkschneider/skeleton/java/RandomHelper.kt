package me.shkschneider.skeleton.java

import android.support.annotation.IntRange
import me.shkschneider.skeleton.helper.LogHelper
import java.util.*

object RandomHelper {

    fun binary(): Boolean {
        return Math.random() < 0.5
    }

    fun string(@IntRange(from = 0) length: Int, characters: String = StringHelper.HEX): String {
        val random = Random()
        val stringBuilder = StringBuilder()
        for (i in 0 until length) {
            stringBuilder.append(characters[random.nextInt(characters.length)])
        }
        return stringBuilder.toString()
    }

    fun inclusive(min: Int, max: Int): Int {
        if (min > max) {
            LogHelper.warning("MIN was greater than MAX")
            return -1
        }
        if (min == max) {
            LogHelper.info("MIN was equal to MAX")
            return min
        }
        return min + (Math.random() * (max - min + 1)).toInt()
    }

    fun exclusive(min: Int, max: Int): Int {
        return inclusive(min + 1, max - 1)
    }

}
