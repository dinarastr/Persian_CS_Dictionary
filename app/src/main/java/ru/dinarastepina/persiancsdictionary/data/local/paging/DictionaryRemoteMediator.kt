package ru.dinarastepina.persiancsdictionary.data.local.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
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
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WordDB>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> null

                LoadType.PREPEND -> return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey()
                    }
                    if (remoteKey?.currentChunkLastId == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.currentChunkLastId
                }
            }

            val data = api.fetchWords(
                lastId = currentPage.orEmpty(),
                limit = when (loadType) {
                    LoadType.REFRESH -> 60
                    else -> 20
                }
            ).words

            val endOfPaginationReached = data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dictionaryDao.deleteAllCache()
                    remoteKeyDao.deleteRemoteKey()
                }
                remoteKeyDao.addRemoteKey(RemoteKey(
                    currentChunkLastId = if (endOfPaginationReached) null else data.last().id
                ))
                dictionaryDao.insertAll(words = data.map { mapper.toDB(it) })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}