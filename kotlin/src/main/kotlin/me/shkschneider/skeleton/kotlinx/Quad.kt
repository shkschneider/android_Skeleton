package me.shkschneider.skeleton.kotlinx

import java.io.Serializable

data class Quad<out A, out B, out C, out D>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D
) : Serializable {

    override fun toString(): String = "($first, $second, $third, $fourth)"

}

fun <T> Quad<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)
