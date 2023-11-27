package ru.dinarastepina.persiancsdictionary.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Database(entities = [WordDB::class], exportSchema = false, version = 1)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}