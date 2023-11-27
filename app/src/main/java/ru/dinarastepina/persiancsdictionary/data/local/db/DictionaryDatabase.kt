package ru.dinarastepina.persiancsdictionary.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Database(entities = [WordDB::class], exportSchema = false, version = 1)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    "persian_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}