package ru.dinarastepina.persiancsdictionary.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "words")
@TypeConverters(TranslationsConverter::class)
data class WordDB(
    @PrimaryKey
    val id: String = "",
    val english: String,
    val meanings: List<String>
)


object TranslationsConverter {
    @TypeConverter
    fun meaningsToString(meanings: List<String>): String {
        return meanings.joinToString(" ; ")
    }

    @TypeConverter
    fun stringToMeanings(string: String): List<String> {
        return string.split(" ; ")
    }
}