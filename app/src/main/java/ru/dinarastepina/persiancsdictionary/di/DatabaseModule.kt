package ru.dinarastepina.persiancsdictionary.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): DictionaryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DictionaryDatabase::class.java,
            "persian_database"
        ).build()
    }
}