package com.amaris.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amaris.data.local.entities.MovieRemoteKeys
import com.amaris.data.model.Movie

@Database(
    entities = [
        Movie::class,
        MovieRemoteKeys::class
    ],
    exportSchema = false,
    version = 1
)
abstract class MovieRoomDatabase : RoomDatabase(), MovieDatabase