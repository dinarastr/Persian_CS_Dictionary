package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM words")
    fun fetchAllWords(): PagingSource<Int, WordDB>

    @Query("SELECT * FROM words WHERE english like :query")
    fun searchWords(query: String): PagingSource<Int, WordDB>

    @Query("SELECT * FROM words WHERE id LIKE :id LIMIT 1")
    fun fetchAllWords(id: String): WordDB
}