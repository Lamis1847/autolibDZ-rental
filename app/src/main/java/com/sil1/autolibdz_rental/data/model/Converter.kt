package com.sil1.autolibdz_rental.data.model

import androidx.room.TypeConverter
import java.util.*

class Converter {
//le converter pour stocker le type date qui n'est pas supporté par sqlLite

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
