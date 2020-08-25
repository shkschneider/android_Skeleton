package me.shkschneider.skeleton.android.util

import androidx.annotation.IntRange
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.kotlin.text.StringHelper
import java.util.Random

fun Random.binary(): Boolean =
    Math.random() < 0.5

fun Random.string(@IntRange(from = 0) length: Int, characters: String = StringHelper.HEX): String =
    StringBuilder().apply {
        val random = Random()
        for (i in 0 until length) {
            append(characters[random.nextInt(characters.length)])
        }
    }.toString()

fun Random.inclusive(min: Int, max: Int): Int {
    if (min > max) {
        Logger.warning("MIN was greater than MAX")
        return -1
    }
    if (min == max) {
        Logger.info("MIN was equal to MAX")
        return min
    }
    return min + (Math.random() * (max - min + 1)).toInt()
}

fun Random.exclusive(min: Int, max: Int): Int =
    inclusive(min + 1, max - 1)
