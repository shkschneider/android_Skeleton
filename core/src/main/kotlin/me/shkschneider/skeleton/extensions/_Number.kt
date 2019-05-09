package me.shkschneider.skeleton.extensions

// Flags

fun Int.has(other: Int): Boolean =
        (this and other) != 0

fun Long.has(other: Long): Boolean =
        (this and other) != 0.toLong()

// toEnglish
// <https://stackoverflow.com/a/47376072/603270>

private val zero = "zero"
private val oneToNine = listOf("one", "two", "three", "four", "five", "six", "seven", "height", "nine")
private val tenToNinteen = listOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
private val dozens = listOf("ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

private fun fromOneToAHundred(number: Short): String = if (number == 0.toShort()) "" else
    when {
        number <= 9 -> oneToNine[number - 1]
        number <= 19 -> tenToNinteen[number % 10]
        else -> "${dozens[number / 10 - 1]} ${fromOneToMany(number % 10)}"
    }

private fun fromOneToMany(number: Int): String = when {
    number >= 1_000_000_000 -> fromOneToMany(number / 1_000_000_000) + " billion " + fromOneToMany(number % 1_000_000_000)
    number >= 1_000_000 -> fromOneToMany(number / 1_000_000) + " million " + fromOneToMany(number % 1_000_000)
    number >= 1_000 -> fromOneToMany(number / 1_000) + " thousand " + fromOneToMany(number % 1_000)
    number >= 100 -> fromOneToMany(number / 100) + " hundred " + fromOneToMany(number % 100)
    else -> fromOneToAHundred(number.toShort())
}

private fun toEnglish(number: Int): String = when {
    number < 0 -> "minus ${toEnglish(-number)}"
    number > 0 -> fromOneToMany(number).trim { it <= ' ' }
    else -> zero
}

fun Number.toEnglish(): String = toEnglish(this.toInt())
