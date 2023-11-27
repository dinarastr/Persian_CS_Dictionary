package ru.dinarastepina.persiancsdictionary.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordDB(
    @PrimaryKey
    val id: String = "",
    val english: String,
    val meanings: List<String>
)