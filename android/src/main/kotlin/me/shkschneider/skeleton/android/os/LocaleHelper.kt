package me.shkschneider.skeleton.android.os

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleHelper {

    object Application {

        // attachBaseContext() -> super.attachBaseContext(switch())
        fun switch(context: Context?, locale: Locale): ContextWrapper {
            context ?: return ContextWrapper(null)
            return ContextWrapper(context.createConfigurationContext(Configuration().apply {
                setLocale(locale)
            }))
        }

        fun locale(context: Context): Locale {
            if (Build.VERSION.SDK_INT >= 24) {
                return context.resources.configuration.locales[0]
            } else {
                @Suppress("DEPRECATION")
                return context.resources.configuration.locale
            }
        }

    }

    object Device {

        fun locales(): List<Locale> {
            val localeListCompat = LocaleListCompat.getDefault()
            return (0 until localeListCompat.size()).map { localeListCompat.get(it) }
        }

        fun locale(): Locale {
            return locales()[0]
        }

    }

    fun language(locale: Locale = Device.locale()): String {
        return locale.displayLanguage
    }

    fun language2(locale: Locale = Device.locale()): String {
        return locale.language
    }

    fun language3(locale: Locale = Device.locale()): String {
        return locale.isO3Language
    }

    fun country(locale: Locale = Device.locale()): String {
        return locale.displayCountry
    }

    fun country2(locale: Locale = Device.locale()): String {
        return locale.country
    }

    fun country3(locale: Locale = Device.locale()): String {
        return locale.isO3Country
    }

}
