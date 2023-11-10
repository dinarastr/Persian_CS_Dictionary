package ru.dinarastepina.persiancsdictionary.domain.repository

import ru.dinarastepina.persiancsdictionary.domain.model.Word

interface IDictionaryRepository {
    fun getAllArticles(page: Int, pageSize: Int): List<Word>

    fun searchArticles(query: String, page: Int, pageSize: Int): List<Word>

    fun getArticleDetails(id: Int): Word
}