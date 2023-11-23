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
                mapper.toDomain(api.fetchWords().words)))
        }.catch {
            Log.i("error", "error")
            emit(Result.failure(RuntimeException("No!")))
        }
    }

    override fun searchArticles(query: String, page: Int, pageSize: Int): Flow<Result<List<Word>>> {
        TODO("Not yet implemented")
    }

    override fun getArticleDetails(id: Int): Flow<Result<Word>> {
        TODO("Not yet implemented")
    }
}