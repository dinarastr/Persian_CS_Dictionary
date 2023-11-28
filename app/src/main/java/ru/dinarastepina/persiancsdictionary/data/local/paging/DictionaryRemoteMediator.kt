package ru.dinarastepina.persiancsdictionary.data.local.paging

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

    private val userDao = database.dictionaryDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WordDB>
    ): MediatorResult {
        return try {
            val latestFetchedId = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.id
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = api.fetchWords(
                lastId = latestFetchedId.orEmpty(),
                limit = 20)

            val endOfPaginationReached = response.words.isEmpty()

            database.withTransaction {
                userDao.insertAll(response.words.map { mapper.toDB(it) })
            }

            MediatorResult.Success(
                endOfPaginationReached = response.nextPage == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, WordDB>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, WordDB>
    ): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { word ->
                remoteKeyDao.getRemoteKeys(id = word.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, WordDB>
    ): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { word ->
                remoteKeyDao.getRemoteKeys(id = word.id)
            }
    }
}