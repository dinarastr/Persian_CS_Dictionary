package ru.dinarastepina.persiancsdictionary.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.domain.model.Word

interface IDictionaryRepository {
    fun getAllArticles(): Flow<PagingData<Word>>

    fun searchArticles(query: String): Flow<PagingData<Word>>

    fun getArticleDetails(id: String): Flow<Result<Word>>
}