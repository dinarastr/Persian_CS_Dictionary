package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dinarastepina.persiancsdictionary.data.local.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): RemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKey>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}