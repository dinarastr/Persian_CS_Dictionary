package ru.dinarastepina.persiancsdictionary.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.dinarastepina.persiancsdictionary.data.local.db.DictionaryDatabase
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class CachedDataSource @Inject constructor(
    private val query: String = "",
    private val database: DictionaryDatabase,
): PagingSource<Int, WordDB>() {
    override fun getRefreshKey(state: PagingState<Int, WordDB>): Int? {
        return state.anchorPosition.let {anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(anchorPosition?.let {
                state.closestPageToPosition(
                    it
                )
            })
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WordDB> {
        val currentPage = params.key ?: 0
        return try {
            val data = database.dictionaryDao().fetchAllWords(offset = 20 * currentPage, limit = 20)
            val endOfPaginationReached = data.isEmpty()
            if (data.isNotEmpty()) {
                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}