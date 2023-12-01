package ru.dinarastepina.persiancsdictionary.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dinarastepina.persiancsdictionary.data.local.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys")
    suspend fun getRemoteKey(): RemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKey(remoteKeys: RemoteKey)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKey()
}