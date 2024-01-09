package com.amaris.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.amaris.data.local.entities.MovieRemoteKeys

@Dao
abstract class MovieRemoteKeysDao : BaseDao<MovieRemoteKeys>() {
    @Query("SELECT * FROM remote_keys WHERE movieId = :movieId")
    abstract suspend fun remoteKeysRepoId(movieId: String): MovieRemoteKeys?

    @Query("DELETE FROM remote_keys")
    abstract suspend fun clearRemoteKeys()
}