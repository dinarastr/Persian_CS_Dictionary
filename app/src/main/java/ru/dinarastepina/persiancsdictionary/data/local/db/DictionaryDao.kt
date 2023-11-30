package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM words order by id")
    fun fetchAllWords(): PagingSource<Int, WordDB>

    @Query("SELECT * FROM words WHERE english like :query")
    fun searchWords(query: String): PagingSource<Int, WordDB>

    @Query("SELECT * FROM words WHERE id LIKE :id LIMIT 1")
    fun fetchAllWords(id: String): WordDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordDB>)

    @Query("delete from words")
    suspend fun deleteAllCache()
}