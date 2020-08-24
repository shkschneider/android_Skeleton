package me.shkschneider.skeleton.android.db

import androidx.room.TypeConverter
import java.util.Date

class SkeletonTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? =
        if (value == null) Date() else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        date?.time ?: 0

    @TypeConverter
    fun fromStringToArray(value: String?): Array<String>? =
        value?.split(",")?.toTypedArray() ?: arrayOf()

    @TypeConverter
    fun stringToStringArray(strings: Array<String>?): String? =
        strings?.joinToString(",") ?: ""

}