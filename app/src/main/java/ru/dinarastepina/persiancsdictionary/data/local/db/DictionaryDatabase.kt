package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.RemoteKey
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Database(entities = [WordDB::class, RemoteKey::class], exportSchema = false, version = 1)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}