package me.shkschneider.skeleton.helper

import android.os.Build
import android.support.annotation.IntRange

// <https://source.android.com/source/build-numbers#platform-code-names-versions-api-levels-and-ndk-releases>
object AndroidHelper {

    const val API_1 = Build.VERSION_CODES.BASE // 1.0
    const val API_2 = Build.VERSION_CODES.BASE_1_1 // 1.1
    const val API_3 = Build.VERSION_CODES.CUPCAKE // 1.5
    const val API_4 = Build.VERSION_CODES.DONUT // 1.6
    const val API_5 = Build.VERSION_CODES.ECLAIR // 2.0
    const val API_6 = Build.VERSION_CODES.ECLAIR_0_1 // 2.0.1
    const val API_7 = Build.VERSION_CODES.ECLAIR_MR1 // 2.1
    const val API_8 = Build.VERSION_CODES.FROYO // 2.2.x
    const val API_9 = Build.VERSION_CODES.GINGERBREAD // 2.3-2.3.2
    const val API_10 = Build.VERSION_CODES.GINGERBREAD_MR1 // 2.3.3-2.3.7
    const val API_11 = Build.VERSION_CODES.HONEYCOMB // 3.0
    const val API_12 = Build.VERSION_CODES.HONEYCOMB_MR1 // 3.1
    const val API_13 = Build.VERSION_CODES.HONEYCOMB_MR2 // 3.2.x
    const val API_14 = Build.VERSION_CODES.ICE_CREAM_SANDWICH // 4.0.1-4.0.2
    const val API_15 = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 // 4.0.3-4.0.4
    const val API_16 = Build.VERSION_CODES.JELLY_BEAN // 4.1.x
    const val API_17 = Build.VERSION_CODES.JELLY_BEAN_MR1 // 4.2.x
    const val API_18 = Build.VERSION_CODES.JELLY_BEAN_MR2 // 4.3.x
    const val API_19 = Build.VERSION_CODES.KITKAT // 4.4-4.4.x
    const val API_20 = Build.VERSION_CODES.KITKAT_WATCH // 4.4.x
    const val API_21 = Build.VERSION_CODES.LOLLIPOP // 5.0
    const val API_22 = Build.VERSION_CODES.LOLLIPOP_MR1 // 5.1
    const val API_23 = Build.VERSION_CODES.M // 6.0 "Marshmallow"
    const val API_24 = Build.VERSION_CODES.N // 7.0 "Nougat"
    const val API_25 = Build.VERSION_CODES.N_MR1 // 7.1.1
    const val API_26 = Build.VERSION_CODES.O // 8.0 "Oreo"
    const val API_27 = Build.VERSION_CODES.O_MR1 // 8.1

    const val PLATFORM = "Android"

    const val ANDROID_1 = API_1
    const val ANDROID_2 = API_5
    const val ANDROID_3 = API_11
    const val ANDROID_4 = API_14
    const val ANDROID_5 = API_21
    const val ANDROID_6 = API_23
    const val ANDROID_7 = API_24
    const val ANDROID_8 = API_26

    fun codename(@IntRange(from = API_1.toLong()) api: Int = api()): String {
        when (api) {
            API_1, API_2 -> return "Base"
            API_3 -> return "Cupcake"
            API_4 -> return "Donut"
            API_5, API_6, API_7 -> return "Eclair"
            API_8 -> return "Froyo"
            API_9, API_10 -> return "Gingerbread"
            API_11, API_12, API_13 -> return "Honeycomb"
            API_14, API_15 -> return "IceCreamSandwich"
            API_16, API_17, API_18 -> return "JellyBean"
            API_19, API_20 -> return "KitKat"
            API_21, API_22 -> return "Lollipop"
            API_23 -> return "Marshmallow"
            API_24, API_25 -> return "Nougat"
            API_26, API_27 -> return "Oreo"
            else -> return "*Preview*"
        }
    }

    fun preview(): Boolean {
        val codename = codename()
        return codename.startsWith("*") && codename.endsWith("*")
    }

    fun api(): Int {
        return Build.VERSION.SDK_INT
    }

    fun versionName(): String {
        return Build.VERSION.RELEASE
    }

}
