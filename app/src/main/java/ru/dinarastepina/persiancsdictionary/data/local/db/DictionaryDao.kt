package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM words order by english asc limit :limit offset :offset")
    suspend fun fetchAllWords(offset: Int, limit: Int): List<WordDB>

    @Query("SELECT * FROM words WHERE english like :query limit :limit offset :offset")
    fun searchWords(query: String, offset: Int, limit: Int): List<WordDB>

    @Query("SELECT * FROM words WHERE id LIKE :id LIMIT 1")
    fun fetchWordDetails(id: String): WordDB

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<WordDB>)

    @Query("delete from words")
    suspend fun deleteAllCache()
}