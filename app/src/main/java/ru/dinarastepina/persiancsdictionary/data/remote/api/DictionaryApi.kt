package ru.dinarastepina.persiancsdictionary.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dinarastepina.persiancsdictionary.data.remote.model.DataRaw
import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel

interface DictionaryApi {
    @GET("dictionary")
    suspend fun fetchWords(
        @Query("lastFetchedId") lastId: String,
        @Query("limit") limit: Int)
    : DataRaw

    @GET("dictionary/search")
    suspend fun searchWords(
        @Query("query") query: String,
        @Query("lastFetchedId") lastId: String,
        @Query("limit") limit: Int
    ): DataRaw

    suspend fun fetchWordDetails(
        @Query("id") id: String)
    : WordApiModel
}