package com.amaris.data.local.db

import androidx.room.TypeConverter

object DBConverters {
    @TypeConverter
    fun toString(list: List<Long>): String = list.joinToString("|")
}