package me.shkschneider.skeleton.kotlinx

// <https://mustafaali.net/2018/01/14/Kotlin-data-classes-and-sensitive-information/>
class Hidden<T>(var value: T) {

    override fun toString() = "█"

}
