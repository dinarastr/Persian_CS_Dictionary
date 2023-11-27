package ru.dinarastepina.persiancsdictionary.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.data.repository.DictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import javax.inject.Singleton

val client = OkHttpClient()
@Module
@InstallIn(SingletonComponent::class)
abstract class DictionaryModule {

    companion object {
        @Provides
        @Singleton
        fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi {
            return retrofit.create(DictionaryApi::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(
                "https://cspersiandictionary.onrender.com/"
            )
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Binds
    abstract fun bindDictionaryRepository(repository: DictionaryRepository): IDictionaryRepository
}