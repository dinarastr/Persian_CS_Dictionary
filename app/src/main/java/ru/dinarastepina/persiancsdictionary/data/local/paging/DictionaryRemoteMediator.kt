package ru.dinarastepina.persiancsdictionary.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DictionaryRemoteMediator @Inject constructor(
    private val database: DictionaryDatabase,
    private val api: DictionaryApi,
    private val mapper: DataMapper
) : RemoteMediator<String, WordDB>() {

    private val userDao = database.dictionaryDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, WordDB>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.id
                }
            }
            val response = api.fetchWords(
                lastId = loadKey.orEmpty(), limit = 20
            )

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
}