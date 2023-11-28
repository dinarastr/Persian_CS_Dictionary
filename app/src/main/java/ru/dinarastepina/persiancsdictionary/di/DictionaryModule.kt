package ru.dinarastepina.persiancsdictionary.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.data.repository.DictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DictionaryModule {

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        mapper: DataMapper,
        api: DictionaryApi,
        database: DictionaryDatabase
    )
    : IDictionaryRepository {
        return DictionaryRepository(
            mapper,
            database,
            api
        )
    }
}