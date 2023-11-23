package ru.dinarastepina.persiancsdictionary.data.remote.api

import retrofit2.http.GET
import ru.dinarastepina.persiancsdictionary.data.remote.model.DataRaw
import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel

interface DictionaryApi {
    @GET("dictionary?lastFetchedId=655601e7c17c6e1a08d17b90")
    suspend fun fetchWords(): DataRaw

    suspend fun searchWords(): DataRaw

    suspend fun fetchWordDetails(): WordApiModel
}