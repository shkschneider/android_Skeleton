package me.shkschneider.skeleton.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.os.LocaleListCompat
import java.util.*

object LocaleHelper {

    object Application {

        fun switch(activity: Activity, locale: Locale) {
            switch(activity.applicationContext, locale)
            activity.recreate()
        }

        fun switch(context: Context, locale: Locale) {
            // Locale.setDefault(locale)
            with(context.resources) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configuration.setLocale(locale)
                } else {
                    @Suppress("DEPRECATION")
                    configuration.locale = locale
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    context.createConfigurationContext(configuration)
                } else {
                    @Suppress("DEPRECATION")
                    updateConfiguration(configuration, displayMetrics)
                }
            }
        }

    }

    object Device {

        fun locales(): List<Locale> {
            val localeListCompat = LocaleListCompat.getDefault()
            return (0 until localeListCompat.size()).map { localeListCompat.get(it) }
        }

        fun locale(): Locale {
            return LocaleListCompat.getDefault().get(0)
        }

    }

    fun language(locale: Locale): String {
        return locale.displayLanguage
    }

    fun language2(locale: Locale): String {
        return locale.language
    }

    fun language3(locale: Locale): String {
        return locale.isO3Language
    }

    fun country(locale: Locale): String {
        return locale.displayCountry
    }

    fun country2(locale: Locale): String {
        return locale.country
    }

    fun country3(locale: Locale): String {
        return locale.isO3Country
    }

}
