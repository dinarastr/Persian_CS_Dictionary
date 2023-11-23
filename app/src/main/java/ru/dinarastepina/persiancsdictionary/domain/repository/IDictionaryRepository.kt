package ru.dinarastepina.persiancsdictionary.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.persiancsdictionary.domain.model.Word

interface IDictionaryRepository {
    fun getAllArticles(page: String = "", pageSize: Int = 20): Flow<Result<List<Word>>>

    fun searchArticles(query: String, page: Int, pageSize: Int): Flow<Result<List<Word>>>

    fun getArticleDetails(id: Int): Flow<Result<Word>>
}