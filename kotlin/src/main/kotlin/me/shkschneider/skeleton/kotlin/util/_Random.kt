package me.shkschneider.skeleton.kotlin.util

import me.shkschneider.skeleton.kotlin.log.Logger
import me.shkschneider.skeleton.kotlin.jvm.toStringOrEmpty
import java.util.Random
import java.util.UUID

/**
 * > Hey, I lost the server password. What is it, again?
 * > It's... wait. How do I know it's really you?
 * > Ooh, good question! I bet we can construct a cool proof-of-identity protocol. I'll start by picking two random...
 * > Oh good; it's you. Here's the password...
 * <https://xkcd.com/1121/>
 */

fun Random.id(): String =
    uuid().filter { it in ('0'..'9') }.toStringOrEmpty()

fun Random.uuid(): String =
    UUID.randomUUID().toString()

fun Random.uuid(id: String): String =
    UUID.nameUUIDFromBytes(id.toByteArray()).toString()

fun Random.binary(): Boolean =
    Math.random() < 0.5

fun Random.string(length: Int, characters: String = ('a'..'z').toString()): String {
    val random = Random()
    val stringBuilder = StringBuilder()
    for (i in 0 until length) {
        stringBuilder.append(characters[random.nextInt(characters.length)])
    }
    return stringBuilder.toString()
}

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
