package me.shkschneider.skeleton.datax

import androidx.room.TypeConverter
import java.util.Date

class SkeletonTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) Date() else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time ?: 0
    }

    @TypeConverter
    fun fromStringToArray(value: String?): Array<String>? {
        return value?.split(",")?.toTypedArray() ?: arrayOf()
    }

    @TypeConverter
    fun stringToStringArray(strings: Array<String>?): String? {
        return strings?.joinToString(",") ?: ""
    }

}