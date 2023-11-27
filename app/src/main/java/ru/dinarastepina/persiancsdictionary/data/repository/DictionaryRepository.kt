package ru.dinarastepina.persiancsdictionary.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import java.lang.RuntimeException
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val api: DictionaryApi,
    private val mapper: DataMapper
): IDictionaryRepository {
    override fun getAllArticles(page: String, pageSize: Int): Flow<Result<List<Word>>> {
        return flow {
            emit(Result.success(
                api.fetchWords(page, pageSize).words.map { mapper.toDomain(it) }))
        }.catch {
            Log.e("error", "error")
            emit(Result.failure(RuntimeException("No!")))
        }
    }

    override fun searchArticles(query: String, page: String, pageSize: Int): Flow<Result<List<Word>>> {
        return flow {
            emit(Result.success(
                api.searchWords(query, page, pageSize).words.map { mapper.toDomain(it) }))
        }.catch {
            Log.e("error", "error")
            emit(Result.failure(RuntimeException("No!")))
        }
    }

    override fun getArticleDetails(id: String): Flow<Result<Word>> {
        return flow {
            emit(Result.success(
                mapper.toDomain(api.fetchWordDetails(id))))
        }.catch {
            Log.e("error", "error")
            emit(Result.failure(RuntimeException("No!")))
        }
    }
}