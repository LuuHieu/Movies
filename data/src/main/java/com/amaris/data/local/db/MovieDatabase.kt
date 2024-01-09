package com.amaris.data.local.db

import com.amaris.data.local.dao.MovieRemoteKeysDao
import com.amaris.data.local.dao.MovieDao

interface MovieDatabase {
    fun movieDao(): MovieDao

    fun movieRemoteKeysDao(): MovieRemoteKeysDao
}