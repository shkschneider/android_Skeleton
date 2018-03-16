package me.shkschneider.skeleton.helper

import android.support.v4.os.LocaleListCompat
import java.util.*

object LocaleHelper {

    fun locales(): List<Locale> {
        val localeListCompat = LocaleListCompat.getDefault()
        return (0 until localeListCompat.size()).map { localeListCompat.get(it) }
    }

    fun locale(): Locale {
        return LocaleListCompat.getDefault().get(0)
    }

    fun language(): String {
        return language(locale())
    }

    fun language(locale: Locale): String {
        return locale.displayLanguage
    }

    fun language2(): String {
        return language2(locale())
    }

    fun language2(locale: Locale): String {
        return locale.language
    }

    fun language3(): String {
        return language3(locale())
    }

    fun language3(locale: Locale): String {
        return locale.isO3Language
    }

    fun country(): String {
        return country(locale())
    }

    fun country(locale: Locale): String {
        return locale.displayCountry
    }

    fun country2(): String {
        return country2(locale())
    }

    fun country2(locale: Locale): String {
        return locale.country
    }

    fun country3(): String {
        return country3(locale())
    }

    fun country3(locale: Locale): String {
        return locale.isO3Country
    }

}
