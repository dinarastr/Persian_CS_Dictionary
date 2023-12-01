package ru.dinarastepina.persiancsdictionary.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.dinarastepina.persiancsdictionary.data.mapper.DataMapper
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val api: DictionaryApi,
    private val query: String,
    private val mapper: DataMapper
): PagingSource<String, Word>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Word> {
        val currentPage: String = params.key ?: ""
        return try {
            val response = api.searchWords(
                query = query,
                lastId = currentPage,
                limit = 20
            )
            val endOfPaginationReached = response.words.isEmpty()

            if (response.words.isNotEmpty()) {
                LoadResult.Page(
                    data = response.words.map { mapper.toDomain(it) },
                    prevKey = if (currentPage == "") null else currentPage,
                    nextKey = if (endOfPaginationReached) null else response.words.last().id
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

    override fun getRefreshKey(state: PagingState<String, Word>): String? {
        return state.anchorPosition.let {anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(anchorPosition?.let {
                state.closestPageToPosition(
                    it
                )
            })
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }
}