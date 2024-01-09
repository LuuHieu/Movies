package com.amaris.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey val movieId: String,
    val prevKey: Int?,
    val nextKey: Int?
) : BaseEntity
