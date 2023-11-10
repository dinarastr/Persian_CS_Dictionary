package ru.dinarastepina.persiancsdictionary.data.repository

import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.model.Word

class DictionaryRepository: IDictionaryRepository {
    override fun getAllArticles(page: Int, pageSize: Int): List<Word> {
        TODO("Not yet implemented")
    }

    override fun searchArticles(query: String, page: Int, pageSize: Int): List<Word> {
        TODO("Not yet implemented")
    }

    override fun getArticleDetails(id: Int): Word {
        TODO("Not yet implemented")
    }
}