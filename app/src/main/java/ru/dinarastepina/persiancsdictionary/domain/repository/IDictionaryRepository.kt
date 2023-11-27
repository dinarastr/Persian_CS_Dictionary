package ru.dinarastepina.persiancsdictionary.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.domain.model.Word

interface IDictionaryRepository {
    fun getAllArticles(page: String, pageSize: Int): Flow<PagingData<Word>>

    fun searchArticles(query: String, page: String, pageSize: Int): Flow<Result<List<Word>>>

    fun getArticleDetails(id: String): Flow<Result<Word>>
}