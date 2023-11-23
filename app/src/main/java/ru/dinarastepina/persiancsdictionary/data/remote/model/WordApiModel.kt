package ru.dinarastepina.persiancsdictionary.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataRaw(
    val success: Boolean,
    val message: String? =null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val words: List<WordApiModel> = emptyList()
)
data class WordApiModel(
    @SerializedName("_id")
    val id: String,
    @SerializedName("EnglishWord")
    val english: String,
    @SerializedName("Meanings")
    val meanings: List<String>
)
