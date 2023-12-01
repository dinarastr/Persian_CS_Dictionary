package ru.dinarastepina.persiancsdictionary.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDao
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.local.paging.DictionaryRemoteMediator
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import java.lang.RuntimeException
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val mapper: DataMapper,
    private val database: DictionaryDatabase,
    private val api: DictionaryApi
): IDictionaryRepository {

    override fun getAllArticles(): Flow<PagingData<Word>> {
       val source = {
            database.dictionaryDao().fetchAllWords()
        }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20) ,
            remoteMediator = DictionaryRemoteMediator(
                database, api, mapper
            ),
            pagingSourceFactory = source
        ).flow.map { data ->
            data.map { mapper.toDomain(it) }
        }.catch {
            Log.e("error", it.message.toString())
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