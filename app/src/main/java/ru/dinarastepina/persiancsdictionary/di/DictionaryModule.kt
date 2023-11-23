package ru.dinarastepina.persiancsdictionary.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import javax.inject.Singleton

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)
@Module
@InstallIn(SingletonComponent::class)
class DictionaryModule {
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