package ru.dinarastepina.persiancsdictionary.data.local.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.RemoteKey
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import java.io.IOException
import java.util.LinkedList
import javax.inject.Inject
import kotlin.jvm.Throws

@OptIn(ExperimentalPagingApi::class)
class DictionaryRemoteMediator @Inject constructor(
    private val database: DictionaryDatabase,
    private val api: DictionaryApi,
    private val mapper: DataMapper
) : RemoteMediator<Int, WordDB>() {

    private val dictionaryDao = database.dictionaryDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WordDB>
    ): MediatorResult {
        return try {
            val currentPage: String = when (loadType) {
                LoadType.REFRESH -> {
                    ""
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val last = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    last.id
                }
            }

            val response = api.fetchWords(
                lastId = currentPage,
                limit = 20
            )

            val endOfPaginationReached = response.words.size < 20
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dictionaryDao.deleteAllCache()
                }
                dictionaryDao.insertAll(words = response.words.map { mapper.toDB(it) })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}